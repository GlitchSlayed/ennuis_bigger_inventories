package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(SpectatorGui.class)
public abstract class SpectatorGuiMixin {
	@Unique private static final Identifier EBI_HOTBAR_SPRITE = ModUtils.id("hud/hotbar");
	@Unique private static final Identifier EBI_HOTBAR_SELECTION_SPRITE = ModUtils.id("hud/hotbar_selection");

	@Shadow
	@Final
	private Minecraft minecraft;

	@ModifyArg(
		method = "extractPage",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyHotbar(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_HOTBAR_SPRITE : original;
	}

	@ModifyArg(
		method = "extractPage",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyHotbarSelection(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_HOTBAR_SELECTION_SPRITE : original;
	}

	@ModifyExpressionValue(method = "extractPage", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNine(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "extractPage", at = @At(value = "CONSTANT", args = "intValue=90"))
	private int modify90(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 100 : original;
	}

	@ModifyExpressionValue(method = "extractPage", at = @At(value = "CONSTANT", args = "intValue=91"))
	private int modify91(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 101 : original;
	}

	@ModifyExpressionValue(method = "extractPage", at = @At(value = "CONSTANT", args = "intValue=182"))
	private int modify182(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 202 : original;
	}

	@ModifyExpressionValue(method = "onMouseScrolled", at = @At(value = "CONSTANT", args = "intValue=8"))
	private int modify8s(int original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? 9 : original;
	}
}
