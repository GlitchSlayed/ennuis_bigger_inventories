package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.api.EnnyPlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements Inventory, EnnyPlayerInventory {
	@Shadow
	@Final
	@Mutable
	public DefaultedList<ItemStack> main;

	@Shadow
	@Final
	public DefaultedList<ItemStack> armor;

	@Shadow
	@Final
	public PlayerEntity player;

	@Override
	public boolean isTenfoursized() {
		return this.player.getWorld().inferTenfoursized();
	}

	// Static method, do not use!
	/*
	@ModifyExpressionValue(method = "getHotbarSize", at = @At(value = "CONSTANT", args = "intValue=9"))
	private static int modifyHotbarSize(int originalValue) {
		return 10;
	}
	*/

	@ModifyArg(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/collection/DefaultedList;ofSize(ILjava/lang/Object;)Lnet/minecraft/util/collection/DefaultedList;",
			ordinal = 0
		)
	)
	private int modifySize(int original, @Local(argsOnly = true) PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 10 * 4 : original;
	}

	// isValidHotbarIndex is static and should be replaced with non-static equivalents
	@ModifyExpressionValue(method = "isValidHotbarIndex", at = @At(value = "CONSTANT", args = "intValue=9"))
	private static int modifyNinesOnIsValidHotbarIndex(int original) {
		return 10;
	}

	@ModifyExpressionValue(method = {"getSwappableHotbarSlot", "scrollInHotbar"}, at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNines(int original) {
		return this.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "getOccupiedSlotWithRoomForStack", at = @At(value = "CONSTANT", args = "intValue=40"))
	private int modify40(int original) {
		return this.main.size() + this.armor.size();
	}
}
