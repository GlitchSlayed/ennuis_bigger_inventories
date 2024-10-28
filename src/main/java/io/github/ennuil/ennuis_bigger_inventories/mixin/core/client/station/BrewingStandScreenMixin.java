package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.BrewingStandMenu;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Function;

@ClientOnly
@Mixin(BrewingStandScreen.class)
public abstract class BrewingStandScreenMixin extends AbstractContainerScreen<BrewingStandMenu> {
	@Unique private static final ResourceLocation EBI_TEXTURE = ModUtils.id("textures/gui/container/brewing_stand.png");
	@Unique private static final ResourceLocation EBI_BREW_PROGRESS_SPRITE = ModUtils.id("container/brewing_stand/brew_progress");
	@Unique private static final ResourceLocation EBI_BUBBLES_SPRITE = ModUtils.id("container/brewing_stand/bubbles");
	@Unique private static final ResourceLocation EBI_FUEL_LENGTH_SPRITE = ModUtils.id("container/brewing_stand/fuel_length");

	private BrewingStandScreenMixin(BrewingStandMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
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

	@WrapOperation(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V",
			ordinal = 0
		)
	)
	private void modifyFuelLengthTexture(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, EBI_FUEL_LENGTH_SPRITE, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 70, y, width, height);
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
	private void modifyBrewProgressTexture(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, EBI_BREW_PROGRESS_SPRITE, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 107, y, width, height);
		} else {
			original.call(graphics, function, texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V",
			ordinal = 2
		)
	)
	private void modifyBubblesTexture(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, EBI_BUBBLES_SPRITE, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 73, y, width, height);
		} else {
			original.call(graphics, function, texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, width, height);
		}
	}
}
