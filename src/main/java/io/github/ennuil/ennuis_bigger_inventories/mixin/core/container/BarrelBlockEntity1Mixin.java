package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.TenfoursizedContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft/world/level/block/entity/BarrelBlockEntity$1")
public abstract class BarrelBlockEntity1Mixin {
	@Shadow(remap = false)
	@Final
	BarrelBlockEntity field_27208;

	@ModifyReturnValue(method = "isOwnContainer", at = @At(value = "RETURN", ordinal = 1))
	private boolean modifyIsPlayerViewing(boolean original, Player player) {
		if (!original) {
			if (player.containerMenu instanceof TenfoursizedContainerMenu menu) {
				return menu.getContainer() == field_27208;
			}
		}

		return original;
	}
}
