package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.container;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ContainerScreen.class)
public abstract class ContainerScreenMixin extends AbstractContainerScreen<ChestMenu> {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/generic_9x6.png");

	private ContainerScreenMixin(ChestMenu menu, Inventory inventory, Component title, boolean dummy) {
		super(menu, inventory, title);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void modifyTitleX(ChestMenu menu, Inventory inventory, Component title, CallbackInfo ci) {
		if (inventory.isTenfoursized()) {
			this.titleLabelX += 9;
		}
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V"
		)
	)
	private Identifier modifyTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_TEXTURE : original;
	}
}
