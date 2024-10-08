package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.api.HackjobKit;
import net.minecraft.inventory.SlotRanges;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SlotRanges.class)
public abstract class SlotRangesMixin {
	@ModifyExpressionValue(method = "method_58084", at = @At(value = "CONSTANT", args = "intValue=9"))
	private static int modifyNines(int original) {
		return HackjobKit.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "method_58084", at = @At(value = "CONSTANT", args = "intValue=27"))
	private static int modify27(int original) {
		return HackjobKit.isTenfoursized() ? 30 : original;
	}

	@ModifyExpressionValue(method = "method_58084", at = @At(value = "CONSTANT", args = "intValue=54"))
	private static int modify54(int original) {
		return HackjobKit.isTenfoursized() ? 60 : original;
	}
}
