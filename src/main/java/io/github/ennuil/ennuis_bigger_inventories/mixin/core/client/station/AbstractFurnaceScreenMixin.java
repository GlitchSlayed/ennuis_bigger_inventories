package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.SplitSpriteFurnaceScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
	private ResourceLocation burnProgressSprite;

	@Unique
	private ResourceLocation litProgressSprite;

	private AbstractFurnaceScreenMixin(T menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@Override
	public void ebi$setProgressSprites(ResourceLocation burnProgressSprite, ResourceLocation litProgressSprite) {
		this.burnProgressSprite = burnProgressSprite;
		this.litProgressSprite = litProgressSprite;
	}

	@ModifyExpressionValue(method = {"init", "getRecipeBookButtonPosition"}, at = @At(value = "CONSTANT", args = "intValue=20", ordinal = 0))
	private int modify20s(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 29 : original;
	}

	@WrapOperation(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V",
			ordinal = 0
		)
	)
	private void modifyLitProgress(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, this.litProgressSprite, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 65, y, width, height);
		} else {
			original.call(graphics, function, texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V",
			ordinal = 1
		)
	)
	private void modifyBurnProgress(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, this.burnProgressSprite, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 88, y, width, height);
		} else {
			original.call(graphics, function, texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, width, height);
		}
	}
}
