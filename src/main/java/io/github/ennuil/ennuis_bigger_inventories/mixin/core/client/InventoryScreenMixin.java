package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends EffectRenderingInventoryScreen<InventoryMenu> {
	@Unique private static final ResourceLocation EBI_TEXTURE = ModUtils.id("textures/gui/container/inventory.png");

	private InventoryScreenMixin(InventoryMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=97"))
	private int modify97(int original, Player player) {
		if (player.getInventory().isTenfoursized()) {
			return 115;
		}

		return original;
	}

	@ModifyExpressionValue(method = {"init", "method_19891"}, at = @At(value = "CONSTANT", args = "intValue=104"))
	private int modify104(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 122 : original;
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"
		)
	)
	private ResourceLocation modifyTexture(ResourceLocation original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			return EBI_TEXTURE;
		} else {
			return original;
		}
	}
}
