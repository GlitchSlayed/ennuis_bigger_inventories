package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import net.minecraft.client.renderer.RenderPipelines;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.SplitSpriteFurnaceScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
@Mixin(AbstractFurnaceScreen.class)
public abstract class AbstractFurnaceScreenMixin<T extends AbstractFurnaceMenu> extends AbstractContainerScreen<T> implements SplitSpriteFurnaceScreen {
	@Unique
	private Identifier burnProgressSprite;

	@Unique
	private Identifier litProgressSprite;

	private AbstractFurnaceScreenMixin(T menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@Override
	public void ebi$setProgressSprites(Identifier burnProgressSprite, Identifier litProgressSprite) {
		this.burnProgressSprite = burnProgressSprite;
		this.litProgressSprite = litProgressSprite;
	}

	@ModifyExpressionValue(method = {"init", "getRecipeBookButtonPosition"}, at = @At(value = "CONSTANT", args = "intValue=20", ordinal = 0))
	private int modify20s(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 29 : original;
	}

	@WrapOperation(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V",
			ordinal = 0
		)
	)
	private void modifyLitProgress(GuiGraphicsExtractor graphics, RenderPipeline function, Identifier texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.litProgressSprite, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 65, y, width, height);
		} else {
			original.call(graphics, function, texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V",
			ordinal = 1
		)
	)
	private void modifyBurnProgress(GuiGraphicsExtractor graphics, RenderPipeline function, Identifier texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.burnProgressSprite, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 88, y, width, height);
		} else {
			original.call(graphics, function, texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, width, height);
		}
	}
}
