package io.github.ennuil.ennuis_bigger_inventories.api;

import net.minecraft.world.World;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface(World.class)
public interface EnnyWorld {
	default boolean inferTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
