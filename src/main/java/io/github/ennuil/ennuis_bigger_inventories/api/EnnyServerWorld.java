package io.github.ennuil.ennuis_bigger_inventories.api;

// An InjectedInterface for ServerWorld
public interface EnnyServerWorld {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
