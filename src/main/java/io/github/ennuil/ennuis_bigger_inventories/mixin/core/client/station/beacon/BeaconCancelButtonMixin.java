package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station.beacon;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.SplitTextureBeaconScreenButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net/minecraft/client/gui/screens/inventory/BeaconScreen$BeaconCancelButton")
public abstract class BeaconCancelButtonMixin implements SplitTextureBeaconScreenButton {
	@Unique private static final ResourceLocation EBI_CANCEL_SPRITE = ModUtils.id("container/beacon/cancel");

	@Inject(method = "<init>", at = @At("TAIL"))
	private void initIconTexture(BeaconScreen beaconScreen, int x, int y, CallbackInfo ci) {
		this.ebi$setIconTexture(EBI_CANCEL_SPRITE);
	}
}
