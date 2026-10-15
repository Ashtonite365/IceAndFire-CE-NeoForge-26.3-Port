package com.iafenvoy.iceandfire.item.block;

import com.iafenvoy.iceandfire.registry.IafFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.grower.TreeGrower;


public class DreadwoodSaplingBlock extends SaplingBlock {
    public DreadwoodSaplingBlock() {
        super(new TreeGrower("dread_wood",
                // 26.3 TreeGrower: old 0.1 secondary chance -> 9:1 weights (same as vanilla OAK/FANCY_OAK)
                WeightedList.of(new Weighted<>(IafFeatures.DREADWOOD, 9), new Weighted<>(IafFeatures.DREADWOOD_LARGE, 1)),
                WeightedList.of(), WeightedList.of(), IafFeatures.DREADWOOD), Properties.ofFullCopy(Blocks.OAK_SAPLING));
    }
}
