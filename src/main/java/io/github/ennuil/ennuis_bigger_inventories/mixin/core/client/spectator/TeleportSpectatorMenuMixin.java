package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.spectator.TeleportSpectatorMenu;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(TeleportSpectatorMenu.class)
public abstract class TeleportSpectatorMenuMixin {
	@Unique private static final Identifier EBI_TELEPORT_TO_PLAYER_TEXTURE = new Identifier("ennuis_bigger_inventories", "spectator/teleport_to_player");

	@WrapOperation(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIFFIIII)V"
		)
	)
	private void modifyTeleportToPlayerTexture(GuiGraphics graphics, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (MinecraftClient.getInstance().interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_TELEPORT_TO_PLAYER_TEXTURE, x,  y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height, textureWidth, textureHeight);
		}
	}
}
