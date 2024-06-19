package io.github.ennuil.ennuis_bigger_inventories;

import io.github.ennuil.ennuis_bigger_inventories.impl.networking.EnnyPackets;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.GenericTensizedContainerScreen;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.ModScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.impl.client.itemgroup.FabricCreativeGuiComponents;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class ClientModInit implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EnnyPackets.registerClient();

		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X1, GenericTensizedContainerScreen::new);
		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X2, GenericTensizedContainerScreen::new);
		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X3, GenericTensizedContainerScreen::new);
		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X4, GenericTensizedContainerScreen::new);
		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X5, GenericTensizedContainerScreen::new);
		HandledScreens.register(ModScreenHandlerTypes.GENERIC_10X6, GenericTensizedContainerScreen::new);

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> EnnyPackets.tenfoursized = null);

		ScreenEvents.AFTER_INIT.register(((client, screen, scaledWidth, scaledHeight) -> {
			if (screen instanceof CreativeInventoryScreen && client.interactionManager.isTenfoursized()) {
				for (var element : screen.children()) {
					if (element instanceof FabricCreativeGuiComponents.ItemGroupButtonWidget button) {
						// 18 is the technical match; however, the button sucks on vanilla FAPI, and EBI is pretty opinionated
						// Let's see if this convinces them to improve their own button :p
						button.setX(button.getX() + 18 - 1);
					}
				}
			}
		}));
	}
}
