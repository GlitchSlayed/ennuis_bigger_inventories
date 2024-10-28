package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.crafting.RecipeAccess;
import net.minecraft.world.item.crafting.RecipePropertySet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SmithingMenu.class)
public abstract class SmithingMenuMixin extends ItemCombinerMenu {
	@Unique
	private static ItemCombinerMenuSlotDefinition createTenfoursizedInputSlotDefinitions(RecipeAccess recipeAccess) {
		RecipePropertySet basePropertySet = recipeAccess.propertySet(RecipePropertySet.SMITHING_BASE);
		RecipePropertySet templatePropertySet = recipeAccess.propertySet(RecipePropertySet.SMITHING_TEMPLATE);
		RecipePropertySet additionPropertySet = recipeAccess.propertySet(RecipePropertySet.SMITHING_ADDITION);
		return ItemCombinerMenuSlotDefinition.create()
			.withSlot(0, 17, 40, templatePropertySet::test)
			.withSlot(1, 35, 40, basePropertySet::test)
			.withSlot(2, 53, 40, additionPropertySet::test)
			.withResultSlot(3, 111, 40)
			.build();
	}

	private SmithingMenuMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, ItemCombinerMenuSlotDefinition itemCombinerMenuSlotDefinition) {
		super(menuType, i, inventory, containerLevelAccess, itemCombinerMenuSlotDefinition);
	}

	@WrapOperation(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;Lnet/minecraft/world/level/Level;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/inventory/SmithingMenu;createInputSlotDefinitions(Lnet/minecraft/world/item/crafting/RecipeAccess;)Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition;"
		)
	)
	private static ItemCombinerMenuSlotDefinition tenfoursizeSlotDefinition(RecipeAccess recipeAccess, Operation<ItemCombinerMenuSlotDefinition> original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? createTenfoursizedInputSlotDefinitions(recipeAccess) : original.call(recipeAccess);
	}
}
