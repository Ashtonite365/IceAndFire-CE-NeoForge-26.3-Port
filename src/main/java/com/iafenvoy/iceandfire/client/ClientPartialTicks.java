package com.iafenvoy.iceandfire.client;

import net.minecraft.client.Minecraft;

/**
 * Client-only holder for {@link Minecraft} access. Only reference this class behind a
 * {@code FMLEnvironment.getDist().isClient()} check so dedicated servers never load client classes.
 */
public final class ClientPartialTicks {
    private ClientPartialTicks() {
    }

    public static float get() {
        return Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
    }
}
