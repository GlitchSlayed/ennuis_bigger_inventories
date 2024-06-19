package io.github.ennuil.ennuis_bigger_inventories.api;

// An injected interface for ServerWorldProperties
public interface EnnyServerWorldProperties {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
