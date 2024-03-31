package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station.beacon;

import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.SplitTextureBeaconIconButtonWidget;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(targets = "net/minecraft/client/gui/screen/ingame/BeaconScreen$CancelButtonWidget")
public abstract class CancelButtonWidgetMixin implements SplitTextureBeaconIconButtonWidget {
	@Unique private static final Identifier EBI_CANCEL_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/beacon/cancel");

	@Inject(method = "<init>", at = @At("TAIL"))
	private void initIconTexture(BeaconScreen beaconScreen, int x, int y, CallbackInfo ci) {
		this.ebi$setIconTexture(EBI_CANCEL_TEXTURE);
	}
}
