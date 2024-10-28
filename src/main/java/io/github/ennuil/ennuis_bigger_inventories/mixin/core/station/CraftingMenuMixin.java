package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CraftingMenu.class)
public abstract class CraftingMenuMixin {
	@Shadow
	@Final
	private Player player;

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=30")
	)
	private int modify30(int original) {
		return player.level().inferTenfoursized() ? 40 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=124")
	)
	private int modify124(int original) {
		return player.level().inferTenfoursized() ? 134 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=37"))
	private int modify37(int original) {
		return player.level().inferTenfoursized() ? 10 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=46"))
	private int modify46(int original) {
		return player.level().inferTenfoursized() ? 10 + 10 * 4 : original;
	}
}
