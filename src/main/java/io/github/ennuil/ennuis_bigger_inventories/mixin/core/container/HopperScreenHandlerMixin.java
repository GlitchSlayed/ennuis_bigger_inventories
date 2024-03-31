package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.HopperScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HopperScreenHandler.class)
public abstract class HopperScreenHandlerMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;)V",
		at = @At(value = "CONSTANT", args = "intValue=44")
	)
	private int modify44(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 53 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}
}
