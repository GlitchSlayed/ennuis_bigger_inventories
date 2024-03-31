package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CraftingScreenHandler.class)
public abstract class CraftingScreenHandlerMixin {
	@Shadow
	@Final
	private PlayerEntity player;

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
		at = @At(
			value = "CONSTANT",
			args = "intValue=9"
		)
	)
	private int modifyNines(int original) {
		return player.getWorld().inferTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
		at = @At(value = "CONSTANT", args = "intValue=30")
	)
	private int modify30(int original) {
		return player.getWorld().inferTenfoursized() ? 40 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
		at = @At(value = "CONSTANT", args = "intValue=124")
	)
	private int modify124(int original) {
		return player.getWorld().inferTenfoursized() ? 134 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=37"))
	private int modify37(int original) {
		return player.getWorld().inferTenfoursized() ? 10 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=46"))
	private int modify46(int original) {
		return player.getWorld().inferTenfoursized() ? 10 + 10 * 4 : original;
	}
}
