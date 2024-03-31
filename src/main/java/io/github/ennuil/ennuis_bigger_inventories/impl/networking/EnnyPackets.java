package io.github.ennuil.ennuis_bigger_inventories.impl.networking;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.Optional;

public class EnnyPackets {
	public static final Identifier SYNC_TENFOURSIZED_S2C_PACKET_ID = new Identifier("ennuis_bigger_inventories", "sync_tenfoursized_s2c");

	public static Optional<Boolean> tenfoursized = Optional.empty();

	public static void registerReceivers(ClientPlayNetworkHandler handler, MinecraftClient client) {
		ClientPlayNetworking.registerReceiver(SYNC_TENFOURSIZED_S2C_PACKET_ID, (client1, handler1, buf, responseSender) -> {
            EnnyPackets.tenfoursized = Optional.of(buf.readBoolean());
		});
	}
}
