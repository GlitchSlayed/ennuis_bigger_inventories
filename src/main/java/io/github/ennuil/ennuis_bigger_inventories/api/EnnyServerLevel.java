package io.github.ennuil.ennuis_bigger_inventories.api;

// An InjectedInterface for ServerWorld
public interface EnnyServerLevel {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
