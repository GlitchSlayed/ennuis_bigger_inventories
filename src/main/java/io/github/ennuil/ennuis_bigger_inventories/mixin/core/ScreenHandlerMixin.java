package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {
	@ModifyExpressionValue(method = "internalOnSlotClick", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNine(int original, @Local PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "internalOnSlotClick", at = @At(value = "CONSTANT", args = "intValue=40"))
	private int modify40(int original, @Local PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 44 : original;
	}
}
