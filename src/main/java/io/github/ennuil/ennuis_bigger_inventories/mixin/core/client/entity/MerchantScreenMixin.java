package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin extends AbstractContainerScreen<MerchantMenu> {
	@Unique private static final ResourceLocation EBI_TEXTURE = ModUtils.id("textures/gui/container/villager.png");
	@Unique private static final ResourceLocation EBI_OUT_OF_STOCK_SPRITE = ModUtils.id("container/villager/out_of_stock");
	@Unique private static final ResourceLocation EBI_EXPERIENCE_BAR_BACKGROUND_SPRITE = ModUtils.id("container/villager/experience_bar_background");
	@Unique private static final ResourceLocation EBI_EXPERIENCE_BAR_CURRENT_SPRITE = ModUtils.id("container/villager/experience_bar_current");
	@Unique private static final ResourceLocation EBI_EXPERIENCE_BAR_RESULT_SPRITE = ModUtils.id("container/villager/experience_bar_result");
	@Unique private static final ResourceLocation EBI_SCROLLER_SPRITE = ModUtils.id("container/villager/scroller");
	@Unique private static final ResourceLocation EBI_SCROLLER_DISABLED_SPRITE = ModUtils.id("container/villager/scroller_disabled");
	@Unique private static final ResourceLocation EBI_TRADE_ARROW_SPRITE = ModUtils.id("container/villager/trade_arrow");
	@Unique private static final ResourceLocation EBI_TRADE_ARROW_OUT_OF_STOCK_SPRITE = ModUtils.id("container/villager/trade_arrow_out_of_stock");

	private MerchantScreenMixin(MerchantMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=276"))
	private int modify276(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 294 : original;
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"
		)
	)
	private ResourceLocation modifyTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_TEXTURE : original;
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"
		)
	)
	private ResourceLocation modifyOutOfStockTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_OUT_OF_STOCK_SPRITE : original;
	}

	@ModifyArg(
		method = "renderProgressBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"
		)
	)
	private ResourceLocation modifyExpBarBackgroundTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_EXPERIENCE_BAR_BACKGROUND_SPRITE : original;
	}

	@ModifyArg(
		method = "renderProgressBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V",
			ordinal = 0
		)
	)
	private ResourceLocation modifyExpBarCurrentTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_EXPERIENCE_BAR_CURRENT_SPRITE : original;
	}

	@ModifyArg(
		method = "renderProgressBar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V",
			ordinal = 1
		)
	)
	private ResourceLocation modifyExpBarResultTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_EXPERIENCE_BAR_RESULT_SPRITE : original;
	}

	@ModifyArg(
		method = "renderScroller",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 0
		)
	)
	private ResourceLocation modifyScrollerTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_SCROLLER_SPRITE : original;
	}

	@ModifyArg(
		method = "renderScroller",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 1
		)
	)
	private ResourceLocation modifyDisabledScrollerTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_SCROLLER_DISABLED_SPRITE : original;
	}

	@ModifyArg(
		method = "renderButtonArrows",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 0
		)
	)
	private ResourceLocation modifyOutOfStockTradeArrowTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_TRADE_ARROW_OUT_OF_STOCK_SPRITE : original;
	}

	@ModifyArg(
		method = "renderButtonArrows",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 1
		)
	)
	private ResourceLocation modifyTradeArrowTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_TRADE_ARROW_SPRITE : original;
	}

	// It turns out the generic "Error" texture was split wrongly on 1.20.2! This is fixed here
	@ModifyExpressionValue(method = "renderBg", at = @At(value = "CONSTANT", args = "intValue=83"))
	private int modifyErrorX(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 92 + 1 : original;
	}

	@ModifyExpressionValue(method = "renderProgressBar", at = @At(value = "CONSTANT", args = "intValue=136"))
	private int modifyExpBarX(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 145 : original;
	}
}
