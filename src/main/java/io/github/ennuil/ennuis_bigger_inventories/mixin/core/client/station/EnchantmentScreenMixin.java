package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(EnchantmentScreen.class)
public abstract class EnchantmentScreenMixin extends AbstractContainerScreen<EnchantmentMenu> {
	@Unique private static final ResourceLocation EBI_TEXTURE = ModUtils.id("textures/gui/container/enchanting_table.png");
	@Unique private static final ResourceLocation EBI_ENCHANTMENT_SLOT_SPRITE = ModUtils.id("container/enchanting_table/enchantment_slot");
	@Unique private static final ResourceLocation EBI_ENCHANTMENT_SLOT_DISABLED_SPRITE = ModUtils.id("container/enchanting_table/enchantment_slot_disabled");
	@Unique private static final ResourceLocation EBI_ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE = ModUtils.id("container/enchanting_table/enchantment_slot_highlighted");
	@Unique private static final ResourceLocation[] EBI_ENABLED_LEVEL_SPRITES = {
		ModUtils.id("container/enchanting_table/level_1"),
		ModUtils.id("container/enchanting_table/level_2"),
		ModUtils.id("container/enchanting_table/level_3")
	};
	@Unique private static final ResourceLocation[] EBI_DISABLED_LEVEL_SPRITES = {
		ModUtils.id("container/enchanting_table/level_1_disabled"),
		ModUtils.id("container/enchanting_table/level_2_disabled"),
		ModUtils.id("container/enchanting_table/level_3_disabled")
	};

	private EnchantmentScreenMixin(EnchantmentMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"
		)
	)
	private ResourceLocation modifyTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_TEXTURE : original;
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 0
		)
	)
	private ResourceLocation modifyDisabledSlotTexture1(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_DISABLED_SPRITE : original;
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 1
		)
	)
	private ResourceLocation modifyDisabledSlotTexture2(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_DISABLED_SPRITE : original;
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 2
		)
	)
	private ResourceLocation modifyDisabledLevelTexture(ResourceLocation original, @Local(ordinal = 5) int l) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_DISABLED_LEVEL_SPRITES[l] : original;
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 3
		)
	)
	private ResourceLocation modifyHighlightedSlotTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE : original;
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 4
		)
	)
	private ResourceLocation modifySlotTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_SPRITE : original;
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 5
		)
	)
	private ResourceLocation modifyLevelTexture(ResourceLocation original, @Local(ordinal = 5) int l) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ENABLED_LEVEL_SPRITES[l] : original;
	}

	@ModifyExpressionValue(method = "renderBg", at = @At(value = "CONSTANT", args = "intValue=86"))
	private int modify86(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 104 : original;
	}

	@ModifyExpressionValue(method = {"renderBg", "render"}, at = @At(value = "CONSTANT", args = "intValue=108"))
	private int modify108(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 126 : original;
	}

	@ModifyExpressionValue(method = "mouseClicked", at = @At(value = "CONSTANT", args = "doubleValue=108.0"))
	private double modify108D(double original) {
		return this.minecraft.gameMode.isTenfoursized() ? 126.0 : original;
	}
}
