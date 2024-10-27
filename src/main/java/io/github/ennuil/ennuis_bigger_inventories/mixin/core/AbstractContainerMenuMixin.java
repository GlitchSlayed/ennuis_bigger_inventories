package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin {
	@ModifyExpressionValue(method = "doClick", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNine(int original, @Local Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "doClick", at = @At(value = "CONSTANT", args = "intValue=40"))
	private int modify40(int original, @Local Inventory inventory) {
		return inventory.isTenfoursized() ? 44 : original;
	}
}
