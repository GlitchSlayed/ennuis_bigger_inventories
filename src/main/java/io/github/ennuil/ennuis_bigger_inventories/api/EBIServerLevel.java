package io.github.ennuil.ennuis_bigger_inventories.api;

// An InjectedInterface for ServerLevel
public interface EBIServerLevel {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
