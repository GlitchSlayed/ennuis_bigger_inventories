package io.github.ennuil.ennuis_bigger_inventories.api;

import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
// An InjectedInterface for ClientPlayerInteractionManager
public interface EnnyClientPlayerInteractionManager {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
