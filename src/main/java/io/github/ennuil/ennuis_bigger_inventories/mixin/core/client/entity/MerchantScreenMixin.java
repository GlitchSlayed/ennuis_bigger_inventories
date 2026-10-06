package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.entity;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin extends AbstractContainerScreen<MerchantMenu> {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/villager.png");
	@Unique private static final Identifier EBI_OUT_OF_STOCK_SPRITE = ModUtils.id("container/villager/out_of_stock");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_BACKGROUND_SPRITE = ModUtils.id("container/villager/experience_bar_background");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_CURRENT_SPRITE = ModUtils.id("container/villager/experience_bar_current");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_RESULT_SPRITE = ModUtils.id("container/villager/experience_bar_result");
	@Unique private static final Identifier EBI_SCROLLER_SPRITE = ModUtils.id("container/villager/scroller");
	@Unique private static final Identifier EBI_SCROLLER_DISABLED_SPRITE = ModUtils.id("container/villager/scroller_disabled");
	@Unique private static final Identifier EBI_TRADE_ARROW_SPRITE = ModUtils.id("container/villager/trade_arrow");
	@Unique private static final Identifier EBI_TRADE_ARROW_OUT_OF_STOCK_SPRITE = ModUtils.id("container/villager/trade_arrow_out_of_stock");

	private MerchantScreenMixin(MerchantMenu menu, Inventory inventory, Component title, boolean dummy) {
		super(menu, inventory, title);
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=276"))
	private static int modify276(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 294 : original;
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
	private Identifier modifyOutOfStockTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_OUT_OF_STOCK_SPRITE : original;
	}

	@ModifyArg(
		method = "extractProgressBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"
		)
	)
	private Identifier modifyExpBarBackgroundTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_EXPERIENCE_BAR_BACKGROUND_SPRITE : original;
	}

	@ModifyArg(
		method = "extractProgressBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyExpBarCurrentTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_EXPERIENCE_BAR_CURRENT_SPRITE : original;
	}

	@ModifyArg(
		method = "extractProgressBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyExpBarResultTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_EXPERIENCE_BAR_RESULT_SPRITE : original;
	}

	@ModifyArg(
		method = "extractScroller",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyScrollerTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_SCROLLER_SPRITE : original;
	}

	@ModifyArg(
		method = "extractScroller",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyDisabledScrollerTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_SCROLLER_DISABLED_SPRITE : original;
	}

	@ModifyArg(
		method = "extractButtonArrows",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyOutOfStockTradeArrowTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_TRADE_ARROW_OUT_OF_STOCK_SPRITE : original;
	}

	@ModifyArg(
		method = "extractButtonArrows",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyTradeArrowTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_TRADE_ARROW_SPRITE : original;
	}

	// It turns out the generic "Error" texture was split wrongly on 1.20.2! This is fixed here
	@ModifyExpressionValue(method = "extractBackground", at = @At(value = "CONSTANT", args = "intValue=83"))
	private int modifyErrorX(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 92 + 1 : original;
	}

	@ModifyExpressionValue(method = "extractProgressBar", at = @At(value = "CONSTANT", args = "intValue=136"))
	private int modifyExpBarX(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 145 : original;
	}
}
