package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.gui.screen.ingame.SmithingScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// TODO - The smithing table screen needs a redesign
@ClientOnly
@Mixin(SmithingScreen.class)
public abstract class SmithingScreenMixin extends ForgingScreen<SmithingScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = ModUtils.id("textures/gui/container/smithing_table.png");

	@Unique private static final Identifier EBI_ERROR_TEXTURE = ModUtils.id("container/smithing_table/error");

	private SmithingScreenMixin(SmithingScreenHandler handler, PlayerInventory playerInventory, Text title, Identifier texture) {
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

	@ModifyArg(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private Identifier modifyErrorTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_ERROR_TEXTURE : original;
	}

	@ModifyExpressionValue(method = {"renderIcon", "renderTooltips"}, at = @At(value = "CONSTANT", args = "intValue=65"))
	private int modify65(int original) {
		return this.client.interactionManager.isTenfoursized() ? 74 : original;
	}

	@ModifyExpressionValue(method = {"renderIcon", "renderTooltips"}, at = @At(value = "CONSTANT", args = "intValue=46"))
	private int modify46(int original) {
		return this.client.interactionManager.isTenfoursized() ? 38 : original;
	}

	@ModifyExpressionValue(method = "drawBackground", at = @At(value = "CONSTANT", args = "intValue=141"))
	private int modify141(int original) {
		return this.client.interactionManager.isTenfoursized() ? 164 : original;
	}

	@ModifyExpressionValue(method = "drawBackground", at = @At(value = "CONSTANT", args = "intValue=75"))
	private int modify75(int original) {
		return this.client.interactionManager.isTenfoursized() ? 69 : original;
	}

	// Title coords
	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=44"))
	private int modify44(int original, SmithingScreenHandler handler, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 38 : original;
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=15"))
	private int modify15(int original, SmithingScreenHandler handler, PlayerInventory playerInventory) {
		return playerInventory.isTenfoursized() ? 12 : original;
	}
}
