package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.spectator.SpectatorHud;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(SpectatorHud.class)
public abstract class SpectatorHudMixin {
	@Unique private static final Identifier EBI_HOTBAR_TEXTURE = ModUtils.id("hud/hotbar");
	@Unique private static final Identifier EBI_HOTBAR_SELECTION_TEXTURE = ModUtils.id("hud/hotbar_selection");

	@Shadow
	@Final
	private MinecraftClient client;

	@ModifyArg(
		method = "renderSpectatorMenu",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyHotbar(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_HOTBAR_TEXTURE : original;
	}

	@ModifyArg(
		method = "renderSpectatorMenu",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyHotbarSelection(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_HOTBAR_SELECTION_TEXTURE : original;
	}

	@ModifyExpressionValue(method = "renderSpectatorMenu", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNine(int original) {
		return this.client.interactionManager.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "renderSpectatorMenu", at = @At(value = "CONSTANT", args = "intValue=90"))
	private int modify90(int original) {
		return this.client.interactionManager.isTenfoursized() ? 100 : original;
	}

	@ModifyExpressionValue(method = "renderSpectatorMenu", at = @At(value = "CONSTANT", args = "intValue=91"))
	private int modify91(int original) {
		return this.client.interactionManager.isTenfoursized() ? 101 : original;
	}

	@ModifyExpressionValue(method = "renderSpectatorMenu", at = @At(value = "CONSTANT", args = "intValue=182"))
	private int modify182(int original) {
		return this.client.interactionManager.isTenfoursized() ? 202 : original;
	}

	@ModifyExpressionValue(method = "cycleSlot", at = @At(value = "CONSTANT", args = "intValue=8"))
	private int modify8s(int original) {
		return MinecraftClient.getInstance().interactionManager.isTenfoursized() ? 9 : original;
	}
}
