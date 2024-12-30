package io.github.ennuil.ennuis_bigger_inventories.api;

// An injected interface for Level
public interface EBILevel {
	default boolean inferTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
