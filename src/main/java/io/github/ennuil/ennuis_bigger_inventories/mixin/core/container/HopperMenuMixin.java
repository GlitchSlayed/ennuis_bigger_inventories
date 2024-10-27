package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HopperMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HopperMenu.class)
public abstract class HopperMenuMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;)V",
		at = @At(value = "CONSTANT", args = "intValue=44")
	)
	private int modify44(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 53 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}
}
