package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyServerLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerLevelData.class)
public interface ServerLevelDataMixin extends EnnyServerLevelData {}
