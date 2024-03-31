package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo;

import net.minecraft.world.storage.WorldSaveStorage;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldSaveStorage.class)
public interface WorldSaveStorageAccessor {
    @Accessor("LOGGER")
    static Logger getLogger() {
		throw new IllegalStateException("Mixin injection failed");
    }
}
