package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import net.minecraft.client.renderer.RenderPipelines;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CrafterScreen;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
@Mixin(CrafterScreen.class)
public abstract class CrafterScreenMixin {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/crafter.png");
	@Unique private static final Identifier EBI_DISABLED_SLOT_SPRITE = ModUtils.id("container/crafter/disabled_slot");
	@Unique private static final Identifier EBI_POWERED_ARROW_SPRITE = ModUtils.id("container/crafter/powered_redstone");
	@Unique private static final Identifier EBI_UNPOWERED_ARROW_SPRITE = ModUtils.id("container/crafter/unpowered_redstone");

	@Shadow
	@Final
	private Player player;

	@Shadow
	@Final
	private static Identifier POWERED_REDSTONE_LOCATION_SPRITE;

	@ModifyArg(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V"))
	private Identifier modifyTexture(Identifier original) {
		return this.player.getInventory().isTenfoursized() ? EBI_TEXTURE : original;
	}

	@ModifyArg(method = "extractDisabledSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"))
	private Identifier modifyDisabledSlot(Identifier original) {
		return this.player.getInventory().isTenfoursized() ? EBI_DISABLED_SLOT_SPRITE : original;
	}

	@WrapOperation(
		method = "extractRedstone",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private void modifyPoweredArrow(GuiGraphicsExtractor instance, RenderPipeline function, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
		if (this.player.getInventory().isTenfoursized()) {
			instance.blitSprite(RenderPipelines.GUI_TEXTURED, texture == POWERED_REDSTONE_LOCATION_SPRITE ? EBI_POWERED_ARROW_SPRITE : EBI_UNPOWERED_ARROW_SPRITE, x - 2, y, width, height);
		} else {
			original.call(instance, function, texture, x, y, width, height);
		}
	}
}
