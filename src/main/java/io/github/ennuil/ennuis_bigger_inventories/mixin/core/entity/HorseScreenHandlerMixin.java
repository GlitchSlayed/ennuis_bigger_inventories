package io.github.ennuil.ennuis_bigger_inventories.mixin.core.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.HorseScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(HorseScreenHandler.class)
public abstract class HorseScreenHandlerMixin {
	@ModifyExpressionValue(
		method = "<init>",
		at = @At(value = "CONSTANT", args = "intValue=8"),
		slice = @Slice(
			to = @At(
				value = "INVOKE_ASSIGN",
				target = "Lnet/minecraft/screen/HorseScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
				ordinal = 1
			)
		)
	)
	private int modify8(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 15 : original;
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(value = "CONSTANT", args = "intValue=80")
	)
	private int modify80(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 91 : original;
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}
}
