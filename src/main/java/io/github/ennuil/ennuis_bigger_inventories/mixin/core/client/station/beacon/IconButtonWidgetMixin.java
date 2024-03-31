package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station.beacon;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.SplitTextureBeaconIconButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(targets = "net/minecraft/client/gui/screen/ingame/BeaconScreen$IconButtonWidget")
public abstract class IconButtonWidgetMixin implements SplitTextureBeaconIconButtonWidget {
	@Unique
	private Identifier textureId;

	@Override
	public void ebi$setIconTexture(Identifier textureId) {
		this.textureId = textureId;
	}

	@WrapOperation(
		method = "renderExtra",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
		)
	)
	private void modifyPatternTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		// I hate these so much; it's not even an EBI hackjob, it's a Mojang-like hackjob!
		if (MinecraftClient.getInstance().interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(this.textureId, x,  y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}
}
