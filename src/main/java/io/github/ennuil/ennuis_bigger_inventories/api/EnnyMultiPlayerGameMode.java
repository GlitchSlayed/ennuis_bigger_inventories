package io.github.ennuil.ennuis_bigger_inventories.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
// An InjectedInterface for ClientPlayerInteractionManager
public interface EnnyMultiPlayerGameMode {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
