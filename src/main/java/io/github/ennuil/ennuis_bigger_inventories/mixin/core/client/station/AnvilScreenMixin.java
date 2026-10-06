package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import net.minecraft.client.renderer.RenderPipelines;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ItemCombinerScreen<AnvilMenu> {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/anvil.png");
	@Unique private static final Identifier EBI_TEXT_FIELD_SPRITE = ModUtils.id("container/anvil/text_field");
	@Unique private static final Identifier EBI_TEXT_FIELD_DISABLED_SPRITE = ModUtils.id("container/anvil/text_field_disabled");
	@Unique private static final Identifier EBI_ERROR_SPRITE = ModUtils.id("container/anvil/error");

	private AnvilScreenMixin(AnvilMenu menu, Inventory inventory, Component title, Identifier texture) {
		super(menu, inventory, title, texture);
	}

	@ModifyArg(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screens/inventory/ItemCombinerScreen;<init>(Lnet/minecraft/world/inventory/ItemCombinerMenu;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/Identifier;)V"
		)
	)
	private static Identifier modifyTextureOnInit(Identifier original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? EBI_TEXTURE : original;
	}

	@WrapOperation(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"
		)
	)
	private void modifyTextFieldTexture(GuiGraphicsExtractor graphics, RenderPipeline function, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			var textFieldTexture = this.menu.getSlot(0).hasItem() ? EBI_TEXT_FIELD_SPRITE : EBI_TEXT_FIELD_DISABLED_SPRITE;
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, textFieldTexture, this.leftPos + 54, y, 128, height);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "extractErrorIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"
		)
	)
	private void modifyErrorTexture(GuiGraphicsExtractor graphics, RenderPipeline function, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EBI_ERROR_SPRITE, this.leftPos + 108, y, width, height);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=60"))
	private int modify60(int original, AnvilMenu menu, Inventory inventory) {
		return inventory.isTenfoursized() ? 55 : original;
	}

	@ModifyExpressionValue(method = "subInit", at = @At(value = "CONSTANT", args = "intValue=62"))
	private int modify62(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 57 : original;
	}

	@ModifyExpressionValue(method = "subInit", at = @At(value = "CONSTANT", args = "intValue=103"))
	private int modify103(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 122 - 1 : original;
	}
}
