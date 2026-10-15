package com.iafenvoy.iceandfire.registry;

import com.iafenvoy.iceandfire.event.handler.ClientEvents;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import com.mojang.blaze3d.platform.InputConstants;

@EventBusSubscriber(Dist.CLIENT)
public final class IafKeyMappings {
    public static final KeyMapping DRAGON_BREATH = new KeyMapping("key.dragon_fireAttack", InputConstants.KEY_R, KeyMapping.Category.GAMEPLAY);
    public static final KeyMapping DRAGON_STRIKE = new KeyMapping("key.dragon_strike", InputConstants.KEY_G, KeyMapping.Category.GAMEPLAY);
    public static final KeyMapping DRAGON_DOWN = new KeyMapping("key.dragon_down", InputConstants.KEY_X, KeyMapping.Category.GAMEPLAY);
    public static final KeyMapping DRAGON_CHANGE_VIEW = new KeyMapping("key.dragon_change_view", InputConstants.KEY_F7, KeyMapping.Category.GAMEPLAY);

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(DRAGON_BREATH);
        event.register(DRAGON_STRIKE);
        event.register(DRAGON_DOWN);
        event.register(DRAGON_CHANGE_VIEW);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (DRAGON_CHANGE_VIEW.consumeClick()) {
            if (ClientEvents.currentView + 1 > 3) ClientEvents.currentView = 0;
            else ClientEvents.currentView++;
        }
    }
}
