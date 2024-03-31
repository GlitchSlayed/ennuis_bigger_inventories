package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.container;

import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(Generic3x3ContainerScreen.class)
public abstract class Generic3x3ContainerScreenMixin extends HandledScreen<Generic3x3ContainerScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = new Identifier("ennuis_bigger_inventories", "textures/gui/container/generic_3x3.png");

	private Generic3x3ContainerScreenMixin(Generic3x3ContainerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
		),
		index = 0
	)
	private Identifier modifyTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? BIGGER_TEXTURE : original;
	}
}
