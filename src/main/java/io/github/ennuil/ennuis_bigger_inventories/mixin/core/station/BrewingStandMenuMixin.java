package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.BrewingStandMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrewingStandMenu.class)
public abstract class BrewingStandMenuMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V",
		at = @At(value = "CONSTANT", args = "intValue=56")
	)
	private int modifyPotion1X(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 66 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V",
		at = @At(value = "CONSTANT", args = "intValue=79")
	)
	private int modifyPotion2X(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 89 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V",
		at = @At(value = "CONSTANT", args = "intValue=102")
	)
	private int modifyPotion3X(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 112 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V",
		at = @At(value = "CONSTANT", args = "intValue=17", ordinal = 1)
	)
	private int modifyFuelX(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 27 : original;
	}


	// Quick Transfer
	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=32"))
	private int modifyHotbarBound(int original, Player player) {
		return player.level().inferTenfoursized() ? 5 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=41"))
	private int modifyInventoryBound(int original, Player player) {
		return player.level().inferTenfoursized() ? 5 + 10 * 4 : original;
	}
}
