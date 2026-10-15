package com.iafenvoy.iceandfire.registry;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.world.processor.DreadRuinProcessor;
import com.iafenvoy.iceandfire.world.processor.GraveyardProcessor;
import com.iafenvoy.iceandfire.world.processor.VillageHouseProcessor;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * MC 26.3: the structure_processor registry holds the processors' MapCodecs directly
 * (StructureProcessorType is no longer a generic registry type). StructureProcessor#codec()
 * returns MapCodec, so {@code return IafProcessors.X.get();} keeps working.
 */
public final class IafProcessors {
    public static final DeferredRegister<MapCodec<? extends StructureProcessor>> REGISTRY = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, IceAndFire.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends StructureProcessor>, MapCodec<GraveyardProcessor>> GRAVEYARD_PROCESSOR = registerProcessor("graveyard_processor", () -> GraveyardProcessor.CODEC);
    public static final DeferredHolder<MapCodec<? extends StructureProcessor>, MapCodec<VillageHouseProcessor>> VILLAGE_HOUSE_PROCESSOR = registerProcessor("village_house_processor", () -> VillageHouseProcessor.CODEC);
    public static final DeferredHolder<MapCodec<? extends StructureProcessor>, MapCodec<DreadRuinProcessor>> DREAD_MAUSOLEUM_PROCESSOR = registerProcessor("dread_mausoleum_processor", () -> DreadRuinProcessor.CODEC);

    private static <P extends StructureProcessor> DeferredHolder<MapCodec<? extends StructureProcessor>, MapCodec<P>> registerProcessor(String name, Supplier<MapCodec<P>> codec) {
        return REGISTRY.register(name, codec);
    }
}
