package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Function;

@ClientOnly
@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ItemCombinerScreen<AnvilMenu> {
	@Unique private static final ResourceLocation EBI_TEXTURE = ModUtils.id("textures/gui/container/anvil.png");
	@Unique private static final ResourceLocation EBI_TEXT_FIELD_SPRITE = ModUtils.id("container/anvil/text_field");
	@Unique private static final ResourceLocation EBI_TEXT_FIELD_DISABLED_SPRITE = ModUtils.id("container/anvil/text_field_disabled");
	@Unique private static final ResourceLocation EBI_ERROR_SPRITE = ModUtils.id("container/anvil/error");

	private AnvilScreenMixin(AnvilMenu menu, Inventory inventory, Component title, ResourceLocation texture) {
		super(menu, inventory, title, texture);
	}

	@ModifyArg(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screens/inventory/ItemCombinerScreen;<init>(Lnet/minecraft/world/inventory/ItemCombinerMenu;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/ResourceLocation;)V"
		)
	)
	private static ResourceLocation modifyTextureOnInit(ResourceLocation original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? EBI_TEXTURE : original;
	}

	@WrapOperation(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"
		)
	)
	private void modifyTextFieldTexture(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			var textFieldTexture = this.menu.getSlot(0).hasItem() ? EBI_TEXT_FIELD_SPRITE : EBI_TEXT_FIELD_DISABLED_SPRITE;
			graphics.blitSprite(function, textFieldTexture, this.leftPos + 54, y, 128, height);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "renderErrorIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"
		)
	)
	private void modifyErrorTexture(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, EBI_ERROR_SPRITE, this.leftPos + 108, y, width, height);
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
