package io.github.ennuil.ennuis_bigger_inventories.impl.networking;

import io.github.ennuil.ennuis_bigger_inventories.impl.networking.payloads.ClientboundSyncTenfoursizedPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class EnnyPackets {
	public static Boolean tenfoursized = null;

	public static void register() {
		PayloadTypeRegistry.playS2C().register(ClientboundSyncTenfoursizedPacket.ID, ClientboundSyncTenfoursizedPacket.CODEC);
	}

	@ClientOnly
	public static void registerClient() {
		ClientPlayNetworking.registerGlobalReceiver(ClientboundSyncTenfoursizedPacket.ID, ClientboundSyncTenfoursizedPacket::handle);
	}
}
