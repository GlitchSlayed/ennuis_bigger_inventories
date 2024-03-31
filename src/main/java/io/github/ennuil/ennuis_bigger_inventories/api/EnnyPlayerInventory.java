package io.github.ennuil.ennuis_bigger_inventories.api;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;

import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface(PlayerInventory.class)
public interface EnnyPlayerInventory {
	default boolean isTenfoursized() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
