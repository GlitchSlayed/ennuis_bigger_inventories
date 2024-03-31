package io.github.ennuil.ennuis_bigger_inventories.mixin.core.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.village.Merchant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreenHandler.class)
public abstract class MerchantScreenHandlerMixin {
	@Unique
	private PlayerInventory playerInventory;

	@Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/village/Merchant;)V", at = @At("TAIL"))
	private void setVariable(int syncId, PlayerInventory playerInventory, Merchant merchant, CallbackInfo ci) {
		this.playerInventory = playerInventory;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/village/Merchant;)V",
		at = @At(value = "CONSTANT", args = "intValue=136")
	)
	private int modify136(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 145 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/village/Merchant;)V",
		at = @At(value = "CONSTANT", args = "intValue=162")
	)
	private int modify162(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 171 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/village/Merchant;)V",
		at = @At(value = "CONSTANT", args = "intValue=220")
	)
	private int modify220(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 229 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/village/Merchant;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=30"))
	private int modify30(int original) {
		return this.playerInventory.isTenfoursized() ? 3 + 10 * 3 : original;
	}

	// Hm, is this worth it?
	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=39"))
	private int modify39OnQuickTransfer(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 3 + 10 * 4 : original;
	}

	@ModifyExpressionValue(method = {"switchTo", "autofill"}, at = @At(value = "CONSTANT", args = "intValue=39"))
	private int modify39Elsewhere(int original) {
		return this.playerInventory.isTenfoursized() ? 3 + 10 * 4 : original;
	}
}
