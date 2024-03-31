package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
	private AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@ModifyExpressionValue(method = "createSlotManager", at = @At(value = "CONSTANT", args = "intValue=27"))
	private int modifyIngredientSlot1X(int original) {
		return this.player.getWorld().inferTenfoursized() ? 36 : original;
	}

	@ModifyExpressionValue(method = "createSlotManager", at = @At(value = "CONSTANT", args = "intValue=76"))
	private int modifyIngredientSlot2X(int original) {
		return this.player.getWorld().inferTenfoursized() ? 85 : original;
	}

	@ModifyExpressionValue(method = "createSlotManager", at = @At(value = "CONSTANT", args = "intValue=134"))
	private int modifyResultSlotX(int original) {
		return this.player.getWorld().inferTenfoursized() ? 143 : original;
	}
}
