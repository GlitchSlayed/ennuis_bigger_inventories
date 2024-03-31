package io.github.ennuil.ennuis_bigger_inventories.mixin.libfuturecollage.client;

import io.github.ennuil.ennuis_bigger_inventories.impl.libfuturecollage.reloaders.GuiAtlasManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.ResourceType;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.quiltmc.qsl.resource.loader.api.reloader.ResourceReloaderKeys;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Shadow
	@Final
	private TextureManager textureManager;

	@Inject(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/resource/VideoWarningManager;<init>()V"
		)
	)
	private void initStuff(RunArgs args, CallbackInfo ci) {
		ResourceLoader.get(ResourceType.CLIENT_RESOURCES).registerReloader(new GuiAtlasManager(this.textureManager));
		ResourceLoader.get(ResourceType.CLIENT_RESOURCES).addReloaderOrdering(ResourceReloaderKeys.Client.SPRITE_ATLASES, GuiAtlasManager.RELOADER_ID);
	}
}
