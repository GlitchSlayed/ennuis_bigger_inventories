package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.LoomMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin extends AbstractContainerScreen<LoomMenu> {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/loom.png");
	@Unique private static final Identifier EBI_BANNER_SLOT_SPRITE = ModUtils.id("container/loom/banner_slot");
	@Unique private static final Identifier EBI_DYE_SLOT_SPRITE = ModUtils.id("container/loom/dye_slot");
	@Unique private static final Identifier EBI_PATTERN_SLOT_SPRITE = ModUtils.id("container/loom/pattern_slot");
	@Unique private static final Identifier EBI_PATTERN_SPRITE = ModUtils.id("container/loom/pattern");
	@Unique private static final Identifier EBI_PATTERN_SELECTED_SPRITE = ModUtils.id("container/loom/pattern_selected");
	@Unique private static final Identifier EBI_PATTERN_HIGHLIGHTED_SPRITE = ModUtils.id("container/loom/pattern_highlighted");
	@Unique private static final Identifier EBI_SCROLLER_SPRITE = ModUtils.id("container/loom/scroller");
	@Unique private static final Identifier EBI_SCROLLER_DISABLED_SPRITE = ModUtils.id("container/loom/scroller_disabled");
	@Unique private static final Identifier EBI_ERROR_TEXTURE = ModUtils.id("container/loom/error");

	@Shadow @Final private static Identifier SCROLLER_SPRITE;
	@Shadow @Final private static Identifier SCROLLER_DISABLED_SPRITE;
	@Shadow @Final private static Identifier PATTERN_SPRITE;
	@Shadow @Final private static Identifier PATTERN_SELECTED_SPRITE;
	@Shadow @Final private static Identifier PATTERN_HIGHLIGHTED_SPRITE;

	private LoomScreenMixin(LoomMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V"
		)
	)
	private Identifier modifyTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_TEXTURE : original;
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyBannerSlotTexture(Identifier original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			return EBI_BANNER_SLOT_SPRITE;
		} else {
			return original;
		}
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyDyeSlotTexture(Identifier original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			return EBI_DYE_SLOT_SPRITE;
		} else {
			return original;
		}
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 2
		)
	)
	private Identifier modifyPatternSlotTexture(Identifier original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			return EBI_PATTERN_SLOT_SPRITE;
		} else {
			return original;
		}
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 3
		)
	)
	private Identifier modifyScrollerTexture(Identifier original) {
		var texture = original;
		if (texture == SCROLLER_SPRITE) {
			texture = EBI_SCROLLER_SPRITE;
		} else if (texture == SCROLLER_DISABLED_SPRITE) {
			texture = EBI_SCROLLER_DISABLED_SPRITE;
		}

		return texture;
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 4
		)
	)
	private Identifier modifyErrorTexture(Identifier original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			return EBI_ERROR_TEXTURE;
		} else {
			return original;
		}
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 5
		)
	)
	private Identifier modifyPatternTexture(Identifier original) {
		var texture = original;
		if (texture == PATTERN_SELECTED_SPRITE) {
			texture = EBI_PATTERN_SELECTED_SPRITE;
		} else if (texture == PATTERN_HIGHLIGHTED_SPRITE) {
			texture = EBI_PATTERN_HIGHLIGHTED_SPRITE;
		} else if (texture == PATTERN_SPRITE) {
			texture = EBI_PATTERN_SPRITE;
		}

		return texture;
	}

	// Modify offsets
	@ModifyExpressionValue(method = {"extractBackground", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=60"))
	private int modify60(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 62 : original;
	}

	@ModifyExpressionValue(method = {"extractBackground", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=119"))
	private int modify119(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 136 : original;
	}

	@ModifyExpressionValue(method = "extractBackground", at = @At(value = "CONSTANT", args = "intValue=141"))
	private int modify139(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 159 : original;
	}

	//Expand the grid!
	@ModifyExpressionValue(method = "totalRowCount", at = @At(value = "CONSTANT", args = "intValue=4"))
	private int modifyFoursAlways(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 5 : original;
	}

	@ModifyExpressionValue(
		method = {"extractBackground", "mouseClicked"},
		at = @At(value = "CONSTANT", args = "intValue=4", ordinal = 1)
	)
	private int modifyFours1(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 5 : original;
	}

	@ModifyExpressionValue(
		method = {"extractBackground", "mouseClicked"},
		at = @At(value = "CONSTANT", args = "intValue=4", ordinal = 2)
	)
	private int modifyFours2(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 5 : original;
	}
}
