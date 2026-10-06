package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net/minecraft/client/gui/spectator/SpectatorMenu$ScrollMenuItem")
public abstract class ScrollMenuItemMixin {
	@Unique private static final Identifier EBI_SCROLL_LEFT_SPRITE = ModUtils.id("spectator/scroll_left");
	@Unique private static final Identifier EBI_SCROLL_RIGHT_SPRITE = ModUtils.id("spectator/scroll_right");

	@ModifyArg(
		method = "extractIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V"
		)
	)
	private Identifier modifyScrollLeftTexture(Identifier original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? EBI_SCROLL_LEFT_SPRITE : original;
	}

	@ModifyArg(
		method = "extractIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyScrollRightTexture(Identifier original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? EBI_SCROLL_RIGHT_SPRITE : original;
	}
}
