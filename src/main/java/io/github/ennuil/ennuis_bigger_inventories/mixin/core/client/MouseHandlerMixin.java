package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
	@ModifyExpressionValue(
		method = "onScroll",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/player/Inventory;getSelectionSize()I"
		)
	)
	private int modifyGetSelectionSizeOnScroll(int original, @Local Inventory inventory) {
		return inventory.isTenfoursized() ? 10 : original;
	}
}
