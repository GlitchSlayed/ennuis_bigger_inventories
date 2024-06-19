package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.SplitTextureFurnaceScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(AbstractFurnaceScreen.class)
public abstract class AbstractFurnaceScreenMixin<T extends AbstractFurnaceScreenHandler> extends HandledScreen<T> implements SplitTextureFurnaceScreen {
	@Unique
	private Identifier burnProgressTexture;

	@Unique
	private Identifier litProgressTexture;

	private AbstractFurnaceScreenMixin(T handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	public void ebi$setProgressTextures(Identifier burnProgressTexture, Identifier litProgressTexture) {
		this.burnProgressTexture = burnProgressTexture;
		this.litProgressTexture = litProgressTexture;
	}

	@ModifyExpressionValue(method = {"init", "method_19877"}, at = @At(value = "CONSTANT", args = "intValue=20", ordinal = 0))
	private int modify20s(int original) {
		return this.client.interactionManager.isTenfoursized() ? 29 : original;
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIII)V",
			ordinal = 0
		)
	)
	private void modifyLitProgress(GuiGraphics graphics, Identifier texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(this.litProgressTexture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 65, y, width, height);
		} else {
			original.call(graphics, texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIII)V",
			ordinal = 1
		)
	)
	private void modifyBurnProgress(GuiGraphics graphics, Identifier texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(this.burnProgressTexture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 88, y, width, height);
		} else {
			original.call(graphics, texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, width, height);
		}
	}
}
