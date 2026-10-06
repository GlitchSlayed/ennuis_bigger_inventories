package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.GrindstoneScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.GrindstoneMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(GrindstoneScreen.class)
public abstract class GrindstoneScreenMixin extends AbstractContainerScreen<GrindstoneMenu> {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/grindstone.png");
	@Unique private static final Identifier EBI_ERROR_SPRITE = ModUtils.id("container/grindstone/error");

	public GrindstoneScreenMixin(GrindstoneMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
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

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"
		)
	)
	private Identifier modifyErrorTexture(Identifier original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			return EBI_ERROR_SPRITE;
		} else {
			return original;
		}
	}

	@ModifyExpressionValue(method = "extractBackground", at = @At(value = "CONSTANT", args = "intValue=92"))
	private int modify92(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 101 : original;
	}
}
