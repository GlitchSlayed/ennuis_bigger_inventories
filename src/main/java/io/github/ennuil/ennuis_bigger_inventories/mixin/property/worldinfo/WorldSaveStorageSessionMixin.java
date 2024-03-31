package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo;

import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldSavePropertiesExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldSaveStorageSessionExtensions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Util;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.storage.WorldSaveStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;

@Mixin(WorldSaveStorage.Session.class)
public class WorldSaveStorageSessionMixin implements WorldSaveStorageSessionExtensions {
	@Shadow
	@Final
	WorldSaveStorage.WorldDirectory directory;

	@Override
	public void ebi$backupLevelDatFileAndTenfoursize(DynamicRegistryManager registryManager, SaveProperties saveProperties) {
		var file = this.directory.path().toFile();
		var nbt = ((WorldSavePropertiesExtensions) saveProperties).ebi$cloneWorldNbtAndTenfoursize(registryManager);
		var rootNbt = new NbtCompound();
		rootNbt.put("Data", nbt);

		try {
			var tempFile = File.createTempFile("level", ".dat", file);
			NbtIo.writeCompressed(rootNbt, tempFile);
			var oldWorldFile = this.directory.getOldWorldDatPath().toFile();
			var	worldFile = this.directory.getWorldDatPath().toFile();
			Util.backupAndReplace(worldFile, tempFile, oldWorldFile);
		} catch (Exception e) {
			WorldSaveStorageAccessor.getLogger().error("Failed to save level {}", file, e);
		}
	}
}
