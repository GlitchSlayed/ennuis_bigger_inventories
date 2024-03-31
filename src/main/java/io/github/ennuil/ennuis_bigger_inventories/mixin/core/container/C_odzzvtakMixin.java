package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.GenericTensizedContainerScreenHandler;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.DoubleInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/block/entity/ChestBlockEntity$C_odzzvtak")
public abstract class C_odzzvtakMixin {
	@Shadow(remap = false)
	@Final
	ChestBlockEntity field_27211;

	@ModifyReturnValue(method = "isPlayerViewing", at = @At(value = "RETURN", ordinal = 1))
	private boolean modifyIsPlayerViewing(boolean original, PlayerEntity player) {
		if (!original) {
			if (player.currentScreenHandler instanceof GenericTensizedContainerScreenHandler handler) {
				var inventory = handler.getInventory();
				return inventory == field_27211 || inventory instanceof DoubleInventory && ((DoubleInventory) inventory).isPart(field_27211);
			}
		}

		return original;
	}
}
