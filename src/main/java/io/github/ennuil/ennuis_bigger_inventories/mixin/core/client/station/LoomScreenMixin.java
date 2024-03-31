package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.LoomScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin extends HandledScreen<LoomScreenHandler> {
	@Shadow
	private boolean canApplyDyePattern;
	@Unique
	private static final Identifier BIGGER_TEXTURE = new Identifier("ennuis_bigger_inventories", "textures/gui/container/loom.png");

	@Unique private static final Identifier EBI_BANNER_SLOT_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/loom/banner_slot");
	@Unique private static final Identifier EBI_DYE_SLOT_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/loom/dye_slot");
	@Unique private static final Identifier EBI_PATTERN_SLOT_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/loom/pattern_slot");
	@Unique private static final Identifier EBI_PATTERN_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/loom/pattern");
	@Unique private static final Identifier EBI_PATTERN_SELECTED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/loom/pattern_selected");
	@Unique private static final Identifier EBI_PATTERN_HIGHLIGHTED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/loom/pattern_highlighted");
	@Unique private static final Identifier EBI_SCROLLER_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/loom/scroller");
	@Unique private static final Identifier EBI_SCROLLER_DISABLED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/loom/scroller_disabled");
	@Unique private static final Identifier EBI_ERROR_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/loom/error");

	private LoomScreenMixin(LoomScreenHandler handler, PlayerInventory inventory, Text title) {
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

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 1
		)
	)
	private void modifyBannerSlotTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_BANNER_SLOT_TEXTURE, x, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 2
		)
	)
	private void modifyDyeSlotTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_DYE_SLOT_TEXTURE, x, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 3
		)
	)
	private void modifyPatternSlotTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_PATTERN_SLOT_TEXTURE, x, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 4
		)
	)
	private void modifyScrollerTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			var scrollerTexture = this.canApplyDyePattern ? EBI_SCROLLER_TEXTURE : EBI_SCROLLER_DISABLED_TEXTURE;
			graphics.drawGuiTexture(scrollerTexture, i + 136, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	// I'd personally nuke it, but Mojang apparently found some usefulness here; oh well
	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 5
		)
	)
	private void modifyErrorTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_ERROR_TEXTURE, x - 3, y - 3, 26, 26);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 6
		)
	)
	private void modifyPatternTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 13) int t) {
		if (this.client.interactionManager.isTenfoursized()) {
			var patternTexture = switch (t - this.backgroundHeight) {
				case 14 -> EBI_PATTERN_SELECTED_TEXTURE;
				case 28 -> EBI_PATTERN_HIGHLIGHTED_TEXTURE;
				default -> EBI_PATTERN_TEXTURE;
			};
			graphics.drawGuiTexture(patternTexture, x,  y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	// Modify offsets
	@ModifyExpressionValue(method = {"drawBackground", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=60"))
	private int modify60(int original) {
		return this.client.interactionManager.isTenfoursized() ? 62 : original;
	}

	@ModifyExpressionValue(method = "mouseClicked", at = @At(value = "CONSTANT", args = "intValue=119"))
	private int modify119(int original) {
		return this.client.interactionManager.isTenfoursized() ? 136 : original;
	}

	@ModifyExpressionValue(method = "drawBackground", at = @At(value = "CONSTANT", args = "intValue=139"))
	private int modify139(int original) {
		return this.client.interactionManager.isTenfoursized() ? 157 : original;
	}

	//Expand the grid!
	@ModifyExpressionValue(method = "getRows", at = @At(value = "CONSTANT", args = "intValue=4"))
	private int modifyFoursAlways(int original) {
		return this.client.interactionManager.isTenfoursized() ? 5 : original;
	}

	@ModifyExpressionValue(
		method = {"drawBackground", "mouseClicked"},
		at = @At(value = "CONSTANT", args = "intValue=4", ordinal = 1)
	)
	private int modifyFours1(int original) {
		return this.client.interactionManager.isTenfoursized() ? 5 : original;
	}

	@ModifyExpressionValue(
		method = {"drawBackground", "mouseClicked"},
		at = @At(value = "CONSTANT", args = "intValue=4", ordinal = 2)
	)
	private int modifyFours2(int original) {
		return this.client.interactionManager.isTenfoursized() ? 5 : original;
	}
}
