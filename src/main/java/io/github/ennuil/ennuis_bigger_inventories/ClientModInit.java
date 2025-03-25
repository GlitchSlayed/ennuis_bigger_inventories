package io.github.ennuil.ennuis_bigger_inventories;

import io.github.ennuil.ennuis_bigger_inventories.impl.networking.EBIPackets;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.ModMenuTypes;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.TenfoursizedContainerScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.impl.client.itemgroup.FabricCreativeGuiComponents;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;

@Environment(EnvType.CLIENT)
public class ClientModInit implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EBIPackets.registerClient();

		MenuScreens.register(ModMenuTypes.GENERIC_10X1, TenfoursizedContainerScreen::new);
		MenuScreens.register(ModMenuTypes.GENERIC_10X2, TenfoursizedContainerScreen::new);
		MenuScreens.register(ModMenuTypes.GENERIC_10X3, TenfoursizedContainerScreen::new);
		MenuScreens.register(ModMenuTypes.GENERIC_10X4, TenfoursizedContainerScreen::new);
		MenuScreens.register(ModMenuTypes.GENERIC_10X5, TenfoursizedContainerScreen::new);
		MenuScreens.register(ModMenuTypes.GENERIC_10X6, TenfoursizedContainerScreen::new);

		ClientPlayConnectionEvents.DISCONNECT.register((menu, client) -> EBIPackets.tenfoursized = null);

		ScreenEvents.AFTER_INIT.register(((client, screen, scaledWidth, scaledHeight) -> {
			if (screen instanceof CreativeModeInventoryScreen && client.gameMode.isTenfoursized()) {
				for (var element : screen.children()) {
					if (element instanceof FabricCreativeGuiComponents.ItemGroupButtonWidget button) {
						// We don't want to mixin a mixin using a hacky library, so use the Fabric Screen API instead!
						button.setX(button.getX() + 18);
					}
				}
			}
		}));
	}
}
