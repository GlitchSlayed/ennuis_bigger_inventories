package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.HackjobKitImpl;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.LevelSettingsExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldCreationUiStateExtensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.server.RegistryLayer;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.storage.PrimaryLevelData;
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
	WorldCreationUiState uiState;

	@Inject(method = "createFromExisting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState;getGameRules()Lnet/minecraft/world/level/GameRules;"))
	private static void setTenfoursizedFromExisting(Minecraft client, Screen parent, LevelSettings info, WorldCreationContext context, Path path, CallbackInfoReturnable<CreateWorldScreen> cir, @Local CreateWorldScreen screen) {
		((WorldCreationUiStateExtensions) screen.getUiState()).ebi$setTenfoursized(((LevelSettingsExtensions) (Object) info).ebi$isTenfoursized());
	}

	@ModifyReturnValue(method = "createLevelSettings", at = @At(value = "RETURN", ordinal = 0))
	private LevelSettings tenfoursizeDebugLevelSettings(LevelSettings original) {
		((LevelSettingsExtensions) (Object) original).ebi$setTenfoursized(true);
		return original;
	}

	@ModifyReturnValue(method = "createLevelSettings", at = @At(value = "RETURN", ordinal = 1))
	private LevelSettings tenfoursizeLevelSettings(LevelSettings original) {
		((LevelSettingsExtensions) (Object) original).ebi$setTenfoursized(((WorldCreationUiStateExtensions) this.uiState).ebi$isTenfoursized());
		return original;
	}

	@Inject(
		method = "createWorldAndCleanup",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldCallback;create(Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;Lnet/minecraft/core/LayeredRegistryAccess;Lnet/minecraft/world/level/storage/PrimaryLevelData;Ljava/nio/file/Path;)Z"
		)
	)
	private void manuallyTuneHackjob(LayeredRegistryAccess<RegistryLayer> layeredRegistryAccess, PrimaryLevelData primaryLevelData, CallbackInfo ci) {
		// While the other setInstances do a swell job at comebacks, it doesn't cover world creation at all!
		// FIXME - Verify that **this works at all costs!!!!!**
		HackjobKitImpl.TenfoursizedProperty.setInstance(primaryLevelData.isTenfoursized());
	}
}
