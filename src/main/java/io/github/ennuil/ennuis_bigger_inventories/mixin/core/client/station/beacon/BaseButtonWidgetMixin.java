package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station.beacon;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(targets = "net/minecraft/client/gui/screen/ingame/BeaconScreen$BaseButtonWidget")
public abstract class BaseButtonWidgetMixin extends PressableWidget {
	@Unique private static final Identifier EBI_BUTTON_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/beacon/button");
	@Unique private static final Identifier EBI_BUTTON_DISABLED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/beacon/button_disabled");
	@Unique private static final Identifier EBI_BUTTON_HIGHLIGHTED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/beacon/button_highlighted");
	@Unique private static final Identifier EBI_BUTTON_SELECTED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/beacon/button_selected");

	private BaseButtonWidgetMixin(int x, int y, int width, int height, Text message) {
		super(x, y, width, height, message);
	}

	@WrapOperation(
		method = "drawWidget",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
		)
	)
	private void modifyPatternTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 3) int j) {
		// Wait ewwwwww, Minecraft uses MinecraftClient.getInstance a lot inside of widgets
		if (MinecraftClient.getInstance().interactionManager.isTenfoursized()) {
			var patternTexture = switch (j / this.width) {
				case 1 -> EBI_BUTTON_SELECTED_TEXTURE;
				case 2 -> EBI_BUTTON_DISABLED_TEXTURE;
				case 3 -> EBI_BUTTON_HIGHLIGHTED_TEXTURE;
				default -> EBI_BUTTON_TEXTURE;
			};
			graphics.drawGuiTexture(patternTexture, x,  y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}
}
