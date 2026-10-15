package com.iafenvoy.iceandfire.item.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.sounds.AmbientLeavesBlockSoundPlayer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jspecify.annotations.NonNull;

public class DreadLeavesBlock extends LeavesBlock {

    public DreadLeavesBlock(BlockBehaviour.Properties properties) {
        super(AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties);
    }

}
