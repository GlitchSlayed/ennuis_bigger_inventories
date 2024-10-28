package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractFurnaceMenu.class)
public abstract class AbstractFurnaceMenuMixin {
	@ModifyArg(
		method = "<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/inventory/Slot;<init>(Lnet/minecraft/world/Container;III)V",
			ordinal = 0
		),
		index = 2
	)
	private int modifyX1(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 65 : original;
	}

	@ModifyArg(
		method = "<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/inventory/FurnaceFuelSlot;<init>(Lnet/minecraft/world/inventory/AbstractFurnaceMenu;Lnet/minecraft/world/Container;III)V"
		),
		index = 3
	)
	private int modifyX2(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 65 : original;
	}

	@ModifyArg(
		method = "<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/inventory/FurnaceResultSlot;<init>(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/Container;III)V"
		),
		index = 3
	)
	private int modifyX3(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 125 : original;
	}

	// 0 to 2 is furnace, 3 to 29 is inv body, 30 to 38 is hotbar
	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=30"))
	private int modify30(int original, Player player) {
		return player.level().inferTenfoursized() ? 3 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=39"))
	private int modify39(int original, Player player) {
		return player.level().inferTenfoursized() ? 3 + 10 * 4 : original;
	}
}
