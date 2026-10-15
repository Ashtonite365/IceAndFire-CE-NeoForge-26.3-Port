package com.iafenvoy.iceandfire.item.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Client-only helpers for H4 items. Only call from code guarded by {@code level.isClientSide()}
 * (26.3: @OnlyIn no longer strips classes, so items must not reference client classes directly).
 */
public final class ItemClientHelper {
    private ItemClientHelper() {}

    public static boolean isShiftDown() {
        return InputConstants.isKeyDown(InputConstants.KEY_LSHIFT) || InputConstants.isKeyDown(InputConstants.KEY_RSHIFT);
    }

    public static @Nullable Entity getClientEntity(Level level, UUID uuid) {
        return level instanceof ClientLevel clientLevel ? clientLevel.entityStorage.getEntityGetter().get(uuid) : null;
    }
}
