package com.iafenvoy.iceandfire.mixin;

import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.atomic.AtomicInteger;

// 26.3: ENTITY_COUNTER moved from Entity to ServerLevel (used by ServerLevel.getNextEntityId)
@Mixin(ServerLevel.class)
public interface EntityIdAccessor {
    @Accessor("ENTITY_COUNTER")
    static AtomicInteger iceandfire$getEntityCounter() {
        throw new AssertionError();
    }
}
