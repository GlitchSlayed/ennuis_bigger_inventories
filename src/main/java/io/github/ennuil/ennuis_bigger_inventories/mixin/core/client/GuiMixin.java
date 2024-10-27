package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(Gui.class)
public abstract class GuiMixin {
	@Unique private static final ResourceLocation EBI_HOTBAR_SPRITE = ModUtils.id("hud/hotbar");
	@Unique private static final ResourceLocation EBI_HOTBAR_OFFHAND_LEFT_SPRITE = ModUtils.id("hud/hotbar_offhand_left");
	@Unique private static final ResourceLocation EBI_HOTBAR_OFFHAND_RIGHT_SPRITE = ModUtils.id("hud/hotbar_offhand_right");
	@Unique private static final ResourceLocation EBI_HOTBAR_SELECTION_SPRITE = ModUtils.id("hud/hotbar_selection");
	@Unique private static final ResourceLocation EBI_EXPERIENCE_BAR_BACKGROUND_SPRITE = ModUtils.id("hud/experience_bar_background");
	@Unique private static final ResourceLocation EBI_EXPERIENCE_BAR_PROGRESS_SPRITE = ModUtils.id("hud/experience_bar_progress");
	@Unique private static final ResourceLocation EBI_JUMP_BAR_BACKGROUND_SPRITE = ModUtils.id("hud/jump_bar_background");
	@Unique private static final ResourceLocation EBI_JUMP_BAR_COOLDOWN_SPRITE = ModUtils.id("hud/jump_bar_cooldown");
	@Unique private static final ResourceLocation EBI_JUMP_BAR_PROGRESS_SPRITE = ModUtils.id("hud/jump_bar_progress");

	@Shadow
	@Final
	private Minecraft minecraft;

	@ModifyArg(
		method = "renderItemHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"
		)
	)
	private ResourceLocation modifyHotbar(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_HOTBAR_SPRITE : original;
	}

	@ModifyArg(
		method = "renderItemHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 1
		)
	)
	private ResourceLocation modifyHotbarSelection(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_HOTBAR_SELECTION_SPRITE : original;
	}

	@ModifyArg(
		method = "renderItemHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 2
		)
	)
	private ResourceLocation modifyHotbarOffhandLeft(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_HOTBAR_OFFHAND_LEFT_SPRITE : original;
	}

	@ModifyArg(
		method = "renderItemHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 3
		)
	)
	private ResourceLocation modifyHotbarOffhandRight(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_HOTBAR_OFFHAND_RIGHT_SPRITE : original;
	}

	@ModifyArg(
		method = "renderExperienceBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"
		)
	)
	private ResourceLocation modifyExperienceBarBackground(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_EXPERIENCE_BAR_BACKGROUND_SPRITE : original;
	}

	@ModifyArg(
		method = "renderExperienceBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V"
		)
	)
	private ResourceLocation modifyExperienceBarProgress(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_EXPERIENCE_BAR_PROGRESS_SPRITE : original;
	}

	@ModifyArg(
		method = "renderJumpMeter",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 0
		)
	)
	private ResourceLocation modifyJumpBarBackground(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_JUMP_BAR_BACKGROUND_SPRITE : original;
	}

	@ModifyArg(
		method = "renderJumpMeter",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 1
		)
	)
	private ResourceLocation modifyJumpBarCooldown(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_JUMP_BAR_COOLDOWN_SPRITE : original;
	}

	@ModifyArg(
		method = "renderJumpMeter",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V"
		)
	)
	private ResourceLocation modifyJumpBarProgress(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_JUMP_BAR_PROGRESS_SPRITE : original;
	}

	@ModifyExpressionValue(method = "renderItemHotbar", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNines(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "renderItemHotbar", at = @At(value = "CONSTANT", args = "intValue=90"))
	private int modify90(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 10 * 10 : original;
	}

	@ModifyExpressionValue(
		method = {
			"renderHotbarAndDecorations",
			"renderItemHotbar",
			"renderPlayerHealth",
			"renderVehicleHealth"
		},
		at = @At(value = "CONSTANT", args = "intValue=91")
	)
	private int modify91(int original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			return (10 * 10) + 1;
		}

		return original;
	}

	@ModifyExpressionValue(method = {"renderItemHotbar", "renderJumpMeter", "renderExperienceBar"}, at = @At(value = "CONSTANT", args = "intValue=182"))
	private int modify182(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? (10 * 20) + 2 : original;
	}

	@ModifyExpressionValue(method = {"renderJumpMeter", "renderExperienceBar"}, at = @At(value = "CONSTANT", args = "floatValue=183.0F"))
	private float modify183F(float original) {
		return this.minecraft.gameMode.isTenfoursized() ? (10.0F * 20.0F) + 3.0F : original;
	}
}
