package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HorseInventoryMenu;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Function;

@ClientOnly
@Mixin(HorseInventoryScreen.class)
public abstract class HorseInventoryScreenMixin extends AbstractContainerScreen<HorseInventoryMenu> {
	@Unique private static final ResourceLocation EBI_TEXTURE = ModUtils.id("textures/gui/container/horse.png");
	@Unique private static final ResourceLocation EBI_CHEST_SLOTS_SPRITE = ModUtils.id("container/horse/chest_slots");
	@Unique private static final ResourceLocation EBI_ARMOR_SLOT_SPRITE = ModUtils.id("container/horse/armor_slot");
	@Unique private static final ResourceLocation EBI_SADDLE_SLOT_SPRITE = ModUtils.id("container/horse/saddle_slot");
	@Unique private static final ResourceLocation EBI_LLAMA_ARMOR_SLOT_SPRITE = ModUtils.id("container/horse/llama_armor_slot");

	private HorseInventoryScreenMixin(HorseInventoryMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V",
			ordinal = 0
		)
	)
	private ResourceLocation modifyTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_TEXTURE : original;
	}

	@WrapOperation(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V"
		)
	)
	private void modifyChestSlotsTexture(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, EBI_CHEST_SLOTS_SPRITE, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, i + 90, y, width, height);
		} else {
			original.call(graphics, function, texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 0
		)
	)
	private void modifySaddleSlotTexture(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, EBI_SADDLE_SLOT_SPRITE, i + 14, y, width, height);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 1
		)
	)
	private void modifyLlamaArmorSlotTexture(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, EBI_LLAMA_ARMOR_SLOT_SPRITE, i + 14, y, width, height);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 2
		)
	)
	private void modifyArmorSlotTexture(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, EBI_ARMOR_SLOT_SPRITE, i + 14, y, width, height);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphics;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"
		),
		index = 1
	)
	private int modify26(int original, @Local(ordinal = 2) int i) {
		return this.minecraft.gameMode.isTenfoursized() ? i + 35 : original;
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphics;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"
		),
		index = 3
	)
	private int modify78(int original, @Local(ordinal = 2) int i) {
		return this.minecraft.gameMode.isTenfoursized() ? i + 87 : original;
	}
}
