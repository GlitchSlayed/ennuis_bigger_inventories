package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyServerWorldProperties;
import net.minecraft.world.ServerWorldProperties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerWorldProperties.class)
public interface ServerWorldPropertiesMixin extends EnnyServerWorldProperties {}
