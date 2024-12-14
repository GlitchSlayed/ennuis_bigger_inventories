package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin extends ClientCommonPacketListenerImpl {
	private ClientPacketListenerMixin(Minecraft client, Connection connection, CommonListenerCookie commonListenerCookie) {
		super(client, connection, commonListenerCookie);
	}

	@ModifyExpressionValue(method = "handleContainerSetSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;isHotbarSlot(I)Z"))
	private boolean modifyIsHotbarSlot(boolean original, @Local int slot) {
		return this.minecraft.gameMode.isTenfoursized()
			? slot >= 9 + 10 * 3 && slot <= 9 + 10 * 4
			: original;
	}
}
