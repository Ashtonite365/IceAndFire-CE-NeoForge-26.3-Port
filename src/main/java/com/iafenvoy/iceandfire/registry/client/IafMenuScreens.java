package com.iafenvoy.iceandfire.registry.client;

import com.iafenvoy.iceandfire.registry.IafMenus;
import com.iafenvoy.iceandfire.screen.gui.*;
import com.iafenvoy.iceandfire.screen.gui.bestiary.BestiaryScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

/** Client-only: binds IafMenus types to their screens. Only loaded on Dist.CLIENT. */
@EventBusSubscriber(value = Dist.CLIENT)
public final class IafMenuScreens {
    @SubscribeEvent
    public static void registerGui(RegisterMenuScreensEvent event) {
        event.register(IafMenus.IAF_LECTERN_SCREEN.get(), LecternScreen::new);
        event.register(IafMenus.PODIUM_SCREEN.get(), PodiumScreen::new);
        event.register(IafMenus.DRAGON_SCREEN.get(), DragonScreen::new);
        event.register(IafMenus.HIPPOGRYPH_SCREEN.get(), HippogryphScreen::new);
        event.register(IafMenus.HIPPOCAMPUS_SCREEN.get(), HippocampusScreen::new);
        event.register(IafMenus.DRAGON_FORGE_SCREEN.get(), DragonForgeScreen::new);
        event.register(IafMenus.BESTIARY_SCREEN.get(), BestiaryScreen::new);
    }
}
