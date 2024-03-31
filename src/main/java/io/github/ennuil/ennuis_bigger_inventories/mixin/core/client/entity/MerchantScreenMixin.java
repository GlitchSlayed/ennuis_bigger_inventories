package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
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
	private static final Identifier BIGGER_TEXTURE = new Identifier("ennuis_bigger_inventories", "textures/gui/container/villager.png");

	@Unique private static final Identifier EBI_OUT_OF_STOCK_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/villager/out_of_stock");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_BACKGROUND_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/villager/experience_bar_background");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_CURRENT_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/villager/experience_bar_current");
	@Unique private static final Identifier EBI_EXPERIENCE_BAR_RESULT_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/villager/experience_bar_result");
	@Unique private static final Identifier EBI_SCROLLER_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/villager/scroller");
	@Unique private static final Identifier EBI_SCROLLER_DISABLED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/villager/scroller_disabled");
	@Unique private static final Identifier EBI_TRADE_ARROW_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/villager/trade_arrow");
	@Unique private static final Identifier EBI_TRADE_ARROW_OUT_OF_STOCK_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/villager/trade_arrow_out_of_stock");

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
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V",
			ordinal = 1
		)
	)
	private void modifyOutOfStockTexture(GuiGraphics graphics, Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			// It turns out the generic "Error" texture was split wrongly on 1.20.2! This is fixed here
			graphics.drawGuiTexture(EBI_OUT_OF_STOCK_TEXTURE, this.x + 92 + 99 + 1, y, width, height);
		} else {
			original.call(graphics, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		}
	}

	// Fix 100s into 102 like Mojang did on 1.20.2
	@ModifyExpressionValue(method = "drawLevelInfo", at = @At(value = "CONSTANT", args = "intValue=100"))
	private int modify100(int original) {
		return this.client.interactionManager.isTenfoursized() ? 102 : original;
	}

	@ModifyExpressionValue(method = "drawLevelInfo", at = @At(value = "CONSTANT", args = "floatValue=100.0"))
	private float modify100F(float original) {
		return this.client.interactionManager.isTenfoursized() ? 102.0F : original;
	}

	@WrapOperation(
		method = "drawLevelInfo",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V",
			ordinal = 0
		)
	)
	private void modifyExpBarBackgroundTexture(GuiGraphics graphics, Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original, @Local(ordinal = 0, argsOnly = true) int ox) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_EXPERIENCE_BAR_BACKGROUND_TEXTURE, ox + 145, y, width, height);
		} else {
			original.call(graphics, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		}
	}

	@WrapOperation(
		method = "drawLevelInfo",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V",
			ordinal = 1
		)
	)
	private void modifyExpBarCurrentTexture(GuiGraphics graphics, Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original, @Local(ordinal = 0, argsOnly = true) int ox, @Local(ordinal = 6) int m) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_EXPERIENCE_BAR_CURRENT_TEXTURE, 102, height, 0, 0, ox + 145, y, m, height);
		} else {
			original.call(graphics, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		}
	}

	@WrapOperation(
		method = "drawLevelInfo",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V",
			ordinal = 2
		)
	)
	private void modifyExpBarResultTexture(GuiGraphics graphics, Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original, @Local(ordinal = 0, argsOnly = true) int ox, @Local(ordinal = 6) int m) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_EXPERIENCE_BAR_RESULT_TEXTURE, 102, 5, m, 0, ox + 145 + m, y - 1, width, 5);
		} else {
			original.call(graphics, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		}
	}

	@WrapOperation(
		method = "renderScrollbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V",
			ordinal = 0
		)
	)
	private void modifyScrollerTexture(GuiGraphics graphics, Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_SCROLLER_TEXTURE, x, y, width, height);
		} else {
			original.call(graphics, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		}
	}

	@WrapOperation(
		method = "renderScrollbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V",
			ordinal = 1
		)
	)
	private void modifyDisabledScrollerTexture(GuiGraphics graphics, Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_SCROLLER_DISABLED_TEXTURE, x, y, width, height);
		} else {
			original.call(graphics, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		}
	}

	@WrapOperation(
		method = "renderArrow",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V",
			ordinal = 0
		)
	)
	private void modifyOutOfStockTradeArrowTexture(GuiGraphics graphics, Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_TRADE_ARROW_OUT_OF_STOCK_TEXTURE, x, y, width, height);
		} else {
			original.call(graphics, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		}
	}

	@WrapOperation(
		method = "renderArrow",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V",
			ordinal = 1
		)
	)
	private void modifyTradeArrowTexture(GuiGraphics graphics, Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_TRADE_ARROW_TEXTURE, x, y, width, height);
		} else {
			original.call(graphics, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		}
	}
}
