package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.StonecutterScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(StonecutterScreen.class)
public abstract class StonecutterScreenMixin extends HandledScreen<StonecutterScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = new Identifier("ennuis_bigger_inventories", "textures/gui/container/stonecutter.png");

	@Unique private static final Identifier EBI_RECIPE_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/stonecutter/recipe");
	@Unique private static final Identifier EBI_RECIPE_SELECTED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/stonecutter/recipe_selected");
	@Unique private static final Identifier EBI_RECIPE_HIGHLIGHTED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/stonecutter/recipe_highlighted");
	@Unique private static final Identifier EBI_SCROLLER_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/stonecutter/scroller");
	@Unique private static final Identifier EBI_SCROLLER_DISABLED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/stonecutter/scroller_disabled");

	private StonecutterScreenMixin(StonecutterScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Shadow
	protected abstract boolean shouldScroll();

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

	// "@Local(ordinal = 2) int i" won't work here for whatever reason; hooray for local shenanigans again!
	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 1
		)
	)
	private void modifyScrollerTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			var scrollerTexture = this.shouldScroll() ? EBI_SCROLLER_TEXTURE : EBI_SCROLLER_DISABLED_TEXTURE;
			graphics.drawGuiTexture(scrollerTexture, this.x + 133, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "renderRecipeBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
		)
	)
	private void modifyPatternTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 10) int n) {
		if (this.client.interactionManager.isTenfoursized()) {
			var patternTexture = switch (n - this.backgroundHeight) {
				case 18 -> EBI_RECIPE_SELECTED_TEXTURE;
				case 36 -> EBI_RECIPE_HIGHLIGHTED_TEXTURE;
				default -> EBI_RECIPE_TEXTURE;
			};
			graphics.drawGuiTexture(patternTexture, x,  y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	// Modify offsets
	@ModifyExpressionValue(method = {"drawBackground", "drawMouseoverTooltip", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=52"))
	private int modify52(int original) {
		return this.client.interactionManager.isTenfoursized() ? 49 : original;
	}

	@ModifyExpressionValue(method = "mouseClicked", at = @At(value = "CONSTANT", args = "intValue=119"))
	private int modify119(int original) {
		return this.client.interactionManager.isTenfoursized() ? 133 : original;
	}

	// Expand the grid
	@ModifyExpressionValue(method = {"shouldScroll", "drawMouseoverTooltip", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=12", ordinal = 0))
	private int modify12(int original) {
		return this.client.interactionManager.isTenfoursized() ? 5 * 3 : original;
	}

	@ModifyExpressionValue(method = "drawBackground", at = @At(value = "CONSTANT", args = "intValue=12", ordinal = 2))
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
