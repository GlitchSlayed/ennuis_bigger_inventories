package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import net.minecraft.client.renderer.RenderPipelines;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CartographyTableScreen;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CartographyTableMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
@Mixin(CartographyTableScreen.class)
public abstract class CartographyTableScreenMixin extends AbstractContainerScreen<CartographyTableMenu> {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/cartography_table.png");
	@Unique private static final Identifier EBI_ERROR_SPRITE = ModUtils.id("container/cartography_table/error");
	@Unique private static final Identifier EBI_LOCKED_SPRITE = ModUtils.id("container/cartography_table/locked");
	@Unique private static final Identifier EBI_MAP_SPRITE = ModUtils.id("container/cartography_table/map");
	@Unique private static final Identifier EBI_DUPLICATED_MAP_SPRITE = ModUtils.id("container/cartography_table/duplicated_map");
	@Unique private static final Identifier EBI_SCALED_MAP_SPRITE = ModUtils.id("container/cartography_table/scaled_map");

	private CartographyTableScreenMixin(CartographyTableMenu menu, Inventory inventory, Component title) {
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
	private Identifier modifyErrorTexture1(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ERROR_SPRITE : original;
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyErrorTexture2(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ERROR_SPRITE : original;
	}

	@ModifyArg(
		method = "extractResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyScaledMapTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_SCALED_MAP_SPRITE : original;
	}

	@WrapOperation(
		method = "extractResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private void modifyDuplicatedMapTexture1(GuiGraphicsExtractor graphics, RenderPipeline function, Identifier texture, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 0) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EBI_DUPLICATED_MAP_SPRITE,  i + 76 + 16, y, width, 50);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "extractResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 2
		)
	)
	private void modifyDuplicatedMapTexture2(GuiGraphicsExtractor graphics, RenderPipeline function, Identifier texture, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 0) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EBI_DUPLICATED_MAP_SPRITE,  i + 76, y, width, 50);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	@ModifyArg(
		method = "extractResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 3
		)
	)
	private Identifier modifyLockedMapTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_MAP_SPRITE : original;
	}

	@ModifyArg(
		method = "extractResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 4
		)
	)
	private Identifier modifyLockedLockTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_LOCKED_SPRITE : original;
	}

	@ModifyArg(
		method = "extractResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 5
		)
	)
	private Identifier modifyMapTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_MAP_SPRITE : original;
	}

	@ModifyExpressionValue(method = "extractBackground", at = @At(value = "CONSTANT", args = "intValue=35"))
	private int modify35(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 44 : original;
	}

	@ModifyExpressionValue(method = "extractResultingMap", at = @At(value = "CONSTANT", args = "intValue=67"))
	private int modify67(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 76 : original;
	}

	@ModifyExpressionValue(method = "extractResultingMap", at = @At(value = "CONSTANT", args = "intValue=86"))
	private int modify86(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 95 : original;
	}

	@ModifyExpressionValue(method = "extractResultingMap", at = @At(value = "CONSTANT", args = "intValue=85"))
	private int modify85(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 94 : original;
	}

	@ModifyExpressionValue(method = "extractResultingMap", at = @At(value = "CONSTANT", args = "intValue=70"))
	private int modify70(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 79 : original;
	}

	@ModifyExpressionValue(method = "extractResultingMap", at = @At(value = "CONSTANT", args = "intValue=71"))
	private int modify71(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 80 : original;
	}

	@ModifyExpressionValue(method = "extractResultingMap", at = @At(value = "CONSTANT", args = "intValue=118"))
	private int modify118(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 127 : original;
	}
}
