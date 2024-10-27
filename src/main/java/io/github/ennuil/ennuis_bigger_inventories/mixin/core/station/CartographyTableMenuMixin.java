package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CartographyTableMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CartographyTableMenu.class)
public abstract class CartographyTableMenuMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=15", ordinal = 0)
	)
	private int modifyMapSlotX(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 24 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=15", ordinal = 2)
	)
	private int modifyAdditionSlotX(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 24 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=145")
	)
	private int modifyOutputSlotX(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 154 : original;
	}

	// Quick Transfer
	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=30"))
	private int modify30(int original, Player player) {
		return player.level().inferTenfoursized() ? 3 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=39"))
	private int modify39(int original, Player player) {
		return player.level().inferTenfoursized() ? 3 + 10 * 4 : original;
	}
}
