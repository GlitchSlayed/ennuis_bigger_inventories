package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CrafterScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CrafterScreenHandler.class)
public abstract class CrafterScreenHandlerMixin {
	@ModifyExpressionValue(
		method = "addSlots",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, @Local(argsOnly = true) PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "addSlots", at = @At(value = "CONSTANT", args = "intValue=26"))
	private int modify26(int original, @Local(argsOnly = true) PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 26 + 9 - 2 : original;
	}

	@ModifyExpressionValue(method = "addSlots", at = @At(value = "CONSTANT", args = "intValue=134"))
	private int modify134(int original, @Local(argsOnly = true) PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 134 + 9 - 2 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45(int original, @Local(argsOnly = true) PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 9 + 10 * 4 : original;
	}
}
