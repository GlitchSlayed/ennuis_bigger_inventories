package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Lifecycle;
import io.github.ennuil.ennuis_bigger_inventories.impl.HackjobKitImpl;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldCreatorExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldInfoExtensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.world.WorldCreationContext;
import net.minecraft.client.world.WorldCreator;
import net.minecraft.registry.LayeredRegistryManager;
import net.minecraft.registry.ServerRegistryLayer;
import net.minecraft.world.WorldInfo;
import net.minecraft.world.WorldSaveProperties;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

@ClientOnly
@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
	@Shadow
	@Final
	WorldCreator worldCreator;

	@Inject(method = "createFromExisting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/WorldCreator;getGameRules()Lnet/minecraft/world/GameRules;"))
	private static void setTenfoursizedFromExisting(MinecraftClient client, Screen parent, WorldInfo info, WorldCreationContext context, Path path, CallbackInfoReturnable<CreateWorldScreen> cir, @Local CreateWorldScreen screen) {
		((WorldCreatorExtensions) screen.getWorldCreator()).ebi$setTenfoursized(((WorldInfoExtensions) (Object) info).ebi$isTenfoursized());
	}

	@ModifyReturnValue(method = "createWorldInfo", at = @At(value = "RETURN", ordinal = 0))
	private WorldInfo tenfoursizeDebugWorldInfo(WorldInfo original) {
		((WorldInfoExtensions) (Object) original).ebi$setTenfoursized(true);
		return original;
	}

	@ModifyReturnValue(method = "createWorldInfo", at = @At(value = "RETURN", ordinal = 1))
	private WorldInfo tenfoursizeWorldInfo(WorldInfo original) {
		((WorldInfoExtensions) (Object) original).ebi$setTenfoursized(((WorldCreatorExtensions) this.worldCreator).ebi$isTenfoursized());
		return original;
	}

	@Inject(method = "createNewWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/integrated/IntegratedServerLoader;start(Lnet/minecraft/world/storage/WorldSaveStorage$Session;Lnet/minecraft/server/ServerReloadableResources;Lnet/minecraft/registry/LayeredRegistryManager;Lnet/minecraft/world/SaveProperties;)V"))
	private void manuallyTuneHackjob(WorldSaveProperties.WorldType type, LayeredRegistryManager<ServerRegistryLayer> manager, Lifecycle lifecycle, CallbackInfo ci, @Local WorldInfo info) {
		// While the other setInstances do a swell job at comebacks, it doesn't cover world creation at all!
		HackjobKitImpl.TenfoursizedProperty.setInstance(((WorldInfoExtensions) (Object) info).ebi$isTenfoursized());
	}
}
