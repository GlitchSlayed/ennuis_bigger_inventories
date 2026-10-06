package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import net.minecraft.client.renderer.RenderPipelines;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.BrewingStandMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
@Mixin(BrewingStandScreen.class)
public abstract class BrewingStandScreenMixin extends AbstractContainerScreen<BrewingStandMenu> {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/brewing_stand.png");
	@Unique private static final Identifier EBI_BREW_PROGRESS_SPRITE = ModUtils.id("container/brewing_stand/brew_progress");
	@Unique private static final Identifier EBI_BUBBLES_SPRITE = ModUtils.id("container/brewing_stand/bubbles");
	@Unique private static final Identifier EBI_FUEL_LENGTH_SPRITE = ModUtils.id("container/brewing_stand/fuel_length");

	private BrewingStandScreenMixin(BrewingStandMenu menu, Inventory inventory, Component title) {
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

	@WrapOperation(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V",
			ordinal = 0
		)
	)
	private void modifyFuelLengthTexture(GuiGraphicsExtractor graphics, RenderPipeline function, Identifier texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EBI_FUEL_LENGTH_SPRITE, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 70, y, width, height);
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
	private void modifyBrewProgressTexture(GuiGraphicsExtractor graphics, RenderPipeline function, Identifier texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EBI_BREW_PROGRESS_SPRITE, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 107, y, width, height);
		} else {
			original.call(graphics, function, texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V",
			ordinal = 2
		)
	)
	private void modifyBubblesTexture(GuiGraphicsExtractor graphics, RenderPipeline function, Identifier texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EBI_BUBBLES_SPRITE, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 73, y, width, height);
		} else {
			original.call(graphics, function, texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, width, height);
		}
	}
}
