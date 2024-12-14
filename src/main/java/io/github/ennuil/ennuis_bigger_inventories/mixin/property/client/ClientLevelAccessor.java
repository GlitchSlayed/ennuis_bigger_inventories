package io.github.ennuil.ennuis_bigger_inventories.mixin.property.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(ClientLevel.class)
public interface ClientLevelAccessor {
	@Accessor
	Minecraft getMinecraft();
}
