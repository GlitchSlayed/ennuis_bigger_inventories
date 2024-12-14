package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.SplitSpriteFurnaceScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(FurnaceScreen.class)
public abstract class FurnaceScreenMixin implements SplitSpriteFurnaceScreen {
	@Unique private static final ResourceLocation EBI_TEXTURE = ModUtils.id("textures/gui/container/furnace.png");
	@Unique private static final ResourceLocation EBI_BURN_PROGRESS_SPRITE = ModUtils.id("container/furnace/burn_progress");
	@Unique private static final ResourceLocation EBI_LIT_PROGRESS_SPRITE = ModUtils.id("container/furnace/lit_progress");

	@ModifyExpressionValue(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/FurnaceScreen;TEXTURE:Lnet/minecraft/resources/ResourceLocation;"))
	private static ResourceLocation modifyTexture(ResourceLocation original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? EBI_TEXTURE : original;
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void modifyAndSetTextures(FurnaceMenu menu, Inventory inventory, Component title, CallbackInfo ci) {
		this.ebi$setProgressSprites(EBI_BURN_PROGRESS_SPRITE, EBI_LIT_PROGRESS_SPRITE);
	}
}
