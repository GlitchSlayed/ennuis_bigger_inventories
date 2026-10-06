package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Hud;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(Hud.class)
public abstract class GuiMixin {
	@Unique private static final Identifier EBI_HOTBAR_SPRITE = ModUtils.id("hud/hotbar");
	@Unique private static final Identifier EBI_HOTBAR_OFFHAND_LEFT_SPRITE = ModUtils.id("hud/hotbar_offhand_left");
	@Unique private static final Identifier EBI_HOTBAR_OFFHAND_RIGHT_SPRITE = ModUtils.id("hud/hotbar_offhand_right");
	@Unique private static final Identifier EBI_HOTBAR_SELECTION_SPRITE = ModUtils.id("hud/hotbar_selection");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_BACKGROUND_SPRITE = ModUtils.id("hud/experience_bar_background");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_PROGRESS_SPRITE = ModUtils.id("hud/experience_bar_progress");
	@Unique private static final Identifier EBI_JUMP_BAR_BACKGROUND_SPRITE = ModUtils.id("hud/jump_bar_background");
	@Unique private static final Identifier EBI_JUMP_BAR_COOLDOWN_SPRITE = ModUtils.id("hud/jump_bar_cooldown");
	@Unique private static final Identifier EBI_JUMP_BAR_PROGRESS_SPRITE = ModUtils.id("hud/jump_bar_progress");

	@Shadow
	@Final
	private Minecraft minecraft;

	@ModifyArg(
		method = "extractItemHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyHotbar(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_HOTBAR_SPRITE : original;
	}

	@ModifyArg(
		method = "extractItemHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyHotbarSelection(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_HOTBAR_SELECTION_SPRITE : original;
	}

	@ModifyArg(
		method = "extractItemHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 2
		)
	)
	private Identifier modifyHotbarOffhandLeft(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_HOTBAR_OFFHAND_LEFT_SPRITE : original;
	}

	@ModifyArg(
		method = "extractItemHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 3
		)
	)
	private Identifier modifyHotbarOffhandRight(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_HOTBAR_OFFHAND_RIGHT_SPRITE : original;
	}

	@ModifyExpressionValue(method = "extractItemHotbar", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNines(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "extractItemHotbar", at = @At(value = "CONSTANT", args = "intValue=90"))
	private int modify90(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 10 * 10 : original;
	}

	@ModifyExpressionValue(
		method = {
			"extractHotbarAndDecorations",
			"extractItemHotbar",
			"extractPlayerHealth",
			"extractVehicleHealth"
		},
		at = @At(value = "CONSTANT", args = "intValue=91")
	)
	private int modify91(int original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			return (10 * 10) + 1;
		}

		return original;
	}

	@ModifyExpressionValue(method = "extractItemHotbar", at = @At(value = "CONSTANT", args = "intValue=182"))
	private int modify182(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? (10 * 20) + 2 : original;
	}
}
