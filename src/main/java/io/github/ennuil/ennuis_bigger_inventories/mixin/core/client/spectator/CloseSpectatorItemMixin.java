package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net/minecraft/client/gui/spectator/SpectatorMenu$CloseSpectatorItem")
public abstract class CloseSpectatorItemMixin {
	@Unique private static final ResourceLocation EBI_CLOSE_SPRITE = ModUtils.id("spectator/close");

	@ModifyArg(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIII)V"
		)
	)
	private ResourceLocation modifyCloseTexture(ResourceLocation original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? EBI_CLOSE_SPRITE : original;
	}
}
