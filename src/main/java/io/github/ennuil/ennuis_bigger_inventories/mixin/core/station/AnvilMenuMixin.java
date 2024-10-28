package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
	@Unique
	private static ItemCombinerMenuSlotDefinition createTenfoursizedInputSlotDefinitions() {
		return ItemCombinerMenuSlotDefinition.create()
			.withSlot(0, 36, 47, itemStack -> true)
			.withSlot(1, 85, 47, itemStack -> true)
			.withResultSlot(2, 143, 47)
			.build();
	}

	private AnvilMenuMixin(@Nullable MenuType<?> menuType, int syncId, Inventory inventory, ContainerLevelAccess containerLevelAccess, ItemCombinerMenuSlotDefinition itemCombinerMenuSlotDefinition) {
		super(menuType, syncId, inventory, containerLevelAccess, itemCombinerMenuSlotDefinition);
	}

	@WrapOperation(
		method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/inventory/AnvilMenu;createInputSlotDefinitions()Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition;"
		)
	)
	private static ItemCombinerMenuSlotDefinition tenfoursizeSlotDefinition(Operation<ItemCombinerMenuSlotDefinition> original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? createTenfoursizedInputSlotDefinitions() : original.call();
	}
}
