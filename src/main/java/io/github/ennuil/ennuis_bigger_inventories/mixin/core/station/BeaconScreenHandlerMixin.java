package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.BeaconScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BeaconScreenHandler.class)
public abstract class BeaconScreenHandlerMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;Lnet/minecraft/screen/ScreenHandlerContext;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modify9(int original, int syncId, Inventory inventory) {
		// why did you cast it downnnnnnnnnnn
		return ((PlayerInventory) inventory).isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;Lnet/minecraft/screen/ScreenHandlerContext;)V",
		at = @At(value = "CONSTANT", args = "intValue=36")
	)
	private int modify36(int original, int syncId, Inventory inventory) {
		return ((PlayerInventory) inventory).isTenfoursized() ? 28 : original;
	}

	// Quick transfer shenanigans
	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=28"))
	private int modify28(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 1 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=37"))
	private int modify37(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 1 + 10 * 4 : original;
	}
}
