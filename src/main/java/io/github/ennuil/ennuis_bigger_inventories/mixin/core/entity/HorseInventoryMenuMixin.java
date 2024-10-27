package io.github.ennuil.ennuis_bigger_inventories.mixin.core.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HorseInventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(HorseInventoryMenu.class)
public abstract class HorseInventoryMenuMixin {
	@ModifyExpressionValue(
		method = "<init>",
		at = @At(value = "CONSTANT", args = "intValue=8"),
		slice = @Slice(
			to = @At(
				value = "INVOKE_ASSIGN",
				target = "Lnet/minecraft/world/inventory/HorseInventoryMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;",
				ordinal = 1
			)
		)
	)
	private int modify8(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 15 : original;
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(value = "CONSTANT", args = "intValue=80")
	)
	private int modify80(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 91 : original;
	}

	@ModifyExpressionValue(
		method = "<init>",
		at = @At(value = "CONSTANT", args = "intValue=9")
	)
	private int modifyNines(int original, int syncId, Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}
}
