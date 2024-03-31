package io.github.ennuil.ennuis_bigger_inventories.api;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@ClientOnly
@InjectedInterface(ClientPlayerInteractionManager.class)
public interface EnnyClientPlayerInteractionManager {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
