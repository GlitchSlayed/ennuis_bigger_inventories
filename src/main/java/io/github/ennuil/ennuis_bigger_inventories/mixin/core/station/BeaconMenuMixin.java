package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.BeaconMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BeaconMenu.class)
public abstract class BeaconMenuMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=36")
	)
	private int modify36(int original, int syncId, Container container) {
		return ((Inventory) container).isTenfoursized() ? 28 : original;
	}

	// Quick transfer shenanigans
	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=28"))
	private int modify28(int original, Player player) {
		return player.level().inferTenfoursized() ? 1 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=37"))
	private int modify37(int original, Player player) {
		return player.level().inferTenfoursized() ? 1 + 10 * 4 : original;
	}
}
