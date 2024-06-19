package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.networking.payloads.SyncTenfoursizedS2CPacket;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.ConnectedClientData;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
	@Shadow
	@Final
	private MinecraftServer server;

	@Inject(
		method = "onPlayerConnect",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;send(Lnet/minecraft/network/packet/Packet;)V",
			ordinal = 0
		)
	)
	private void sendSyncPacket(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData connectedClientData, CallbackInfo ci, @Local ServerPlayNetworkHandler handler) {
		boolean tenfoursized = this.server.getSaveProperties().getMainWorldProperties().isTenfoursized();
		handler.send(new CustomPayloadS2CPacket(new SyncTenfoursizedS2CPacket(tenfoursized)));
	}
}
