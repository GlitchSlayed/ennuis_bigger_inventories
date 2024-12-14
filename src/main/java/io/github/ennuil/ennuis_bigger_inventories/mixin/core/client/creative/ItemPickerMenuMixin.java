package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
public abstract class ItemPickerMenuMixin {
	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=9", ordinal = 0))
	private int modifyInitNine0(int original, Player player) {
		return player.level().inferTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=9", ordinal = 1))
	private int modifyInitNine1(int original, Player player) {
		return player.level().inferTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = {
			"calculateRowCount",
			"scrollTo",
			"quickMoveStack"
		},
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original) {
		return CreativeModeInventoryScreenAccessor.getContainer().getContainerSize() == 50 ? 10 : original;
	}

	@ModifyExpressionValue(method = "canScroll", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45(int original) {
		return CreativeModeInventoryScreenAccessor.getContainer().getContainerSize() == 50 ? 10 * 5 : original;
	}
}
