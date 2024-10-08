package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.SplitTextureFurnaceScreen;
import net.minecraft.client.gui.screen.ingame.FurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(FurnaceScreen.class)
public abstract class FurnaceScreenMixin implements SplitTextureFurnaceScreen {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/furnace.png");
	@Unique private static final Identifier EBI_BURN_PROGRESS = ModUtils.id("container/furnace/burn_progress");
	@Unique private static final Identifier EBI_LIT_PROGRESS = ModUtils.id("container/furnace/lit_progress");

	@ModifyExpressionValue(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/ingame/FurnaceScreen;TEXTURE:Lnet/minecraft/util/Identifier;"))
	private static Identifier modifyTexture(Identifier original, @Local(argsOnly = true) PlayerInventory inventory) {
		return inventory.isTenfoursized() ? EBI_TEXTURE : original;
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void modifyAndSetTextures(FurnaceScreenHandler handler, PlayerInventory inventory, Text title, CallbackInfo ci) {
		this.ebi$setProgressTextures(EBI_BURN_PROGRESS, EBI_LIT_PROGRESS);
	}
}
