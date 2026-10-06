package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.StonecutterScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.StonecutterMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(StonecutterScreen.class)
public abstract class StonecutterScreenMixin extends AbstractContainerScreen<StonecutterMenu> {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/stonecutter.png");
	@Unique private static final Identifier EBI_RECIPE_SPRITE = ModUtils.id("container/stonecutter/recipe");
	@Unique private static final Identifier EBI_RECIPE_SELECTED_SPRITE = ModUtils.id("container/stonecutter/recipe_selected");
	@Unique private static final Identifier EBI_RECIPE_HIGHLIGHTED_SPRITE = ModUtils.id("container/stonecutter/recipe_highlighted");
	@Unique private static final Identifier EBI_SCROLLER_SPRITE = ModUtils.id("container/stonecutter/scroller");
	@Unique private static final Identifier EBI_SCROLLER_DISABLED_SPRITE = ModUtils.id("container/stonecutter/scroller_disabled");

	@Shadow @Final private static Identifier RECIPE_SELECTED_SPRITE;
	@Shadow @Final private static Identifier RECIPE_HIGHLIGHTED_SPRITE;
	@Shadow @Final private static Identifier RECIPE_SPRITE;
	@Shadow @Final private static Identifier SCROLLER_SPRITE;
	@Shadow @Final private static Identifier SCROLLER_DISABLED_SPRITE;

	private StonecutterScreenMixin(StonecutterMenu menu, Inventory inventory, Component title) {
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
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"
		)
	)
	private Identifier modifyScrollerTexture(Identifier original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			var texture = original;
			if (texture == SCROLLER_SPRITE) {
				texture = EBI_SCROLLER_SPRITE;
			} else if (texture == SCROLLER_DISABLED_SPRITE) {
				texture = EBI_SCROLLER_DISABLED_SPRITE;
			}

			return texture;
		} else {
			return original;
		}
	}

	@ModifyArg(
		method = "extractButtons",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"
		)
	)
	private Identifier modifyPatternTexture(Identifier original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			var texture = original;
			if (texture == RECIPE_SELECTED_SPRITE) {
				texture = EBI_RECIPE_SELECTED_SPRITE;
			} else if (texture == RECIPE_HIGHLIGHTED_SPRITE) {
				texture = EBI_RECIPE_HIGHLIGHTED_SPRITE;
			} else if (texture == RECIPE_SPRITE) {
				texture = EBI_RECIPE_SPRITE;
			}

			return texture;
		} else {
			return original;
		}
	}

	// Modify offsets
	@ModifyExpressionValue(method = {"extractContents", "extractTooltip", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=52"))
	private int modify52(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 49 : original;
	}

	@ModifyExpressionValue(method = {"extractBackground", "extractContents", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=119"))
	private int modify119(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 133 : original;
	}

	// Expand the grid
	@ModifyExpressionValue(method = {"isScrollBarActive", "extractTooltip", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=12", ordinal = 0))
	private int modify12(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 5 * 3 : original;
	}

	@ModifyExpressionValue(method = "extractBackground", at = @At(value = "CONSTANT", args = "intValue=12", ordinal = 1))
	private int modify12OnRender(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 5 * 3 : original;
	}

	@ModifyExpressionValue(method = {
		"extractTooltip",
		"extractButtons",
		"extractRecipes",
		"mouseClicked",
		"mouseDragged",
		"mouseScrolled"
	}, at = @At(value = "CONSTANT", args = "intValue=4"))
	private int modify4(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 5 : original;
	}
}
