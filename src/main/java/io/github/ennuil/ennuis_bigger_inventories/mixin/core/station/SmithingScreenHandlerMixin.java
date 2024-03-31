package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SmithingScreenHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SmithingScreenHandler.class)
public abstract class SmithingScreenHandlerMixin extends ForgingScreenHandler {
	// And with a "world" variable, you'd be screaming "Ennui! world! optimization!", but unfortunately,
	// it isn't even initialized by the time I have to apply these mixins!
	private SmithingScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@ModifyExpressionValue(method = "createSlotManager", at = @At(value = "CONSTANT", args = "intValue=8"))
	private int modifyTemplateSlotX(int original) {
		return this.player.getWorld().inferTenfoursized() ? 17 : original;
	}

	@ModifyExpressionValue(method = "createSlotManager", at = @At(value = "CONSTANT", args = "intValue=26"))
	private int modifyBaseSlotX(int original) {
		return this.player.getWorld().inferTenfoursized() ? 35 : original;
	}

	@ModifyExpressionValue(method = "createSlotManager", at = @At(value = "CONSTANT", args = "intValue=44"))
	private int modifyAdditionSlotX(int original) {
		return this.player.getWorld().inferTenfoursized() ? 53 : original;
	}

	@ModifyExpressionValue(method = "createSlotManager", at = @At(value = "CONSTANT", args = "intValue=98"))
	private int modifyResultSlotX(int original) {
		return this.player.getWorld().inferTenfoursized() ? 111 : original;
	}

	@ModifyExpressionValue(method = "createSlotManager", at = @At(value = "CONSTANT", args = "intValue=48"))
	private int modifySlotsY(int original) {
		return this.player.getWorld().inferTenfoursized() ? 40 : original;
	}
}
