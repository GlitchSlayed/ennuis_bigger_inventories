package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station.beacon;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(targets = "net/minecraft/client/gui/screen/ingame/BeaconScreen$BaseButtonWidget")
public abstract class BaseButtonWidgetMixin extends PressableWidget {
	@Unique private static final Identifier EBI_BUTTON_DISABLED_TEXTURE = ModUtils.id("container/beacon/button_disabled");
	@Unique private static final Identifier EBI_BUTTON_SELECTED_TEXTURE = ModUtils.id("container/beacon/button_selected");
	@Unique private static final Identifier EBI_BUTTON_HIGHLIGHTED_TEXTURE = ModUtils.id("container/beacon/button_highlighted");
	@Unique private static final Identifier EBI_BUTTON_TEXTURE = ModUtils.id("container/beacon/button");

	private BaseButtonWidgetMixin(int x, int y, int width, int height, Text message) {
		super(x, y, width, height, message);
	}

	@WrapOperation(
		method = "drawWidget",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private void modifyPatternTexture(GuiGraphics graphics, Identifier texture, int x, int y, int width, int height, Operation<Void> original, @Local Identifier id) {
		// Wait ewwwwww, Minecraft uses MinecraftClient.getInstance a lot inside of widgets
		if (MinecraftClient.getInstance().interactionManager.isTenfoursized()) {
			Identifier patternTexture;
			if (id.equals(BeaconScreenAccessor.getButtonDisabled())) {
				patternTexture = EBI_BUTTON_DISABLED_TEXTURE;
			} else if (id.equals(BeaconScreenAccessor.getButtonSelected())) {
				patternTexture = EBI_BUTTON_SELECTED_TEXTURE;
			} else if (id.equals(BeaconScreenAccessor.getButtonHighlighted())) {
				patternTexture = EBI_BUTTON_HIGHLIGHTED_TEXTURE;
			} else {
				patternTexture = EBI_BUTTON_TEXTURE;
			}
			graphics.drawGuiTexture(patternTexture, x,  y, width, height);
		} else {
			original.call(graphics, texture, x, y, width, height);
		}
	}
}
