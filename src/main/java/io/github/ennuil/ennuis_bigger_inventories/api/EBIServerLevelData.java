package io.github.ennuil.ennuis_bigger_inventories.api;

// An injected interface for ServerLevelData
public interface EBIServerLevelData {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
