package io.github.ennuil.ennuis_bigger_inventories.mixin.core.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantMenu.class)
public abstract class MerchantMenuMixin {
	@Unique
	private Inventory inventory;

	@Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V", at = @At("TAIL"))
	private void setVariable(int syncId, Inventory inventory, Merchant merchant, CallbackInfo ci) {
		this.inventory = inventory;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V",
		at = @At(value = "CONSTANT", args = "intValue=136")
	)
	private int modify136(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 145 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V",
		at = @At(value = "CONSTANT", args = "intValue=162")
	)
	private int modify162(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 171 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V",
		at = @At(value = "CONSTANT", args = "intValue=220")
	)
	private int modify220(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 229 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/trading/Merchant;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=30"))
	private int modify30(int original) {
		return this.inventory.isTenfoursized() ? 3 + 10 * 3 : original;
	}

	// Hm, is this worth it?
	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=39"))
	private int modify39OnQuickTransfer(int original, Player player) {
		return player.level().inferTenfoursized() ? 3 + 10 * 4 : original;
	}

	@ModifyExpressionValue(method = {"tryMoveItems", "moveFromInventoryToPaymentSlot"}, at = @At(value = "CONSTANT", args = "intValue=39"))
	private int modify39Elsewhere(int original) {
		return this.inventory.isTenfoursized() ? 3 + 10 * 4 : original;
	}
}
