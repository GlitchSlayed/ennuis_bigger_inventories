package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Unique private static final Identifier EBI_HOTBAR_TEXTURE = new Identifier("ennuis_bigger_inventories", "hud/hotbar");
	@Unique private static final Identifier EBI_HOTBAR_OFFHAND_LEFT_TEXTURE = new Identifier("ennuis_bigger_inventories", "hud/hotbar_offhand_left");
	@Unique private static final Identifier EBI_HOTBAR_OFFHAND_RIGHT_TEXTURE = new Identifier("ennuis_bigger_inventories", "hud/hotbar_offhand_right");
	@Unique private static final Identifier EBI_HOTBAR_SELECTION_TEXTURE = new Identifier("ennuis_bigger_inventories", "hud/hotbar_selection");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_BACKGROUND = new Identifier("ennuis_bigger_inventories", "hud/experience_bar_background");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_PROGRESS = new Identifier("ennuis_bigger_inventories", "hud/experience_bar_progress");
	@Unique private static final Identifier EBI_JUMP_BAR_BACKGROUND = new Identifier("ennuis_bigger_inventories", "hud/jump_bar_background");
	@Unique private static final Identifier EBI_JUMP_BAR_COOLDOWN = new Identifier("ennuis_bigger_inventories", "hud/jump_bar_cooldown");
	@Unique private static final Identifier EBI_JUMP_BAR_PROGRESS = new Identifier("ennuis_bigger_inventories", "hud/jump_bar_progress");

	@Shadow
	@Final
	private MinecraftClient client;

	@ModifyExpressionValue(method = "renderHotbar", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNines(int original) {
		return this.client.interactionManager.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "renderHotbar", at = @At(value = "CONSTANT", args = "intValue=90"))
	private int modify90(int original) {
		return this.client.interactionManager.isTenfoursized() ? 10 * 10 : original;
	}

	@ModifyExpressionValue(
		method = {
			"renderHotbar",
			"renderStatusBars",
			"renderMountHealth",
			"render"
		},
		at = @At(value = "CONSTANT", args = "intValue=91")
	)
	private int modify91(int original) {
		if (this.client.interactionManager.isTenfoursized()) {
			return (10 * 10) + 1;
		}

		return original;
	}

	@WrapOperation(
		method = "renderHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 0
		)
	)
	private void modifyHotbar(GuiGraphics instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			instance.drawGuiTexture(EBI_HOTBAR_TEXTURE, x, y, 202, height);
		} else {
			original.call(instance, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "renderHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 1
		)
	)
	private void modifyHotbarSelection(GuiGraphics instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			instance.drawGuiTexture(EBI_HOTBAR_SELECTION_TEXTURE, x, y, width, 23);
		} else {
			original.call(instance, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "renderHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 2
		)
	)
	private void modifyHotbarOffhandLeft(GuiGraphics instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			instance.drawGuiTexture(EBI_HOTBAR_OFFHAND_LEFT_TEXTURE, x, y, width, height);
		} else {
			original.call(instance, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "renderHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 2
		)
	)
	private void modifyHotbarOffhandRight(GuiGraphics instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			instance.drawGuiTexture(EBI_HOTBAR_OFFHAND_RIGHT_TEXTURE, x, y, width, height);
		} else {
			original.call(instance, texture, x, y, u, v, width, height);
		}
	}

	@ModifyExpressionValue(method = {"renderHotbar"}, at = @At(value = "CONSTANT", args = "intValue=182"))
	private int modify182(int original) {
		return this.client.interactionManager.isTenfoursized() ? (10 * 20) + 2 : original;
	}

	@ModifyExpressionValue(method = {"renderExperienceBar", "renderMountJumpBar"}, at = @At(value = "CONSTANT", args = "floatValue=183.0F"))
	private float modify183F(float original) {
		return this.client.interactionManager.isTenfoursized() ? (10.0F * 20.0F) + 3.0F : original;
	}

	@WrapOperation(
		method = "renderExperienceBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 0
		)
	)
	private void modifyExperienceBarBackground(GuiGraphics instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			instance.drawGuiTexture(EBI_EXPERIENCE_BAR_BACKGROUND, x, y, 202, height);
		} else {
			original.call(instance, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "renderExperienceBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 1
		)
	)
	private void modifyExperienceBarProgress(GuiGraphics instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			instance.drawGuiTexture(EBI_EXPERIENCE_BAR_PROGRESS, 202, 5, 0, 0, x, y, width, height);
		} else {
			original.call(instance, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "renderMountJumpBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 0
		)
	)
	private void modifyJumpBarBackground(GuiGraphics instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			instance.drawGuiTexture(EBI_JUMP_BAR_BACKGROUND, x, y, 202, height);
		} else {
			original.call(instance, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "renderMountJumpBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 1
		)
	)
	private void modifyJumpBarCooldown(GuiGraphics instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			instance.drawGuiTexture(EBI_JUMP_BAR_COOLDOWN, x, y, 202, height);
		} else {
			original.call(instance, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "renderMountJumpBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 2
		)
	)
	private void modifyJumpBarProgress(GuiGraphics instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			instance.drawGuiTexture(EBI_JUMP_BAR_PROGRESS, 202, 5, 0, 0, x, y, width, height);
		} else {
			original.call(instance, texture, x, y, u, v, width, height);
		}
	}
}
