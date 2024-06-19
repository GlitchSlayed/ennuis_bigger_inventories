package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.CrafterScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CrafterScreen.class)
public abstract class CrafterScreenMixin {
	@Unique
	private static final Identifier BIGGER_TEXTURE = ModUtils.id("textures/gui/container/crafter.png");

	@Unique private static final Identifier EBI_DISABLED_SLOT_TEXTURE = ModUtils.id("container/crafter/disabled_slot");
	@Unique private static final Identifier EBI_POWERED_ARROW_TEXTURE = ModUtils.id("container/crafter/powered_redstone");
	@Unique private static final Identifier EBI_UNPOWERED_ARROW_TEXTURE = ModUtils.id("container/crafter/unpowered_redstone");

	@Shadow
	@Final
	private PlayerEntity player;

	@Shadow
	@Final
	private static Identifier POWERED_ARROW_TEXTURE;

	@ModifyArg(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
	private Identifier modifyTexture(Identifier original) {
		return this.player.getInventory().isTenfoursized() ? BIGGER_TEXTURE : original;
	}

	@ModifyArg(method = "drawDisabledSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"))
	private Identifier modifyDisabledSlot(Identifier original) {
		return this.player.getInventory().isTenfoursized() ? EBI_DISABLED_SLOT_TEXTURE : original;
	}

	@WrapOperation(
		method = "drawArrowTexture",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private void modifyPoweredArrow(GuiGraphics instance, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
		if (this.player.getInventory().isTenfoursized()) {
			instance.drawGuiTexture(texture == POWERED_ARROW_TEXTURE ? EBI_POWERED_ARROW_TEXTURE : EBI_UNPOWERED_ARROW_TEXTURE, x - 2, y, width, height);
		} else {
			original.call(instance, texture, x, y, width, height);
		}
	}
}
