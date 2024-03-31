package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.inventory.SimpleInventory;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@ClientOnly
@Mixin(CreativeInventoryScreen.class)
public interface CreativeInventoryScreenAccessor {
    @Accessor("INVENTORY")
	@Final
	@Mutable
    static SimpleInventory getInventory() {
		throw new IllegalStateException("Mixin injection failed");
    }

	@Accessor("INVENTORY")
	@Final
	@Mutable
	static void setInventory(SimpleInventory inventory) {
		throw new IllegalStateException("Mixin injection failed");
	}
}
