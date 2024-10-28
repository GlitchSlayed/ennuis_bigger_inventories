package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(targets = "net/minecraft/client/gui/spectator/SpectatorMenu$ScrollMenuItem")
public abstract class ScrollMenuItemMixin {
	@Unique private static final ResourceLocation EBI_SCROLL_LEFT_SPRITE = ModUtils.id("spectator/scroll_left");
	@Unique private static final ResourceLocation EBI_SCROLL_RIGHT_SPRITE = ModUtils.id("spectator/scroll_right");

	@ModifyArg(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIII)V"
		)
	)
	private ResourceLocation modifyScrollLeftTexture(ResourceLocation original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? EBI_SCROLL_LEFT_SPRITE : original;
	}

	@ModifyArg(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIII)V",
			ordinal = 1
		)
	)
	private ResourceLocation modifyScrollRightTexture(ResourceLocation original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? EBI_SCROLL_RIGHT_SPRITE : original;
	}
}
