package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CrafterScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(CrafterScreen.class)
public abstract class CrafterScreenMixin {
	@Unique private static final ResourceLocation EBI_TEXTURE = ModUtils.id("textures/gui/container/crafter.png");
	@Unique private static final ResourceLocation EBI_DISABLED_SLOT_SPRITE = ModUtils.id("container/crafter/disabled_slot");
	@Unique private static final ResourceLocation EBI_POWERED_ARROW_SPRITE = ModUtils.id("container/crafter/powered_redstone");
	@Unique private static final ResourceLocation EBI_UNPOWERED_ARROW_SPRITE = ModUtils.id("container/crafter/unpowered_redstone");

	@Shadow
	@Final
	private Player player;

	@Shadow
	@Final
	private static ResourceLocation POWERED_REDSTONE_LOCATION_SPRITE;

	@ModifyArg(method = "renderBg", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"))
	private ResourceLocation modifyTexture(ResourceLocation original) {
		return this.player.getInventory().isTenfoursized() ? EBI_TEXTURE : original;
	}

	@ModifyArg(method = "renderDisabledSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
	private ResourceLocation modifyDisabledSlot(ResourceLocation original) {
		return this.player.getInventory().isTenfoursized() ? EBI_DISABLED_SLOT_SPRITE : original;
	}

	@WrapOperation(
		method = "renderRedstone",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 0
		)
	)
	private void modifyPoweredArrow(GuiGraphics instance, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original) {
		if (this.player.getInventory().isTenfoursized()) {
			instance.blitSprite(texture == POWERED_REDSTONE_LOCATION_SPRITE ? EBI_POWERED_ARROW_SPRITE : EBI_UNPOWERED_ARROW_SPRITE, x - 2, y, width, height);
		} else {
			original.call(instance, texture, x, y, width, height);
		}
	}
}
