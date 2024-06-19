package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(targets = "net/minecraft/client/gui/hud/spectator/SpectatorMenu$CloseSpectatorMenuCommand")
public abstract class CloseSpectatorMenuCommandMixin {
	@Unique private static final Identifier EBI_CLOSE_TEXTURE = ModUtils.id("spectator/close");

	@ModifyArg(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private Identifier modifyCloseTexture(Identifier original) {
		return MinecraftClient.getInstance().interactionManager.isTenfoursized() ? EBI_CLOSE_TEXTURE : original;
	}
}
