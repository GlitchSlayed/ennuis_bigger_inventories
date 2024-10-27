package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.TenfoursizedContainerMenu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// Friggin' nested anonymous classes, I hate you!
@Mixin(targets = "net/minecraft/world/level/block/ChestBlock$2$1")
public abstract class ChestBlock21Mixin {
	@Shadow(remap = false)
	Container field_17360;

	@ModifyReturnValue(method = "createMenu", at = @At(value = "RETURN", ordinal = 0))
	private AbstractContainerMenu returnTenfoursizedMenu(AbstractContainerMenu original, int syncId, Inventory inventory) {
		if (inventory.isTenfoursized()) {
			return TenfoursizedContainerMenu.sixRows(syncId, inventory, field_17360);
		} else {
			return original;
		}
	}
}
