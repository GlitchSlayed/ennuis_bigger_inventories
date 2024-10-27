package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station.beacon;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(targets = "net/minecraft/client/gui/screens/inventory/BeaconScreen$BeaconScreenButton")
public abstract class BaseButtonWidgetMixin extends AbstractButton {
	@Unique private static final ResourceLocation EBI_BUTTON_DISABLED_SPRITE = ModUtils.id("container/beacon/button_disabled");
	@Unique private static final ResourceLocation EBI_BUTTON_SELECTED_SPRITE = ModUtils.id("container/beacon/button_selected");
	@Unique private static final ResourceLocation EBI_BUTTON_HIGHLIGHTED_SPRITE = ModUtils.id("container/beacon/button_highlighted");
	@Unique private static final ResourceLocation EBI_BUTTON_SPRITE = ModUtils.id("container/beacon/button");

	private BaseButtonWidgetMixin(int x, int y, int width, int height, Component message) {
		super(x, y, width, height, message);
	}

	@WrapOperation(
		method = "renderWidget",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"
		)
	)
	private void modifyPatternTexture(GuiGraphics graphics, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original, @Local ResourceLocation id) {
		// Wait ewwwwww, Minecraft uses MinecraftClient.getInstance a lot inside of widgets
		if (Minecraft.getInstance().gameMode.isTenfoursized()) {
			ResourceLocation patternTexture;
			if (id.equals(BeaconScreenAccessor.getButtonDisabledSprite())) {
				patternTexture = EBI_BUTTON_DISABLED_SPRITE;
			} else if (id.equals(BeaconScreenAccessor.getButtonSelectedSprite())) {
				patternTexture = EBI_BUTTON_SELECTED_SPRITE;
			} else if (id.equals(BeaconScreenAccessor.getButtonHighlightedSprite())) {
				patternTexture = EBI_BUTTON_HIGHLIGHTED_SPRITE;
			} else {
				patternTexture = EBI_BUTTON_SPRITE;
			}
			graphics.blitSprite(patternTexture, x,  y, width, height);
		} else {
			original.call(graphics, texture, x, y, width, height);
		}
	}
}
