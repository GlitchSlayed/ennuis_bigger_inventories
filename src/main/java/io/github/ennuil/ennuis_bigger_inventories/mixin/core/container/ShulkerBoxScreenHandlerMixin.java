package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ShulkerBoxScreenHandler.class)
public abstract class ShulkerBoxScreenHandlerMixin {
	@ModifyArg(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/inventory/SimpleInventory;<init>(I)V"
		)
	)
	private static int modifySize(int original, @Local(argsOnly = true) PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 * 3 : original;
	}

	@ModifyArg(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/screen/ShulkerBoxScreenHandler;checkSize(Lnet/minecraft/inventory/Inventory;I)V"
		),
		index = 1
	)
	private int modifySizeCheck(int original, @Local(argsOnly = true) PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 * 3 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}
}
