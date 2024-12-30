package io.github.ennuil.ennuis_bigger_inventories.impl.networking;

import io.github.ennuil.ennuis_bigger_inventories.impl.networking.payloads.ClientboundSyncTenfoursizedPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class EBIPackets {
	public static Boolean tenfoursized = null;

	public static void register() {
		PayloadTypeRegistry.playS2C().register(ClientboundSyncTenfoursizedPacket.ID, ClientboundSyncTenfoursizedPacket.CODEC);
	}

	@Environment(EnvType.CLIENT)
	public static void registerClient() {
		ClientPlayNetworking.registerGlobalReceiver(ClientboundSyncTenfoursizedPacket.ID, ClientboundSyncTenfoursizedPacket::handle);
	}
}
