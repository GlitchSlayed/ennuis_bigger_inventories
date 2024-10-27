package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.SplitSpriteFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.BlastFurnaceScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(BlastFurnaceScreen.class)
public abstract class BlastFurnaceScreenMixin implements SplitSpriteFurnaceScreen {
	@Unique private static final ResourceLocation EBI_TEXTURE = ModUtils.id("textures/gui/container/blast_furnace.png");
	@Unique private static final ResourceLocation EBI_BURN_PROGRESS_SPRITE = ModUtils.id("container/blast_furnace/burn_progress");
	@Unique private static final ResourceLocation EBI_LIT_PROGRESS_SPRITE = ModUtils.id("container/blast_furnace/lit_progress");

	@ModifyExpressionValue(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/BlastFurnaceScreen;TEXTURE:Lnet/minecraft/resources/ResourceLocation;"))
	private static ResourceLocation modifyTexture(ResourceLocation original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? EBI_TEXTURE : original;
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void modifyAndSetTextures(CallbackInfo ci) {
		this.ebi$setProgressSprites(EBI_BURN_PROGRESS_SPRITE, EBI_LIT_PROGRESS_SPRITE);
	}
}
