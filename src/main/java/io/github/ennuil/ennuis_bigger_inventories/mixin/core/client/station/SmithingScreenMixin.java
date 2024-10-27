package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.SmithingMenu;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// TODO - The smithing table screen needs a redesign
@ClientOnly
@Mixin(SmithingScreen.class)
public abstract class SmithingScreenMixin extends ItemCombinerScreen<SmithingMenu> {
	@Unique private static final ResourceLocation EBI_TEXTURE = ModUtils.id("textures/gui/container/smithing_table.png");
	@Unique private static final ResourceLocation EBI_ERROR_SPRITE = ModUtils.id("container/smithing_table/error");

	private SmithingScreenMixin(SmithingMenu menu, Inventory inventory, Component title, ResourceLocation texture) {
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

	@ModifyArg(
		method = "renderErrorIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"
		)
	)
	private ResourceLocation modifyErrorTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ERROR_SPRITE : original;
	}

	@ModifyExpressionValue(method = {"renderErrorIcon", "renderOnboardingTooltips"}, at = @At(value = "CONSTANT", args = "intValue=65"))
	private int modify65(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 74 : original;
	}

	@ModifyExpressionValue(method = {"renderErrorIcon", "renderOnboardingTooltips"}, at = @At(value = "CONSTANT", args = "intValue=46"))
	private int modify46(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 38 : original;
	}

	@ModifyExpressionValue(method = "renderBg", at = @At(value = "CONSTANT", args = "intValue=141"))
	private int modify141(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 164 : original;
	}

	@ModifyExpressionValue(method = "renderBg", at = @At(value = "CONSTANT", args = "intValue=75"))
	private int modify75(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 69 : original;
	}

	// Title coords
	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=44"))
	private int modify44(int original, SmithingMenu menu, Inventory inventory) {
		return inventory.isTenfoursized() ? 38 : original;
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=15"))
	private int modify15(int original, SmithingMenu menu, Inventory inventory) {
		return inventory.isTenfoursized() ? 12 : original;
	}
}
