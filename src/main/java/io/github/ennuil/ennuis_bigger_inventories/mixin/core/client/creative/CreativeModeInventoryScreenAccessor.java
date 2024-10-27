package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.SimpleContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@ClientOnly
@Mixin(CreativeModeInventoryScreen.class)
public interface CreativeModeInventoryScreenAccessor {
    @Accessor("CONTAINER")
	@Final
	@Mutable
    static SimpleContainer getContainer() {
		throw new IllegalStateException("Mixin injection failed");
    }

	@Accessor("CONTAINER")
	@Final
	@Mutable
	static void setContainer(SimpleContainer container) {
		throw new IllegalStateException("Mixin injection failed");
	}
}
