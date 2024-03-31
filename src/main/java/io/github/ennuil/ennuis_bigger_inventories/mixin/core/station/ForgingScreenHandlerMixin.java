package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ForgingScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ForgingScreenHandler.class)
public abstract class ForgingScreenHandlerMixin {
	@Shadow
	@Final
	protected PlayerEntity player;

	@ModifyExpressionValue(method = "addSlots", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNinesOnAddSlots(int original, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "getHotbarSlotsEnd", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNinesOnGetHotbarSlotsEnd(int original) {
		return this.player.getWorld().inferTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "getInventorySlotsEnd", at = @At(value = "CONSTANT", args = "intValue=27"))
	private int modify27(int original) {
		return this.player.getWorld().inferTenfoursized() ? 10 * 3 : original;
	}
}
