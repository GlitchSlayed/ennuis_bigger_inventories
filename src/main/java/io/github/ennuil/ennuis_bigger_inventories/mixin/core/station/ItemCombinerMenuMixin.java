package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemCombinerMenu.class)
public abstract class ItemCombinerMenuMixin {
	@Shadow
	@Final
	protected Player player;

	@ModifyExpressionValue(method = "createInventorySlots", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNinesOnAddSlots(int original, Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "getUseRowEnd", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNinesOnGetHotbarSlotsEnd(int original) {
		return this.player.level().inferTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "getInventorySlotEnd", at = @At(value = "CONSTANT", args = "intValue=27"))
	private int modify27(int original) {
		return this.player.level().inferTenfoursized() ? 10 * 3 : original;
	}
}
