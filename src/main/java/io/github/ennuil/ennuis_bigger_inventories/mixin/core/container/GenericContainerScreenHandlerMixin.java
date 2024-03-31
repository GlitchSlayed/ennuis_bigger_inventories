package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(GenericContainerScreenHandler.class)
public abstract class GenericContainerScreenHandlerMixin {
	@ModifyExpressionValue(
		method = "<init>(Lnet/minecraft/screen/ScreenHandlerType;ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;I)V",
		at = @At(
			value = "CONSTANT",
			args = "intValue=8",
			ordinal = 0
		)
	)
	private int modify8(int original, ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 17 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(Lnet/minecraft/screen/ScreenHandlerType;ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;I)V",
		at = @At(
			value = "CONSTANT",
			args = "intValue=9"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE_ASSIGN",
				target = "Lnet/minecraft/screen/GenericContainerScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
				ordinal = 0
			)
		)
	)
	private int modifyNines(int original, ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}
}
