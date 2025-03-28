package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.api.EBIInventory;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Inventory.class)
public abstract class InventoryMixin implements Container, EBIInventory {
	@Shadow
	@Final
	public Player player;

	@Shadow
	private int selected;

	@Override
	public boolean isTenfoursized() {
		return this.player.level().inferTenfoursized();
	}

	@ModifyExpressionValue(method = "setSelectedSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;isHotbarSlot(I)Z"))
	private boolean modifyIsHotbarSlotOnSetSelected(boolean original) {
		return this.isTenfoursized() ? this.selected >= 0 && this.selected < 10 : original;
	}

	// Static method, do not use!
	/*
	@ModifyExpressionValue(method = "getSelectionSize", at = @At(value = "CONSTANT", args = "intValue=9"))
	private static int modifyHotbarSize(int originalValue) {
		return 10;
	}
	*/

	@ModifyArg(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;",
			ordinal = 0
		)
	)
	private int modifySize(int original, @Local(argsOnly = true) Player player) {
		return player.level().inferTenfoursized() ? 10 * 4 : original;
	}

	// isValidHotbarIndex is static and should be replaced with non-static equivalents
	@ModifyExpressionValue(method = "isHotbarSlot", at = @At(value = "CONSTANT", args = "intValue=9"))
	private static int modifyNinesOnIsValidHotbarIndex(int original) {
		return 10;
	}

	@ModifyExpressionValue(method = {"getSuitableHotbarSlot"}, at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNines(int original) {
		return this.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "getSlotWithRemainingSpace", at = @At(value = "CONSTANT", args = "intValue=40"))
	private int modify40(int original) {
		return this.isTenfoursized() ? 44 : original;
	}

	// Hacky, yes, but reliable
	@ModifyArg(
		method = {
			"removeItem(II)Lnet/minecraft/world/item/ItemStack;",
			"removeItemNoUpdate",
			"setItem",
			"getItem"
		},
		at = @At(
			value = "INVOKE",
			target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;get(I)Ljava/lang/Object;"
		),
		index = 0
	)
	private int modifyOffsets(int original) {
		return this.isTenfoursized() ? original - 4 : original;
	}
}
