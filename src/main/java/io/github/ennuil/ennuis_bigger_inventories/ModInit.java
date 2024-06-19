package io.github.ennuil.ennuis_bigger_inventories;

import io.github.ennuil.ennuis_bigger_inventories.impl.networking.EnnyPackets;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.ModScreenHandlerTypes;
import net.fabricmc.api.ModInitializer;

public class ModInit implements ModInitializer {
	@Override
	public void onInitialize() {
		EnnyPackets.register();
		ModScreenHandlerTypes.register();
	}
}
