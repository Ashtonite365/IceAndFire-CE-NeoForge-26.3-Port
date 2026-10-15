package com.iafenvoy.iceandfire.network;

import com.iafenvoy.iceandfire.network.payload.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
/**
 * 26.3 side safety: client handlers are wrapped in lambdas, not method references. A method reference links
 * ClientNetworkHandlers (Minecraft, Options...) when the registration line runs, i.e. on a dedicated server too;
 * a lambda body only touches it when a payload is actually handled on the client.
 */
public final class NetworkManager {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(DragonSetBurnBlockS2CPayload.ID, DragonSetBurnBlockS2CPayload.CODEC, (payload, ctx) -> ClientNetworkHandlers.handleDragonSetBurnBlock(payload, ctx));
        registrar.playToClient(LightningBoltS2CPayload.ID, LightningBoltS2CPayload.CODEC, (payload, ctx) -> ClientNetworkHandlers.handleLightningBolt(payload, ctx));
        registrar.playToClient(UpdatePixieHouseS2CPayload.ID, UpdatePixieHouseS2CPayload.CODEC, (payload, ctx) -> ClientNetworkHandlers.handleUpdatePixieHouse(payload, ctx));
        registrar.playToClient(UpdatePixieJarS2CPayload.ID, UpdatePixieJarS2CPayload.CODEC, (payload, ctx) -> ClientNetworkHandlers.handleUpdatePixieJar(payload, ctx));
        registrar.playToClient(UpdatePodiumS2CPayload.ID, UpdatePodiumS2CPayload.CODEC, (payload, ctx) -> ClientNetworkHandlers.handleUpdatePodium(payload, ctx));

        registrar.playToServer(DragonControlC2SPayload.ID, DragonControlC2SPayload.CODEC, ServerNetworkHandlers::handleDragonControl);

        registrar.playBidirectional(StartRidingMobPayload.ID, StartRidingMobPayload.CODEC, ServerNetworkHandlers::handleStartRidingMob, (payload, ctx) -> ClientNetworkHandlers.handleStartRidingMob(payload, ctx));
    }
}
