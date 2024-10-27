package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import io.github.ennuil.ennuis_bigger_inventories.api.EnnyServerLevelData;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.ServerLevelDataExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.LevelSettingsExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.PrimaryLevelDataExtensions;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PrimaryLevelData.class)
public abstract class PrimaryLevelDataMixin implements EnnyServerLevelData, ServerLevelDataExtensions, PrimaryLevelDataExtensions {
	@Shadow
	private LevelSettings settings;

	@Shadow
	protected abstract void setTagData(RegistryAccess registryAccess, CompoundTag levelNbt, @Nullable CompoundTag playerNbt);

	@Override
	public boolean isTenfoursized() {
		return ((LevelSettingsExtensions) (Object) this.settings).ebi$isTenfoursized();
	}

	@Override
	public void ebi$setTenfoursized(boolean tenfoursized) {
		((LevelSettingsExtensions) (Object) this.settings).ebi$setTenfoursized(tenfoursized);
	}

	@ModifyReturnValue(method = "parse", at = @At("RETURN"))
	private static <T> PrimaryLevelData readTenfoursizedProperty(PrimaryLevelData original, Dynamic<T> dynamic, LevelSettings settings, PrimaryLevelData.SpecialWorldProperty worldType, WorldOptions worldGenOptions, Lifecycle lifecycle) {
		boolean value = ((LevelSettingsExtensions) (Object) settings).ebi$isTenfoursized();
		((ServerLevelDataExtensions) original).ebi$setTenfoursized(value);
		return original;
	}

	@Inject(method = "setTagData", at = @At("TAIL"))
	private void updateTenfoursizedProperty(RegistryAccess registryManager, CompoundTag levelNbt, CompoundTag playerNbt, CallbackInfo ci) {
		levelNbt.putBoolean("ennuis_bigger_inventories:is_tenfoursized", ((LevelSettingsExtensions) (Object) this.settings).ebi$isTenfoursized());
	}

	@Override
	public CompoundTag ebi$cloneWorldNbtAndTenfoursize(RegistryAccess registryAccess) {
		var nbt = new CompoundTag();
		this.setTagData(registryAccess, nbt, null);
		nbt.putBoolean("ennuis_bigger_inventories:is_tenfoursized", true);
		return nbt;
	}
}
