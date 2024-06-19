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
@Mixin(targets = "net/minecraft/client/gui/hud/spectator/SpectatorMenu$ChangePageSpectatorMenuCommand")
public abstract class ChangePageSpectatorMenuCommandMixin {
	@Unique private static final Identifier EBI_SCROLL_LEFT_TEXTURE = ModUtils.id("spectator/scroll_left");
	@Unique private static final Identifier EBI_SCROLL_RIGHT_TEXTURE = ModUtils.id("spectator/scroll_right");

	@ModifyArg(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyScrollLeftTexture(Identifier original) {
		return MinecraftClient.getInstance().interactionManager.isTenfoursized() ? EBI_SCROLL_LEFT_TEXTURE : original;
	}

	@ModifyArg(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyScrollRightTexture(Identifier original) {
		return MinecraftClient.getInstance().interactionManager.isTenfoursized() ? EBI_SCROLL_RIGHT_TEXTURE : original;
	}
}
