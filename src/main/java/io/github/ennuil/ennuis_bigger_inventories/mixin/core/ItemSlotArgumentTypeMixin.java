package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.command.argument.ItemSlotArgumentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// I can't conditionally modify this argument without the hackjob kit, but the hackjob kit is too risky here,
// so just give expanded slots everywhere...?
// TODO - Revisit this for Minecraft 1.20.5
@Mixin(ItemSlotArgumentType.class)
public abstract class ItemSlotArgumentTypeMixin {
	@ModifyExpressionValue(method = "method_9472", at = @At(value = "CONSTANT", args = "intValue=9"))
	private static int modifyNines(int original) {
		return 10;
	}

	@ModifyExpressionValue(method = "method_9472", at = @At(value = "CONSTANT", args = "intValue=54"))
	private static int modify54(int original) {
		return 10 * 6;
	}

	@ModifyExpressionValue(method = "method_9472", at = @At(value = "CONSTANT", args = "intValue=27"))
	private static int modify27(int original) {
		return 10 * 3;
	}
}
