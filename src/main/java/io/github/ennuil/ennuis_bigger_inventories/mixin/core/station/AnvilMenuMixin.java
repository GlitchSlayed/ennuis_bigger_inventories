package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
	private AnvilMenuMixin(@Nullable MenuType<?> type, int syncId, Inventory inventory, ContainerLevelAccess levelAccess) {
		super(type, syncId, inventory, levelAccess);
	}

	@ModifyExpressionValue(method = "createInputSlotDefinitions", at = @At(value = "CONSTANT", args = "intValue=27"))
	private int modifyIngredientSlot1X(int original) {
		return this.player.level().inferTenfoursized() ? 36 : original;
	}

	@ModifyExpressionValue(method = "createInputSlotDefinitions", at = @At(value = "CONSTANT", args = "intValue=76"))
	private int modifyIngredientSlot2X(int original) {
		return this.player.level().inferTenfoursized() ? 85 : original;
	}

	@ModifyExpressionValue(method = "createInputSlotDefinitions", at = @At(value = "CONSTANT", args = "intValue=134"))
	private int modifyResultSlotX(int original) {
		return this.player.level().inferTenfoursized() ? 143 : original;
	}
}
