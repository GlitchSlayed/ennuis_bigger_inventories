package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ShulkerBoxMenu.class)
public abstract class ShulkerBoxMenuMixin {
	@ModifyArg(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/SimpleContainer;<init>(I)V"
		)
	)
	private static int modifySize(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 10 * 3 : original;
	}

	@ModifyArg(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/inventory/ShulkerBoxMenu;checkContainerSize(Lnet/minecraft/world/Container;I)V"
		)
	)
	private int modifySizeCheck(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 10 * 3 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}
}
