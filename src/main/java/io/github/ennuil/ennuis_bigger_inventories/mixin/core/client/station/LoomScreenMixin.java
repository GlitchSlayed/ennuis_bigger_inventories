package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.LoomScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.LoomScreenHandler;
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
@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin extends HandledScreen<LoomScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = ModUtils.id("textures/gui/container/loom.png");

	@Unique private static final Identifier EBI_BANNER_SLOT_TEXTURE = ModUtils.id("container/loom/banner_slot");
	@Unique private static final Identifier EBI_DYE_SLOT_TEXTURE = ModUtils.id("container/loom/dye_slot");
	@Unique private static final Identifier EBI_PATTERN_SLOT_TEXTURE = ModUtils.id("container/loom/pattern_slot");
	@Unique private static final Identifier EBI_PATTERN_TEXTURE = ModUtils.id("container/loom/pattern");
	@Unique private static final Identifier EBI_PATTERN_SELECTED_TEXTURE = ModUtils.id("container/loom/pattern_selected");
	@Unique private static final Identifier EBI_PATTERN_HIGHLIGHTED_TEXTURE = ModUtils.id("container/loom/pattern_highlighted");
	@Unique private static final Identifier EBI_SCROLLER_TEXTURE = ModUtils.id("container/loom/scroller");
	@Unique private static final Identifier EBI_SCROLLER_DISABLED_TEXTURE = ModUtils.id("container/loom/scroller_disabled");
	@Unique private static final Identifier EBI_ERROR_TEXTURE = ModUtils.id("container/loom/error");

	@Shadow @Final private static Identifier SCROLLER;
	@Shadow @Final private static Identifier SCROLLER_DISABLED;
	@Shadow @Final private static Identifier PATTERN;
	@Shadow @Final private static Identifier PATTERN_SELECTED;
	@Shadow @Final private static Identifier PATTERN_HIGHLIGHTED;

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

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyBannerSlotTexture(Identifier original) {
		if (this.client.interactionManager.isTenfoursized()) {
			return EBI_BANNER_SLOT_TEXTURE;
		} else {
			return original;
		}
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyDyeSlotTexture(Identifier original) {
		if (this.client.interactionManager.isTenfoursized()) {
			return EBI_DYE_SLOT_TEXTURE;
		} else {
			return original;
		}
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 2
		)
	)
	private Identifier modifyPatternSlotTexture(Identifier original) {
		if (this.client.interactionManager.isTenfoursized()) {
			return EBI_PATTERN_SLOT_TEXTURE;
		} else {
			return original;
		}
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 3
		)
	)
	private Identifier modifyScrollerTexture(Identifier original) {
		var texture = original;
		if (texture == SCROLLER) {
			texture = EBI_SCROLLER_TEXTURE;
		} else if (texture == SCROLLER_DISABLED) {
			texture = EBI_SCROLLER_DISABLED_TEXTURE;
		}

		return texture;
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 4
		)
	)
	private Identifier modifyErrorTexture(Identifier original) {
		if (this.client.interactionManager.isTenfoursized()) {
			return EBI_ERROR_TEXTURE;
		} else {
			return original;
		}
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 5
		)
	)
	private Identifier modifyPatternTexture(Identifier original) {
		var texture = original;
		if (texture == PATTERN_SELECTED) {
			texture = EBI_PATTERN_SELECTED_TEXTURE;
		} else if (texture == PATTERN_HIGHLIGHTED) {
			texture = EBI_PATTERN_HIGHLIGHTED_TEXTURE;
		} else if (texture == PATTERN) {
			texture = EBI_PATTERN_TEXTURE;
		}

		return texture;
	}

	// Modify offsets
	@ModifyExpressionValue(method = {"drawBackground", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=60"))
	private int modify60(int original) {
		return this.client.interactionManager.isTenfoursized() ? 62 : original;
	}

	@ModifyExpressionValue(method = {"drawBackground", "mouseClicked"}, at = @At(value = "CONSTANT", args = "intValue=119"))
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
