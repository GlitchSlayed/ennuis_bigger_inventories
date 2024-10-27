package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo;

import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.PrimaryLevelDataExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.LevelStorageAccessExtensions;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LevelStorageSource.LevelStorageAccess.class)
public abstract class LevelStorageAccessMixin implements LevelStorageAccessExtensions {
	@Shadow
	protected abstract void saveLevelData(CompoundTag compound);

	@Override
	public void ebi$backupLevelDataAndTenfoursize(RegistryAccess registryManager, WorldData saveProperties) {
		var nbt = ((PrimaryLevelDataExtensions) saveProperties).ebi$cloneWorldNbtAndTenfoursize(registryManager);
		var rootNbt = new CompoundTag();
		rootNbt.put("Data", nbt);
		this.saveLevelData(rootNbt);
	}
}
