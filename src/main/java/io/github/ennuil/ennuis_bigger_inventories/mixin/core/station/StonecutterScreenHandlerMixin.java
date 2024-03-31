package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StonecutterScreenHandler.class)
public abstract class StonecutterScreenHandlerMixin {
	// This World!!!
	@Shadow
	@Final
	private World world;

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
		at = @At(value = "CONSTANT", args = "intValue=143")
	)
	private int modifyOutputSlotX(int original) {
		return this.world.inferTenfoursized() ? 161 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original) {
		return this.world.inferTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=29"))
	private int modify31(int original) {
		return this.world.inferTenfoursized() ? 2 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=38"))
	private int modify40(int original) {
		return this.world.inferTenfoursized() ? 2 + 10 * 4 : original;
	}
}
