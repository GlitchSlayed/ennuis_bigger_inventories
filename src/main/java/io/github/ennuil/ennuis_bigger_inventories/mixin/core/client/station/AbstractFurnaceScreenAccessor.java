package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractFurnaceScreen.class)
public interface AbstractFurnaceScreenAccessor {
	@Accessor
	@Mutable
	void setBackground(Identifier background);
}
