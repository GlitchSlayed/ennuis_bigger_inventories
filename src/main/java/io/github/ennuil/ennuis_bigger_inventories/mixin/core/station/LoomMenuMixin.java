package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.LoomMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LoomMenu.class)
public abstract class LoomMenuMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=143")
	)
	private int modifyOutputSlotX(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 161 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=31"))
	private int modify31(int original, Player player) {
		return player.level().inferTenfoursized() ? 4 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=40"))
	private int modify40(int original, Player player) {
		return player.level().inferTenfoursized() ? 4 + 10 * 4 : original;
	}
}
