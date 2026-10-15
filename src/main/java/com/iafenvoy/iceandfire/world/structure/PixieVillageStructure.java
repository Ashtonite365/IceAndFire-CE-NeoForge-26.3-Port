package com.iafenvoy.iceandfire.world.structure;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.PixieEntity;
import com.iafenvoy.iceandfire.item.block.PixieHouseBlock;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafStructurePieces;
import com.iafenvoy.iceandfire.registry.IafStructureTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PixieVillageStructure extends Structure {
    public static final MapCodec<PixieVillageStructure> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(settingsCodec(instance)).apply(instance, PixieVillageStructure::new));

    protected PixieVillageStructure(StructureSettings config) {
        super(config);
    }

    /** Max horizontal reach from the start; vanilla only creates structure references within 8 chunks. */
    private static final int MAX_REACH = 112;

    @SuppressWarnings("deprecation")
    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        if (context.random().nextDouble() >= IafCommonConfig.INSTANCE.worldGen.generatePixieVillageChance.getValue())
            return Optional.empty();
        Rotation blockRotation = Rotation.getRandom(context.random());
        BlockPos blockPos = this.getLowestYIn5by5Box(context, context.chunkPos().getBlockX(7), context.chunkPos().getBlockZ(7), blockRotation);
        long seed = context.random().nextLong();
        int maxRoads = IafCommonConfig.INSTANCE.pixie.size.getValue();
        int maxPixies = IafCommonConfig.INSTANCE.pixie.maxPixiesPerVillage.getValue();
        return Optional.of(new GenerationStub(blockPos, collector -> collector.addPiece(new PixieVillagePiece(blockPos, seed, maxRoads, maxPixies, context.heightAccessor().getMinY(), context.heightAccessor().getMaxY()))));
    }

    @Override
    public @NotNull StructureType<?> type() {
        return IafStructureTypes.PIXIE_VILLAGE.get();
    }

    /** One road cell of the village; house/pixie data is precomputed so every chunk sees the same layout. */
    private record Cell(int x, int z, Direction houseDir, int houseColor, boolean pixie) {
    }

    /**
     * Deterministic x/z layout (same random sequence as the original generator). Heights are resolved per chunk
     * at placement time, so each postProcess call only reads/writes inside its own chunk.
     */
    private static List<Cell> layout(BlockPos origin, long seed, int baseRoads, int maxPixies) {
        RandomSource random = RandomSource.create(seed);
        int maxRoads = baseRoads + random.nextInt(5);
        int spawnedPixies = 0;
        int bx = origin.getX(), bz = origin.getZ();
        List<Cell> cells = new ArrayList<>();
        for (int road = 0; road < maxRoads; road++) {
            int roadLength = 10 + random.nextInt(15);
            Direction dir = Direction.from2DDataValue(random.nextInt(3));
            for (int i = 0; i < roadLength; i++) {
                int x = bx + dir.getStepX() * i, z = bz + dir.getStepZ() * i;
                Direction houseDir = null;
                int color = 0;
                boolean pixie = false;
                if (random.nextInt(8) == 0) {
                    houseDir = random.nextBoolean() ? dir.getClockWise() : dir.getCounterClockWise();
                    color = random.nextInt(5);
                }
                // keep the whole village (incl. house offset) inside the structure-reference range
                if (Math.abs(x - origin.getX()) > MAX_REACH - 1 || Math.abs(z - origin.getZ()) > MAX_REACH - 1)
                    continue;
                if (houseDir != null && spawnedPixies < maxPixies) {
                    pixie = true;
                    spawnedPixies++;
                }
                cells.add(new Cell(x, z, houseDir, color, pixie));
            }
            int adv = random.nextInt(roadLength);
            bx += dir.getStepX() * adv;
            bz += dir.getStepZ() * adv;
        }
        return cells;
    }

    private static BlockState houseState(int color, Direction houseDir) {
        Direction facing = houseDir.getOpposite();
        return switch (color) {
            case 0 -> IafBlocks.PIXIE_HOUSE_MUSHROOM_RED.get().defaultBlockState().setValue(PixieHouseBlock.FACING, facing);
            case 1 -> IafBlocks.PIXIE_HOUSE_MUSHROOM_BROWN.get().defaultBlockState().setValue(PixieHouseBlock.FACING, facing);
            case 2 -> IafBlocks.PIXIE_HOUSE_OAK.get().defaultBlockState().setValue(PixieHouseBlock.FACING, facing);
            case 3 -> IafBlocks.PIXIE_HOUSE_BIRCH.get().defaultBlockState().setValue(PixieHouseBlock.FACING, facing);
            case 4 -> IafBlocks.PIXIE_HOUSE_SPRUCE.get().defaultBlockState().setValue(PixieHouseBlock.FACING, facing);
            default -> IafBlocks.PIXIE_HOUSE_OAK.get().defaultBlockState();
        };
    }

    public static class PixieVillagePiece extends StructurePiece {
        private final BlockPos origin;
        private final long seed;
        private final int maxRoads;
        private final int maxPixies;
        private List<Cell> cells;

        protected PixieVillagePiece(BlockPos origin, long seed, int maxRoads, int maxPixies, int minY, int maxY) {
            super(IafStructurePieces.PIXIE_VILLAGE.get(), 0, computeBox(origin, seed, maxRoads, maxPixies, minY, maxY));
            this.origin = origin;
            this.seed = seed;
            this.maxRoads = maxRoads;
            this.maxPixies = maxPixies;
        }

        public PixieVillagePiece(StructurePieceSerializationContext context, CompoundTag nbt) {
            super(IafStructurePieces.PIXIE_VILLAGE.get(), nbt);
            this.origin = new BlockPos(nbt.getIntOr("OX", 0), nbt.getIntOr("OY", 0), nbt.getIntOr("OZ", 0));
            this.seed = nbt.getLongOr("Seed", 0L);
            this.maxRoads = nbt.getIntOr("Roads", 0);
            this.maxPixies = nbt.getIntOr("Pixies", 0);
        }

        private static BoundingBox computeBox(BlockPos origin, long seed, int maxRoads, int maxPixies, int minY, int maxY) {
            int x0 = origin.getX(), x1 = x0, z0 = origin.getZ(), z1 = z0;
            for (Cell c : layout(origin, seed, maxRoads, maxPixies)) {
                x0 = Math.min(x0, c.x() - 1); x1 = Math.max(x1, c.x() + 1);
                z0 = Math.min(z0, c.z() - 1); z1 = Math.max(z1, c.z() + 1);
            }
            return new BoundingBox(x0, minY, z0, x1, maxY, z1);
        }

        @Override
        protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, @NotNull CompoundTag nbt) {
            nbt.putInt("OX", this.origin.getX());
            nbt.putInt("OY", this.origin.getY());
            nbt.putInt("OZ", this.origin.getZ());
            nbt.putLong("Seed", this.seed);
            nbt.putInt("Roads", this.maxRoads);
            nbt.putInt("Pixies", this.maxPixies);
        }

        @Override
        public void postProcess(@NotNull WorldGenLevel world, @NotNull StructureManager structureAccessor, @NotNull ChunkGenerator chunkGenerator, @NotNull RandomSource random, @NotNull BoundingBox chunkBox, @NotNull ChunkPos chunkPos, @NotNull BlockPos pivot) {
            if (this.cells == null) this.cells = layout(this.origin, this.seed, this.maxRoads, this.maxPixies);
            // Every read/write below is clamped to chunkBox (the chunk being decorated), never a neighbour.
            for (Cell c : this.cells) {
                BlockPos column = new BlockPos(c.x(), 0, c.z());
                if (chunkBox.isInside(column.atY(chunkBox.minY()))) {
                    BlockPos road = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, column).below();
                    if (chunkBox.isInside(road)) {
                        if (world.getBlockState(road).getFluidState().isEmpty())
                            world.setBlock(road, Blocks.DIRT_PATH.defaultBlockState(), 2);
                        else
                            world.setBlock(road, Blocks.SPRUCE_PLANKS.defaultBlockState(), 2);
                        if (c.pixie()) {
                            PixieEntity pixie = IafEntities.PIXIE.get().create(world.getLevel(), EntitySpawnReason.STRUCTURE);
                            if (pixie != null) {
                                pixie.finalizeSpawn(world, world.getCurrentDifficultyAt(road.above()), EntitySpawnReason.STRUCTURE, null);
                                pixie.setPos(road.getX() + 0.5, road.getY() + 2, road.getZ() + 0.5);
                                pixie.setPersistenceRequired();
                                world.addFreshEntity(pixie);
                            }
                        }
                    }
                }
                if (c.houseDir() == null) continue;
                BlockPos hcol = column.relative(c.houseDir());
                if (!chunkBox.isInside(hcol.atY(chunkBox.minY()))) continue;
                // house sits beside the road; the road column is in this or the adjacent chunk, so use the house column's own surface
                BlockPos base = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, hcol).below();
                if (!chunkBox.isInside(base) || !chunkBox.isInside(base.above())) continue;
                world.setBlock(base.above(), houseState(c.houseColor(), c.houseDir()), 2);
                if (!world.getBlockState(base).canOcclude()) {
                    world.setBlock(base, Blocks.COARSE_DIRT.defaultBlockState(), 2);
                    if (chunkBox.isInside(base.below()))
                        world.setBlock(base.below(), Blocks.COARSE_DIRT.defaultBlockState(), 2);
                }
            }
        }
    }
}
