package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.container;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.HopperScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HopperMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(HopperScreen.class)
public abstract class HopperScreenMixin extends AbstractContainerScreen<HopperMenu> {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/hopper.png");

	private HopperScreenMixin(HopperMenu menu, Inventory inventory, Component title, boolean dummy) {
		super(menu, inventory, title);
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=176"))
	private static int modifyBackgroundWidth(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 194 : original;
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
