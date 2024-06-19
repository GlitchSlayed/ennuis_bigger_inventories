package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.StonecutterScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(StonecutterScreen.class)
public abstract class StonecutterScreenMixin extends HandledScreen<StonecutterScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = ModUtils.id("textures/gui/container/stonecutter.png");

	@Unique private static final Identifier EBI_RECIPE_TEXTURE = ModUtils.id("container/stonecutter/recipe");
	@Unique private static final Identifier EBI_RECIPE_SELECTED_TEXTURE = ModUtils.id("container/stonecutter/recipe_selected");
	@Unique private static final Identifier EBI_RECIPE_HIGHLIGHTED_TEXTURE = ModUtils.id("container/stonecutter/recipe_highlighted");
	@Unique private static final Identifier EBI_SCROLLER_TEXTURE = ModUtils.id("container/stonecutter/scroller");
	@Unique private static final Identifier EBI_SCROLLER_DISABLED_TEXTURE = ModUtils.id("container/stonecutter/scroller_disabled");

	@Shadow @Final private static Identifier RECIPE_SELECTED;
	@Shadow @Final private static Identifier RECIPE_HIGHLIGHTED;
	@Shadow @Final private static Identifier RECIPE;
	@Shadow @Final private static Identifier SCROLLER;
	@Shadow @Final private static Identifier SCROLLER_DISABLED;

	private StonecutterScreenMixin(StonecutterScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
		),
		index = 0
	)
	private Identifier modifyTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? BIGGER_TEXTURE : original;
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private Identifier modifyScrollerTexture(Identifier original) {
		if (this.client.interactionManager.isTenfoursized()) {
			var texture = original;
			if (texture == SCROLLER) {
				texture = EBI_SCROLLER_TEXTURE;
			} else if (texture == SCROLLER_DISABLED) {
				texture = EBI_SCROLLER_DISABLED_TEXTURE;
			}

			return texture;
		} else {
			return original;
		}
	}

	@ModifyArg(
		method = "renderRecipeBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private Identifier modifyPatternTexture(Identifier original) {
		if (this.client.interactionManager.isTenfoursized()) {
			var texture = original;
			if (texture == RECIPE_SELECTED) {
				texture = EBI_RECIPE_SELECTED_TEXTURE;
			} else if (texture == RECIPE_HIGHLIGHTED) {
				texture = EBI_RECIPE_HIGHLIGHTED_TEXTURE;
			} else if (texture == RECIPE) {
				texture = EBI_RECIPE_TEXTURE;
			}

			return texture;
		} else {
			return original;
		}
	}

	// Modify offsets
	@ModifyExpressionValue(method = {"drawBackground", "drawMouseoverTooltip", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=52"))
	private int modify52(int original) {
		return this.client.interactionManager.isTenfoursized() ? 49 : original;
	}

	@ModifyExpressionValue(method = {"drawBackground", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=119"))
	private int modify119(int original) {
		return this.client.interactionManager.isTenfoursized() ? 133 : original;
	}

	// Expand the grid
	@ModifyExpressionValue(method = {"shouldScroll", "drawMouseoverTooltip", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=12", ordinal = 0))
	private int modify12(int original) {
		return this.client.interactionManager.isTenfoursized() ? 5 * 3 : original;
	}

	@ModifyExpressionValue(method = "drawBackground", at = @At(value = "CONSTANT", args = "intValue=12", ordinal = 1))
	private int modify12OnRender(int original) {
		return this.client.interactionManager.isTenfoursized() ? 5 * 3 : original;
	}

	@ModifyExpressionValue(method = {
		"drawMouseoverTooltip",
		"renderRecipeBackground",
		"renderRecipeIcons",
		"mouseClicked",
		"mouseDragged",
		"mouseScrolled"
	}, at = @At(value = "CONSTANT", args = "intValue=4"))
	private int modify4(int original) {
		return this.client.interactionManager.isTenfoursized() ? 5 : original;
	}
}
