package io.github.ennuil.ennuis_bigger_inventories.api;

import net.minecraft.server.world.ServerWorld;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface(ServerWorld.class)
public interface EnnyServerWorld {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
