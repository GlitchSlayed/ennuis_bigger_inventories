package io.github.ennuil.ennuis_bigger_inventories.api;

// An injected interface for World
public interface EnnyWorld {
	default boolean inferTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
