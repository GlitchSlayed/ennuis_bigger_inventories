package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo;

import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldSavePropertiesExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldSaveStorageSessionExtensions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.storage.WorldSaveStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WorldSaveStorage.Session.class)
public abstract class WorldSaveStorageSessionMixin implements WorldSaveStorageSessionExtensions {
	@Shadow
	protected abstract void method_54538(NbtCompound compound);

	@Override
	public void ebi$backupLevelDatFileAndTenfoursize(DynamicRegistryManager registryManager, SaveProperties saveProperties) {
		var nbt = ((WorldSavePropertiesExtensions) saveProperties).ebi$cloneWorldNbtAndTenfoursize(registryManager);
		var rootNbt = new NbtCompound();
		rootNbt.put("Data", nbt);
		this.method_54538(rootNbt);
	}
}
