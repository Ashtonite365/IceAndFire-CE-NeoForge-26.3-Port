package com.iafenvoy.iceandfire.world.feature;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.uranus.util.RandomHelper;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import com.iafenvoy.iceandfire.registry.IafEntities;
import java.util.function.Supplier;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;

public class DragonSkeletonSpawnFeature implements Feature {
    // 26.3: features are data objects; one unit codec per dragon type, entity type resolved lazily at placement
    public static final MapCodec<DragonSkeletonSpawnFeature> FIRE_CODEC = MapCodec.unit(() -> new DragonSkeletonSpawnFeature(IafEntities.FIRE_DRAGON));
    public static final MapCodec<DragonSkeletonSpawnFeature> ICE_CODEC = MapCodec.unit(() -> new DragonSkeletonSpawnFeature(IafEntities.ICE_DRAGON));
    public static final MapCodec<DragonSkeletonSpawnFeature> LIGHTNING_CODEC = MapCodec.unit(() -> new DragonSkeletonSpawnFeature(IafEntities.LIGHTNING_DRAGON));
    protected final Supplier<? extends EntityType<? extends DragonBaseEntity>> dragonType;

    public DragonSkeletonSpawnFeature(Supplier<? extends EntityType<? extends DragonBaseEntity>> dt) {
        this.dragonType = dt;
    }

    @Override
    public MapCodec<DragonSkeletonSpawnFeature> codec() {
        return this.dragonType == IafEntities.ICE_DRAGON ? ICE_CODEC : this.dragonType == IafEntities.LIGHTNING_DRAGON ? LIGHTNING_CODEC : FIRE_CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator generator, RandomSource random, BlockPos origin) {
        WorldGenLevel world = level;
                BlockPos pos = world.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, origin.offset(8, 0, 8));
        if (IafCommonConfig.INSTANCE.dragon.generateSkeletons.getValue() && random.nextDouble() < IafCommonConfig.INSTANCE.dragon.generateSkeletonChance.getValue()) {
            DragonBaseEntity dragon = this.dragonType.get().create(world.getLevel(), EntitySpawnReason.CHUNK_GENERATION);
            assert dragon != null;
            dragon.setPos(pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F);
            int age = 10 + random.nextInt(100);
            dragon.growDragon(age);
            dragon.modelDeadProgress = 20;
            dragon.setModelDead(true);
            dragon.setDeathStage(age / 10);
            dragon.setYRot(random.nextInt(360));
            dragon.setVariant(RandomHelper.randomOne(dragon.dragonType.colors()).getName());
            world.addFreshEntity(dragon);
        }
        return true;
    }
}
