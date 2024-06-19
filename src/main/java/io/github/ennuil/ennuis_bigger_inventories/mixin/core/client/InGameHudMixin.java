package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.in_game.InGameHud;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Unique private static final Identifier EBI_HOTBAR_TEXTURE = ModUtils.id("hud/hotbar");
	@Unique private static final Identifier EBI_HOTBAR_OFFHAND_LEFT_TEXTURE = ModUtils.id("hud/hotbar_offhand_left");
	@Unique private static final Identifier EBI_HOTBAR_OFFHAND_RIGHT_TEXTURE = ModUtils.id("hud/hotbar_offhand_right");
	@Unique private static final Identifier EBI_HOTBAR_SELECTION_TEXTURE = ModUtils.id("hud/hotbar_selection");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_BACKGROUND = ModUtils.id("hud/experience_bar_background");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_PROGRESS = ModUtils.id("hud/experience_bar_progress");
	@Unique private static final Identifier EBI_JUMP_BAR_BACKGROUND = ModUtils.id("hud/jump_bar_background");
	@Unique private static final Identifier EBI_JUMP_BAR_COOLDOWN = ModUtils.id("hud/jump_bar_cooldown");
	@Unique private static final Identifier EBI_JUMP_BAR_PROGRESS = ModUtils.id("hud/jump_bar_progress");

	@Shadow
	@Final
	private MinecraftClient client;

	@ModifyArg(
		method = "renderSurvivalHotbar",
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
		method = "renderSurvivalHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyHotbarSelection(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_HOTBAR_SELECTION_TEXTURE : original;
	}

	@ModifyArg(
		method = "renderSurvivalHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 2
		)
	)
	private Identifier modifyHotbarOffhandLeft(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_HOTBAR_OFFHAND_LEFT_TEXTURE : original;
	}

	@ModifyArg(
		method = "renderSurvivalHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 3
		)
	)
	private Identifier modifyHotbarOffhandRight(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_HOTBAR_OFFHAND_RIGHT_TEXTURE : original;
	}

	@ModifyArg(
		method = "renderExperienceBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private Identifier modifyExperienceBarBackground(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_EXPERIENCE_BAR_BACKGROUND : original;
	}

	@ModifyArg(
		method = "renderExperienceBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIII)V"
		)
	)
	private Identifier modifyExperienceBarProgress(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_EXPERIENCE_BAR_PROGRESS : original;
	}

	@ModifyArg(
		method = "renderMountJumpBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyJumpBarBackground(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_JUMP_BAR_BACKGROUND : original;
	}

	@ModifyArg(
		method = "renderMountJumpBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyJumpBarCooldown(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_JUMP_BAR_COOLDOWN : original;
	}

	@ModifyArg(
		method = "renderMountJumpBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIII)V"
		)
	)
	private Identifier modifyJumpBarProgress(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_JUMP_BAR_PROGRESS : original;
	}

	@ModifyExpressionValue(method = "renderSurvivalHotbar", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNines(int original) {
		return this.client.interactionManager.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "renderSurvivalHotbar", at = @At(value = "CONSTANT", args = "intValue=90"))
	private int modify90(int original) {
		return this.client.interactionManager.isTenfoursized() ? 10 * 10 : original;
	}

	@ModifyExpressionValue(
		method = {
			"renderHotbar",
			"renderSurvivalHotbar",
			"renderStatusBars",
			"renderMountHealth"
		},
		at = @At(value = "CONSTANT", args = "intValue=91")
	)
	private int modify91(int original) {
		if (this.client.interactionManager.isTenfoursized()) {
			return (10 * 10) + 1;
		}

		return original;
	}

	@ModifyExpressionValue(method = {"renderSurvivalHotbar", "renderMountJumpBar", "renderExperienceBar"}, at = @At(value = "CONSTANT", args = "intValue=182"))
	private int modify182(int original) {
		return this.client.interactionManager.isTenfoursized() ? (10 * 20) + 2 : original;
	}

	@ModifyExpressionValue(method = {"renderMountJumpBar", "renderExperienceBar"}, at = @At(value = "CONSTANT", args = "floatValue=183.0F"))
	private float modify183F(float original) {
		return this.client.interactionManager.isTenfoursized() ? (10.0F * 20.0F) + 3.0F : original;
	}
}
