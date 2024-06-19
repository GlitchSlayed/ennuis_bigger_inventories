package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ForgingScreen<AnvilScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = ModUtils.id("textures/gui/container/anvil.png");

	@Unique private static final Identifier EBI_TEXT_FIELD_TEXTURE = ModUtils.id("container/anvil/text_field");
	@Unique private static final Identifier EBI_TEXT_FIELD_DISABLED_TEXTURE = ModUtils.id("container/anvil/text_field_disabled");
	@Unique private static final Identifier EBI_ERROR_TEXTURE = ModUtils.id("container/anvil/error");

	private AnvilScreenMixin(AnvilScreenHandler handler, PlayerInventory playerInventory, Text title, Identifier texture) {
		super(handler, playerInventory, title, texture);
	}

	@ModifyArg(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screen/ingame/ForgingScreen;<init>(Lnet/minecraft/screen/ForgingScreenHandler;Lnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/text/Text;Lnet/minecraft/util/Identifier;)V"
		),
		index = 3
	)
	private static Identifier modifyTextureOnInit(Identifier original, @Local(argsOnly = true) PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? BIGGER_TEXTURE : original;
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private void modifyTextFieldTexture(GuiGraphics graphics, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			var textFieldTexture = this.handler.getSlot(0).hasStack() ? EBI_TEXT_FIELD_TEXTURE : EBI_TEXT_FIELD_DISABLED_TEXTURE;
			graphics.drawGuiTexture(textFieldTexture, this.x + 54, y, 128, height);
		} else {
			original.call(graphics, texture, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private void modifyErrorTexture(GuiGraphics graphics, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_ERROR_TEXTURE, this.x + 108, y, width, height);
		} else {
			original.call(graphics, texture, x, y, width, height);
		}
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=60"))
	private int modify60(int original, AnvilScreenHandler handler, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 55 : original;
	}

	@ModifyExpressionValue(method = "setup", at = @At(value = "CONSTANT", args = "intValue=62"))
	private int modify62(int original) {
		return this.client.interactionManager.isTenfoursized() ? 57 : original;
	}

	@ModifyExpressionValue(method = "setup", at = @At(value = "CONSTANT", args = "intValue=103"))
	private int modify103(int original) {
		return this.client.interactionManager.isTenfoursized() ? 122 - 1 : original;
	}
}
