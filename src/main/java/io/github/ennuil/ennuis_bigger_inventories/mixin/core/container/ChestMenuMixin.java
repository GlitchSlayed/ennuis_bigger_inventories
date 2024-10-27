package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ChestMenu.class)
public abstract class ChestMenuMixin {
	@ModifyExpressionValue(
		method = "<init>(Lnet/minecraft/world/inventory/MenuType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;I)V",
		at = @At(
			value = "CONSTANT",
			args = "intValue=8",
			ordinal = 0
		)
	)
	private int modify8(int original, MenuType<?> type, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 17 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(Lnet/minecraft/world/inventory/MenuType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;I)V",
		at = @At(
			value = "CONSTANT",
			args = "intValue=9"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE_ASSIGN",
				target = "Lnet/minecraft/world/inventory/ChestMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;",
				ordinal = 0
			)
		)
	)
	private int modifyNines(int original, MenuType<?> type, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}
}
