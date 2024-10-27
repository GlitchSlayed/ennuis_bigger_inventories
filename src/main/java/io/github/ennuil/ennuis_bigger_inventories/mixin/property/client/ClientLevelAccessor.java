package io.github.ennuil.ennuis_bigger_inventories.mixin.property.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@ClientOnly
@Mixin(ClientLevel.class)
public interface ClientLevelAccessor {
	@Accessor
	Minecraft getMinecraft();
}
