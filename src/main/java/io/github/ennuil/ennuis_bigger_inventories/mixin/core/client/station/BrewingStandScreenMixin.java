package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.BrewingStandScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(BrewingStandScreen.class)
public abstract class BrewingStandScreenMixin extends HandledScreen<BrewingStandScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = new Identifier("ennuis_bigger_inventories", "textures/gui/container/brewing_stand.png");

	@Unique private static final Identifier EBI_BREW_PROGRESS_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/brewing_stand/brew_progress");
	@Unique private static final Identifier EBI_BUBBLES_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/brewing_stand/bubbles");
	@Unique private static final Identifier EBI_FUEL_LENGTH_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/brewing_stand/fuel_length");

	private BrewingStandScreenMixin(BrewingStandScreenHandler handler, PlayerInventory inventory, Text title) {
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

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 1
		)
	)
	private void modifyFuelLengthTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_FUEL_LENGTH_TEXTURE, 18, height, 0, 0, i + 70, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 2
		)
	)
	private void modifyBrewProgressTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_BREW_PROGRESS_TEXTURE, width, 28, 0, 0, i + 107, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 3
		)
	)
	private void modifyBubblesTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_BUBBLES_TEXTURE, width, 29, 0, 29 - height, i + 73, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}
}
