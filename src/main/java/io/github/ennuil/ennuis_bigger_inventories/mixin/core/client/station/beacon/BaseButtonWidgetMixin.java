package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station.beacon;

import net.minecraft.client.renderer.RenderPipelines;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net/minecraft/client/gui/screens/inventory/BeaconScreen$BeaconScreenButton")
public abstract class BaseButtonWidgetMixin extends AbstractButton {
	@Unique private static final Identifier EBI_BUTTON_DISABLED_SPRITE = ModUtils.id("container/beacon/button_disabled");
	@Unique private static final Identifier EBI_BUTTON_SELECTED_SPRITE = ModUtils.id("container/beacon/button_selected");
	@Unique private static final Identifier EBI_BUTTON_HIGHLIGHTED_SPRITE = ModUtils.id("container/beacon/button_highlighted");
	@Unique private static final Identifier EBI_BUTTON_SPRITE = ModUtils.id("container/beacon/button");

	private BaseButtonWidgetMixin(int x, int y, int width, int height, Component message) {
		super(x, y, width, height, message);
	}

	@WrapOperation(
		method = "extractContents",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"
		)
	)
	private void modifyPatternTexture(GuiGraphicsExtractor graphics, RenderPipeline function, Identifier texture, int x, int y, int width, int height, Operation<Void> original, @Local Identifier id) {
		// Wait ewwwwww, Minecraft uses MinecraftClient.getInstance a lot inside of widgets
		if (Minecraft.getInstance().gameMode.isTenfoursized()) {
			Identifier patternTexture;
			if (id.equals(BeaconScreenAccessor.getButtonDisabledSprite())) {
				patternTexture = EBI_BUTTON_DISABLED_SPRITE;
			} else if (id.equals(BeaconScreenAccessor.getButtonSelectedSprite())) {
				patternTexture = EBI_BUTTON_SELECTED_SPRITE;
			} else if (id.equals(BeaconScreenAccessor.getButtonHighlightedSprite())) {
				patternTexture = EBI_BUTTON_HIGHLIGHTED_SPRITE;
			} else {
				patternTexture = EBI_BUTTON_SPRITE;
			}
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, patternTexture, x,  y, width, height);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}
}
