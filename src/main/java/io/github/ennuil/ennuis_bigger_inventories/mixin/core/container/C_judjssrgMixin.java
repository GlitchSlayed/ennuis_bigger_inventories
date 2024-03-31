package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.GenericTensizedContainerScreenHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// Friggin' nested anonymous classes, I hate you!
@Mixin(targets = "net/minecraft/block/ChestBlock$C_klhjloya$C_judjssrg")
public abstract class C_judjssrgMixin {
	@Shadow(remap = false)
	Inventory field_17360;

	@ModifyReturnValue(method = "createMenu", at = @At(value = "RETURN", ordinal = 0))
	private ScreenHandler return10x6ScreenHandler(ScreenHandler original, int syncId, PlayerInventory playerInventory) {
		if (playerInventory.isTenfoursized()) {
			return GenericTensizedContainerScreenHandler.createGeneric10x6(syncId, playerInventory, field_17360);
		} else {
			return original;
		}
	}
}
