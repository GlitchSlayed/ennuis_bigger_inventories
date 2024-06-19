package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin extends HandledScreen<MerchantScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = ModUtils.id("textures/gui/container/villager.png");

	@Unique private static final Identifier EBI_OUT_OF_STOCK_TEXTURE = ModUtils.id("container/villager/out_of_stock");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_BACKGROUND_TEXTURE = ModUtils.id("container/villager/experience_bar_background");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_CURRENT_TEXTURE = ModUtils.id("container/villager/experience_bar_current");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_RESULT_TEXTURE = ModUtils.id("container/villager/experience_bar_result");
	@Unique private static final Identifier EBI_SCROLLER_TEXTURE = ModUtils.id("container/villager/scroller");
	@Unique private static final Identifier EBI_SCROLLER_DISABLED_TEXTURE = ModUtils.id("container/villager/scroller_disabled");
	@Unique private static final Identifier EBI_TRADE_ARROW_TEXTURE = ModUtils.id("container/villager/trade_arrow");
	@Unique private static final Identifier EBI_TRADE_ARROW_OUT_OF_STOCK_TEXTURE = ModUtils.id("container/villager/trade_arrow_out_of_stock");

	private MerchantScreenMixin(MerchantScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=276"))
	private int modify276(int original, @Local(argsOnly = true) PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 294 : original;
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V"
		)
	)
	private Identifier modifyTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? BIGGER_TEXTURE : original;
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIII)V"
		)
	)
	private Identifier modifyOutOfStockTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_OUT_OF_STOCK_TEXTURE : original;
	}

	@ModifyArg(
		method = "drawLevelInfo",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIII)V"
		)
	)
	private Identifier modifyExpBarBackgroundTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_EXPERIENCE_BAR_BACKGROUND_TEXTURE : original;
	}

	@ModifyArg(
		method = "drawLevelInfo",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyExpBarCurrentTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_EXPERIENCE_BAR_CURRENT_TEXTURE : original;
	}

	@ModifyArg(
		method = "drawLevelInfo",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyExpBarResultTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_EXPERIENCE_BAR_RESULT_TEXTURE : original;
	}

	@ModifyArg(
		method = "renderScrollbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyScrollerTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_SCROLLER_TEXTURE : original;
	}

	@ModifyArg(
		method = "renderScrollbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyDisabledScrollerTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_SCROLLER_DISABLED_TEXTURE : original;
	}

	@ModifyArg(
		method = "renderArrow",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyOutOfStockTradeArrowTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_TRADE_ARROW_OUT_OF_STOCK_TEXTURE : original;
	}

	@ModifyArg(
		method = "renderArrow",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyTradeArrowTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_TRADE_ARROW_TEXTURE : original;
	}

	// It turns out the generic "Error" texture was split wrongly on 1.20.2! This is fixed here
	@ModifyExpressionValue(method = "drawBackground", at = @At(value = "CONSTANT", args = "intValue=83"))
	private int modifyErrorX(int original) {
		return this.client.interactionManager.isTenfoursized() ? 92 + 1 : original;
	}

	@ModifyExpressionValue(method = "drawLevelInfo", at = @At(value = "CONSTANT", args = "intValue=136"))
	private int modifyExpBarX(int original) {
		return this.client.interactionManager.isTenfoursized() ? 145 : original;
	}
}
