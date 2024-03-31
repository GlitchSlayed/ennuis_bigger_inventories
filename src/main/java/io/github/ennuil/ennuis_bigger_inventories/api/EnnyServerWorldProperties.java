package io.github.ennuil.ennuis_bigger_inventories.api;

import net.minecraft.world.ServerWorldProperties;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface(ServerWorldProperties.class)
public interface EnnyServerWorldProperties {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
