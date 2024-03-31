package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.GenericTensizedContainerScreenHandler;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/block/entity/BarrelBlockEntity$C_sybayene")
public abstract class C_sybayeneMixin {
	@Shadow(remap = false)
	@Final
	BarrelBlockEntity field_27208;

	@ModifyReturnValue(method = "isPlayerViewing", at = @At(value = "RETURN", ordinal = 1))
	private boolean modifyIsPlayerViewing(boolean original, PlayerEntity player) {
		if (!original) {
			if (player.currentScreenHandler instanceof GenericTensizedContainerScreenHandler handler) {
				return handler.getInventory() == field_27208;
			}
		}

		return original;
	}
}
