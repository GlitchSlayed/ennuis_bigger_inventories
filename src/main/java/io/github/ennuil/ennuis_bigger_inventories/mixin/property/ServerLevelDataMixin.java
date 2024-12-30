package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import io.github.ennuil.ennuis_bigger_inventories.api.EBIServerLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerLevelData.class)
public interface ServerLevelDataMixin extends EBIServerLevelData {}
