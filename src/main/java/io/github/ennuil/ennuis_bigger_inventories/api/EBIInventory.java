package io.github.ennuil.ennuis_bigger_inventories.api;

// An injected interface for Inventory
public interface EBIInventory {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
