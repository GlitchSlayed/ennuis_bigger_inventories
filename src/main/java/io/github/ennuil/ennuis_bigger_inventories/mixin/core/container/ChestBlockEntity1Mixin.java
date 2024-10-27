package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.TenfoursizedContainerMenu;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/world/level/block/entity/ChestBlockEntity$1")
public abstract class ChestBlockEntity1Mixin {
	@Shadow(remap = false)
	@Final
	ChestBlockEntity field_27211;

	@ModifyReturnValue(method = "isOwnContainer", at = @At(value = "RETURN", ordinal = 1))
	private boolean modifyIsPlayerViewing(boolean original, Player player) {
		if (!original) {
			if (player.containerMenu instanceof TenfoursizedContainerMenu menu) {
				var inventory = menu.getContainer();
				return inventory == field_27211 || inventory instanceof CompoundContainer && ((CompoundContainer) inventory).contains(field_27211);
			}
		}

		return original;
	}
}
