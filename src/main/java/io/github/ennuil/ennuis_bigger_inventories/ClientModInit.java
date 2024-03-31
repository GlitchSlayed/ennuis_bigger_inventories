package io.github.ennuil.ennuis_bigger_inventories;

import io.github.ennuil.ennuis_bigger_inventories.impl.networking.EnnyPackets;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.GenericTensizedContainerScreen;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.ModScreenHandlerTypes;
import net.fabricmc.fabric.impl.client.itemgroup.FabricCreativeGuiComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;
import org.quiltmc.qsl.screen.api.client.ScreenEvents;

import java.util.Optional;

@ClientOnly
public class ClientModInit implements ClientModInitializer, ClientPlayConnectionEvents.Init, ClientPlayConnectionEvents.Disconnect, ScreenEvents.AfterInit {
	@Override
	public void onInitializeClient(ModContainer mod) {
		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X1, GenericTensizedContainerScreen::new);
		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X2, GenericTensizedContainerScreen::new);
		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X3, GenericTensizedContainerScreen::new);
		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X4, GenericTensizedContainerScreen::new);
		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X5, GenericTensizedContainerScreen::new);
		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X6, GenericTensizedContainerScreen::new);
	}

	@Override
	public void onPlayInit(ClientPlayNetworkHandler handler, MinecraftClient client) {
		EnnyPackets.registerReceivers(handler, client);
	}

	@Override
	public void onPlayDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
		// They may try to backstab the client, but unfortunately I'm a bit paranoid myself!
		EnnyPackets.tenfoursized = Optional.empty();
	}

	// (Quilted) Fabric API Compat Moment
	@Override
	public void afterInit(Screen screen, MinecraftClient client, boolean firstInit) {
		if (screen instanceof CreativeInventoryScreen && client.interactionManager.isTenfoursized()) {
			for (var element : screen.children()) {
				if (element instanceof FabricCreativeGuiComponents.ItemGroupButtonWidget button) {
					// 18 is the technical match; however, the button sucks on vanilla FAPI, and EBI is pretty opinionated
					// Let's see if this convinces them to improve their own button :p
					button.setX(button.getX() + 18 - 1);
				}
			}
		}
	}
}
