package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DispenserMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DispenserMenu.class)
public abstract class DispenserMenuMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;)V",
		at = @At(value = "CONSTANT", args = "intValue=62")
	)
	private int modify62(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 71 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45(int original, Player player) {
		return player.level().inferTenfoursized() ? 9 + 10 * 4 : original;
	}
}
