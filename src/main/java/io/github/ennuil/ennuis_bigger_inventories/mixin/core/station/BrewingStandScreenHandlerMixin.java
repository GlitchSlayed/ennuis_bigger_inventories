package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.BrewingStandScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrewingStandScreenHandler.class)
public abstract class BrewingStandScreenHandlerMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V",
		at = @At(value = "CONSTANT", args = "intValue=56")
	)
	private int modifyPotion1X(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 66 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V",
		at = @At(value = "CONSTANT", args = "intValue=79")
	)
	private int modifyPotion2X(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 89 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V",
		at = @At(value = "CONSTANT", args = "intValue=102")
	)
	private int modifyPotion3X(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 112 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V",
		at = @At(value = "CONSTANT", args = "intValue=17", ordinal = 1)
	)
	private int modifyFuelX(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 27 : original;
	}


	// Quick Transfer
	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=32"))
	private int modifyHotbarBound(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 5 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=41"))
	private int modifyInventoryBound(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 5 + 10 * 4 : original;
	}
}
