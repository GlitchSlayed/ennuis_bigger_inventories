package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindstoneScreenHandlerMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
		at = @At(value = "CONSTANT", args = "intValue=49")
	)
	private int modifyLeftSlotsX(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 58 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
		at = @At(value = "CONSTANT", args = "intValue=129")
	)
	private int modifyOutputSlotX(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 138 : original;
	}

	// Quick Transfer
	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=30"))
	private int modify30(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 3 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=39"))
	private int modify39(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 3 + 10 * 3 : original;
	}
}
