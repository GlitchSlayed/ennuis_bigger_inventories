package io.github.ennuil.ennuis_bigger_inventories.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

// An InjectedInterface for MultiPlayerGameMode
@Environment(EnvType.CLIENT)
public interface EBIMultiPlayerGameMode {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
