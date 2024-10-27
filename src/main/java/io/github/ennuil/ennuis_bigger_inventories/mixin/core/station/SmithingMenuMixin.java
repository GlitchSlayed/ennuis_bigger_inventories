package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SmithingMenu;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SmithingMenu.class)
public abstract class SmithingMenuMixin extends ItemCombinerMenu {
	// And with a "level" variable, you'd be screaming "Ennui! level! optimization!", but unfortunately,
	// it isn't even initialized by the time I have to apply these mixins!
	private SmithingMenuMixin(@Nullable MenuType<?> type, int syncId, Inventory inventory, ContainerLevelAccess context) {
		super(type, syncId, inventory, context);
	}

	@ModifyExpressionValue(method = "createInputSlotDefinitions", at = @At(value = "CONSTANT", args = "intValue=8"))
	private int modifyTemplateSlotX(int original) {
		return this.player.level().inferTenfoursized() ? 17 : original;
	}

	@ModifyExpressionValue(method = "createInputSlotDefinitions", at = @At(value = "CONSTANT", args = "intValue=26"))
	private int modifyBaseSlotX(int original) {
		return this.player.level().inferTenfoursized() ? 35 : original;
	}

	@ModifyExpressionValue(method = "createInputSlotDefinitions", at = @At(value = "CONSTANT", args = "intValue=44"))
	private int modifyAdditionSlotX(int original) {
		return this.player.level().inferTenfoursized() ? 53 : original;
	}

	@ModifyExpressionValue(method = "createInputSlotDefinitions", at = @At(value = "CONSTANT", args = "intValue=98"))
	private int modifyResultSlotX(int original) {
		return this.player.level().inferTenfoursized() ? 111 : original;
	}

	@ModifyExpressionValue(method = "createInputSlotDefinitions", at = @At(value = "CONSTANT", args = "intValue=48"))
	private int modifySlotsY(int original) {
		return this.player.level().inferTenfoursized() ? 40 : original;
	}
}
