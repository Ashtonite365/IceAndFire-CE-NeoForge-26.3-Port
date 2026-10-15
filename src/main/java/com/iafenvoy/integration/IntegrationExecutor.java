/*
 * Local stub of IAFEnvoy Integration's IntegrationExecutor (no 26.3 build). Personal Ice and Fire port only.
 * Runs the supplied action only when the given mod is loaded; the Supplier indirection keeps optional classes unloaded otherwise.
 */
package com.iafenvoy.integration;

import net.neoforged.fml.ModList;

import java.util.function.Supplier;

public final class IntegrationExecutor {
    private IntegrationExecutor() {}

    public static void runWhenLoad(String modId, Supplier<Runnable> action) {
        if (ModList.get().isLoaded(modId)) {
            action.get().run();
        }
    }
}
