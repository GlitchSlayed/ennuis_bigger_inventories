package io.github.ennuil.ennuis_bigger_inventories.impl.networking.payloads;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import io.github.ennuil.ennuis_bigger_inventories.impl.networking.EBIPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ClientboundSyncTenfoursizedPacket(boolean tenfoursized) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, ClientboundSyncTenfoursizedPacket> CODEC = CustomPacketPayload.codec(ClientboundSyncTenfoursizedPacket::write, ClientboundSyncTenfoursizedPacket::new);
	public static final CustomPacketPayload.Type<ClientboundSyncTenfoursizedPacket> ID = new CustomPacketPayload.Type<>(ModUtils.id("sync_tenfoursized_s2c"));

	public ClientboundSyncTenfoursizedPacket(FriendlyByteBuf byteBuf) {
		this(byteBuf.readBoolean());
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}

	public void write(FriendlyByteBuf byteBuf) {
		byteBuf.writeBoolean(this.tenfoursized);
	}

	@Environment(EnvType.CLIENT)
	public static void handle(ClientboundSyncTenfoursizedPacket payload, ClientPlayNetworking.Context context) {
		EBIPackets.tenfoursized = payload.tenfoursized();
	}
}
