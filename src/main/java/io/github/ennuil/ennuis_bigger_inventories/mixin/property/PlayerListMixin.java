package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.networking.payloads.ClientboundSyncTenfoursizedPacket;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
	@Shadow
	@Final
	private MinecraftServer server;

	@Inject(
		method = "placeNewPlayer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V",
			ordinal = 0
		)
	)
	private void sendSyncPacket(Connection connection, ServerPlayer player, CommonListenerCookie connectedClientData, CallbackInfo ci, @Local ServerGamePacketListenerImpl listenerImpl) {
		boolean tenfoursized = this.server.getWorldData().overworldData().isTenfoursized();
		listenerImpl.send(new ClientboundCustomPayloadPacket(new ClientboundSyncTenfoursizedPacket(tenfoursized)));
	}
}
