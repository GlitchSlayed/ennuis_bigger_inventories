package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.api.EnnyInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Inventory.class)
public abstract class InventoryMixin implements Container, EnnyInventory {
	@Shadow
	@Final
	@Mutable
	public NonNullList<ItemStack> items;

	@Shadow
	@Final
	public NonNullList<ItemStack> armor;

	@Shadow
	@Final
	public Player player;

	@Override
	public boolean isTenfoursized() {
		return this.player.level().inferTenfoursized();
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
		return this.items.size() + this.armor.size();
	}
}
