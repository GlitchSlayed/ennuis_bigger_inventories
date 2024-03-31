package io.github.ennuil.ennuis_bigger_inventories.mixin.property.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@ClientOnly
@Mixin(ClientWorld.class)
public interface ClientWorldAccessor {
	@Accessor
	MinecraftClient getClient();
}
