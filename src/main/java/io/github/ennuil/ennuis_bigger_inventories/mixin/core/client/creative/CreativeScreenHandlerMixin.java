package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
public abstract class CreativeScreenHandlerMixin {
	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=9", ordinal = 0))
	private int modifyInitNine0(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=9", ordinal = 1))
	private int modifyInitNine1(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=9", ordinal = 3))
	private int modifyInitNine3(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = {
			"calculateExtraRows",
			"scrollItems",
			"quickTransfer"
		},
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original) {
		return CreativeInventoryScreenAccessor.getInventory().size() == 50 ? 10 : original;
	}

	@ModifyExpressionValue(method = "shouldShowScrollbar", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45(int original) {
		return CreativeInventoryScreenAccessor.getInventory().size() == 50 ? 10 * 5 : original;
	}
}
