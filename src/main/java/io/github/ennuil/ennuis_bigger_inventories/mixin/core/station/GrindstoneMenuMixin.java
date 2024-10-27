package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.GrindstoneMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GrindstoneMenu.class)
public abstract class GrindstoneMenuMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=49")
	)
	private int modifyLeftSlotsX(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 58 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=129")
	)
	private int modifyOutputSlotX(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 138 : original;
	}

	// Quick Transfer
	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=30"))
	private int modify30(int original, Player player) {
		return player.level().inferTenfoursized() ? 3 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=39"))
	private int modify39(int original, Player player) {
		return player.level().inferTenfoursized() ? 3 + 10 * 3 : original;
	}
}
