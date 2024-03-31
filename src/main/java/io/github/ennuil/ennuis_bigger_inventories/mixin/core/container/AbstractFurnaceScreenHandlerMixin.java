package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractFurnaceScreenHandler.class)
public abstract class AbstractFurnaceScreenHandlerMixin {
	@ModifyArg(
		method = "<init>(Lnet/minecraft/screen/ScreenHandlerType;Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/recipe/book/RecipeBookCategory;ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/screen/slot/Slot;<init>(Lnet/minecraft/inventory/Inventory;III)V",
			ordinal = 0
		),
		index = 2
	)
	private int modifyX1(int original, @Local(argsOnly = true) PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 65 : original;
	}

	@ModifyArg(
		method = "<init>(Lnet/minecraft/screen/ScreenHandlerType;Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/recipe/book/RecipeBookCategory;ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/screen/slot/FurnaceFuelSlot;<init>(Lnet/minecraft/screen/AbstractFurnaceScreenHandler;Lnet/minecraft/inventory/Inventory;III)V"
		),
		index = 3
	)
	private int modifyX2(int original, @Local(argsOnly = true) PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 65 : original;
	}

	@ModifyArg(
		method = "<init>(Lnet/minecraft/screen/ScreenHandlerType;Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/recipe/book/RecipeBookCategory;ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/screen/slot/FurnaceOutputSlot;<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/inventory/Inventory;III)V"
		),
		index = 3
	)
	private int modifyX3(int original, @Local(argsOnly = true) PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 125 : original;
	}

	// Modify the relevant nines
	@ModifyExpressionValue(
		method = "<init>(Lnet/minecraft/screen/ScreenHandlerType;Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/recipe/book/RecipeBookCategory;ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V",
		at = @At(
			value = "CONSTANT",
			args = "intValue=9"
		)
	)
	private int modifyNine(int original, ScreenHandlerType<?> type, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookCategory category, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}

	// 0 to 2 is furnace, 3 to 29 is inv body, 30 to 38 is hotbar
	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=30"))
	private int modify30(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 3 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=39"))
	private int modify39(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 3 + 10 * 4 : original;
	}
}
