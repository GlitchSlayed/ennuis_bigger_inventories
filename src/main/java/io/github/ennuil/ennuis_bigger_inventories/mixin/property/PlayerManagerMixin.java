package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import io.github.ennuil.ennuis_bigger_inventories.impl.networking.EnnyPackets;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.PacketBundleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
	@Shadow
	@Final
	private MinecraftServer server;

	// This is fine, Quilt squashes down packet bundles
	@ModifyArg(
		method = "onPlayerConnect",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V",
			ordinal = 0
		)
	)
	private Packet<ClientPlayPacketListener> sendSyncPacket(Packet<ClientPlayPacketListener> packet) {
		boolean tenfoursized = this.server.getSaveProperties().getMainWorldProperties().isTenfoursized();
		var buf = PacketByteBufs.create();
		buf.writeBoolean(tenfoursized);
		return new PacketBundleS2CPacket(List.of(
			ServerPlayNetworking.createS2CPacket(EnnyPackets.SYNC_TENFOURSIZED_S2C_PACKET_ID, buf),
			packet
		));
	}
}
