package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(targets = "net/minecraft/client/gui/hud/spectator/SpectatorMenu$ChangePageSpectatorMenuCommand")
public abstract class ChangePageSpectatorMenuCommandMixin {
	@Unique private static final Identifier EBI_SCROLL_LEFT_TEXTURE = new Identifier("ennuis_bigger_inventories", "spectator/scroll_left");
	@Unique private static final Identifier EBI_SCROLL_RIGHT_TEXTURE = new Identifier("ennuis_bigger_inventories", "spectator/scroll_right");

	@WrapOperation(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIFFIIII)V",
			ordinal = 0
		)
	)
	private void modifyScrollLeftTexture(GuiGraphics graphics, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (MinecraftClient.getInstance().interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_SCROLL_LEFT_TEXTURE, x,  y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height, textureWidth, textureHeight);
		}
	}

	@WrapOperation(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIFFIIII)V",
			ordinal = 1
		)
	)
	private void modifyScrollRightTexture(GuiGraphics graphics, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (MinecraftClient.getInstance().interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_SCROLL_RIGHT_TEXTURE, x,  y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height, textureWidth, textureHeight);
		}
	}
}
