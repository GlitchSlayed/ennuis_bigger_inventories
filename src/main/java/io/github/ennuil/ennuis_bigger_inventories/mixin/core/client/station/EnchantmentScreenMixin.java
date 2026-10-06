package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(EnchantmentScreen.class)
public abstract class EnchantmentScreenMixin extends AbstractContainerScreen<EnchantmentMenu> {
	@Unique private static final Identifier EBI_TEXTURE = ModUtils.id("textures/gui/container/enchanting_table.png");
	@Unique private static final Identifier EBI_ENCHANTMENT_SLOT_SPRITE = ModUtils.id("container/enchanting_table/enchantment_slot");
	@Unique private static final Identifier EBI_ENCHANTMENT_SLOT_DISABLED_SPRITE = ModUtils.id("container/enchanting_table/enchantment_slot_disabled");
	@Unique private static final Identifier EBI_ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE = ModUtils.id("container/enchanting_table/enchantment_slot_highlighted");
	@Unique private static final Identifier[] EBI_ENABLED_LEVEL_SPRITES = {
		ModUtils.id("container/enchanting_table/level_1"),
		ModUtils.id("container/enchanting_table/level_2"),
		ModUtils.id("container/enchanting_table/level_3")
	};
	@Unique private static final Identifier[] EBI_DISABLED_LEVEL_SPRITES = {
		ModUtils.id("container/enchanting_table/level_1_disabled"),
		ModUtils.id("container/enchanting_table/level_2_disabled"),
		ModUtils.id("container/enchanting_table/level_3_disabled")
	};

	private EnchantmentScreenMixin(EnchantmentMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V"
		)
	)
	private Identifier modifyTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_TEXTURE : original;
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyDisabledSlotTexture1(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_DISABLED_SPRITE : original;
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyDisabledSlotTexture2(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_DISABLED_SPRITE : original;
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 2
		)
	)
	private Identifier modifyDisabledLevelTexture(Identifier original, @Local(ordinal = 5) int l) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_DISABLED_LEVEL_SPRITES[l] : original;
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 3
		)
	)
	private Identifier modifyHighlightedSlotTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE : original;
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 4
		)
	)
	private Identifier modifySlotTexture(Identifier original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_SPRITE : original;
	}

	@ModifyArg(
		method = "extractBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
			ordinal = 5
		)
	)
	private Identifier modifyLevelTexture(Identifier original, @Local(ordinal = 5) int l) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ENABLED_LEVEL_SPRITES[l] : original;
	}

	@ModifyExpressionValue(method = "extractBackground", at = @At(value = "CONSTANT", args = "intValue=86"))
	private int modify86(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 104 : original;
	}

	@ModifyExpressionValue(method = {"extractBackground", "extractContents", "extractRenderState"}, at = @At(value = "CONSTANT", args = "intValue=108"))
	private int modify108(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 126 : original;
	}

	@ModifyExpressionValue(method = "mouseClicked", at = @At(value = "CONSTANT", args = "doubleValue=108.0"))
	private double modify108D(double original) {
		return this.minecraft.gameMode.isTenfoursized() ? 126.0 : original;
	}
}
