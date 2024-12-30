package io.github.ennuil.ennuis_bigger_inventories;

import io.github.ennuil.ennuis_bigger_inventories.impl.networking.EBIPackets;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.ModMenuTypes;
import net.fabricmc.api.ModInitializer;

public class ModInit implements ModInitializer {
	@Override
	public void onInitialize() {
		EBIPackets.register();
		ModMenuTypes.register();
	}
}
