package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Generic3x3ContainerScreenHandler.class)
public abstract class Generic3x3ContainerScreenHandlerMixin {
	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;)V",
		at = @At(value = "CONSTANT", args = "intValue=9"),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/inventory/Inventory;onOpen(Lnet/minecraft/entity/player/PlayerEntity;)V"
			)
		)
	)
	private int modifyNines(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;)V",
		at = @At(value = "CONSTANT", args = "intValue=62")
	)
	private int modify62(int original, int syncId, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 71 : original;
	}

	@ModifyExpressionValue(method = "quickTransfer", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45(int original, PlayerEntity player) {
		return player.getWorld().inferTenfoursized() ? 9 + 10 * 4 : original;
	}
}
