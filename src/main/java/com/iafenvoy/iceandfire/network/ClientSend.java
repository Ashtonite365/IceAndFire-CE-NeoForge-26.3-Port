package com.iafenvoy.iceandfire.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

/** Client-only: the one place common code (entities) reaches ClientPacketDistributor. Call only when level().isClientSide(). */
public final class ClientSend {
    private ClientSend() {
    }

    public static void toServer(CustomPacketPayload payload) {
        ClientPacketDistributor.sendToServer(payload);
    }
}
