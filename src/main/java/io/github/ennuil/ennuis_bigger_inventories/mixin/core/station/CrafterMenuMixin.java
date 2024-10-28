package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CrafterMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CrafterMenu.class)
public abstract class CrafterMenuMixin {
	@ModifyExpressionValue(method = "addSlots", at = @At(value = "CONSTANT", args = "intValue=26"))
	private int modify26(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 26 + 9 - 2 : original;
	}

	@ModifyExpressionValue(method = "addSlots", at = @At(value = "CONSTANT", args = "intValue=134"))
	private int modify134(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 134 + 9 - 2 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45(int original, @Local(argsOnly = true) Player player) {
		return player.level().inferTenfoursized() ? 9 + 10 * 4 : original;
	}
}
