package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.PlayerScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin {
	@Shadow
	@Final
	private PlayerEntity owner;

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=98"))
	private int modifyInputSlotXs(int original) {
		return this.owner.getInventory().isTenfoursized() ? 116 : original;
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=154"))
	private int modifyResultSlotX(int original) {
		return this.owner.getInventory().isTenfoursized() ? 172 : original;
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNines(int original) {
		return this.owner.getInventory().isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=39"))
	private int modify39(int original) {
		return this.owner.getInventory().isTenfoursized() ? 39 + 4 : original;
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=40"))
	private int modify40Constant(int original) {
		return this.owner.getInventory().isTenfoursized() ? 40 + 4 : original;
	}

	// isHotbarSlot is a static method, and therefore, its usages should be replaced with non-static ones
	// TODO - Use MinecraftClient.getInstance()! It's a fallback anyway!
	/*
	@ModifyExpressionValue(method = "isHotbarSlot", at = @At(value = "CONSTANT", args = "intValue=36"))
	private static int modify36OnIsHotbarSlot(int original) {
		return 9 + 10 * 3;
	}

	@ModifyExpressionValue(method = "isHotbarSlot", at = @At(value = "CONSTANT", args = "intValue=45"))
	private static int modify45OnIsHotbarSlot(int original) {
		return 9 + 10 * 4;
	}
	*/

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=36"))
	private int modify36OnQuickTransfer(int original) {
		return this.owner.getInventory().isTenfoursized() ? 9 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45OnQuickTransfer(int original) {
		return this.owner.getInventory().isTenfoursized() ? 9 + 10 * 4 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=46"))
	private int modify46(int original) {
		return this.owner.getInventory().isTenfoursized() ? 9 + 10 * 4 + 1: original;
	}
}
