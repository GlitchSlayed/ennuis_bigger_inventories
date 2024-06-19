package io.github.ennuil.ennuis_bigger_inventories.impl.networking;

import io.github.ennuil.ennuis_bigger_inventories.impl.networking.payloads.SyncTenfoursizedS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class EnnyPackets {
	public static Boolean tenfoursized = null;

	public static void register() {
		PayloadTypeRegistry.playS2C().register(SyncTenfoursizedS2CPacket.ID, SyncTenfoursizedS2CPacket.CODEC);
	}

	@ClientOnly
	public static void registerClient() {
		ClientPlayNetworking.registerGlobalReceiver(SyncTenfoursizedS2CPacket.ID, SyncTenfoursizedS2CPacket::handle);
	}
}
