package io.github.ennuil.ennuis_bigger_inventories;

import io.github.ennuil.ennuis_bigger_inventories.impl.networking.EnnyPackets;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.TenfoursizedContainerScreen;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.impl.client.itemgroup.FabricCreativeGuiComponents;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class ClientModInit implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EnnyPackets.registerClient();

		MenuScreens.register(ModMenuTypes.GENERIC_10X1, TenfoursizedContainerScreen::new);
		MenuScreens.register(ModMenuTypes.GENERIC_10X2, TenfoursizedContainerScreen::new);
		MenuScreens.register(ModMenuTypes.GENERIC_10X3, TenfoursizedContainerScreen::new);
		MenuScreens.register(ModMenuTypes.GENERIC_10X4, TenfoursizedContainerScreen::new);
		MenuScreens.register(ModMenuTypes.GENERIC_10X5, TenfoursizedContainerScreen::new);
		MenuScreens.register(ModMenuTypes.GENERIC_10X6, TenfoursizedContainerScreen::new);

		ClientPlayConnectionEvents.DISCONNECT.register((menu, client) -> EnnyPackets.tenfoursized = null);

		ScreenEvents.AFTER_INIT.register(((client, screen, scaledWidth, scaledHeight) -> {
			if (screen instanceof CreativeModeInventoryScreen && client.gameMode.isTenfoursized()) {
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
