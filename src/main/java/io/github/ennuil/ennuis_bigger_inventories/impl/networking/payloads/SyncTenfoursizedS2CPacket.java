package io.github.ennuil.ennuis_bigger_inventories.impl.networking.payloads;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import io.github.ennuil.ennuis_bigger_inventories.impl.networking.EnnyPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.payload.CustomPayload;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public record SyncTenfoursizedS2CPacket(boolean tenfoursized) implements CustomPayload {
	public static final PacketCodec<PacketByteBuf, SyncTenfoursizedS2CPacket> CODEC = CustomPayload.create(SyncTenfoursizedS2CPacket::write, SyncTenfoursizedS2CPacket::new);
	public static final CustomPayload.Id<SyncTenfoursizedS2CPacket> ID = new CustomPayload.Id<>(ModUtils.id("sync_tenfoursized_s2c"));

	public SyncTenfoursizedS2CPacket(PacketByteBuf byteBuf) {
		this(byteBuf.readBoolean());
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public void write(PacketByteBuf byteBuf) {
		byteBuf.writeBoolean(this.tenfoursized);
	}

	@ClientOnly
	public static void handle(SyncTenfoursizedS2CPacket payload, ClientPlayNetworking.Context context) {
		EnnyPackets.tenfoursized = payload.tenfoursized();
	}
}
