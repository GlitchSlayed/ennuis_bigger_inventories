package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChestMenu.class)
public abstract class ChestMenuMixin {
	@ModifyArg(
		method = "<init>(Lnet/minecraft/world/inventory/MenuType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;I)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/inventory/ChestMenu;addChestGrid(Lnet/minecraft/world/Container;II)V"
		),
		index = 1
	)
	private int modifyChestGridX(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 17 : original;
	}
}
