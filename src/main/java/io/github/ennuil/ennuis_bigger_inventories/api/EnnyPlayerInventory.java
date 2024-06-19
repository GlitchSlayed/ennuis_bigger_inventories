package io.github.ennuil.ennuis_bigger_inventories.api;

// An injected interface for PlayerInventory
public interface EnnyPlayerInventory {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
