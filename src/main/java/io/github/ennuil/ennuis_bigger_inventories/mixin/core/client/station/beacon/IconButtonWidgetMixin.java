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
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(targets = "net/minecraft/client/gui/screen/ingame/BeaconScreen$IconButtonWidget")
public abstract class IconButtonWidgetMixin implements SplitTextureBeaconIconButtonWidget {
	@Unique
	private Identifier textureId;

	@Override
	public void ebi$setIconTexture(Identifier textureId) {
		this.textureId = textureId;
	}

	@ModifyArg(
		method = "renderExtra",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private Identifier modifyPatternTexture(Identifier original) {
		// I hate these so much; it's not even an EBI hackjob, it's a Mojang-like hackjob!
		if (MinecraftClient.getInstance().interactionManager.isTenfoursized()) {
			return this.textureId;
		} else {
			return original;
		}
	}
}
