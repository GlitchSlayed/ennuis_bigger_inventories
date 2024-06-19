package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(EnchantmentScreen.class)
public abstract class EnchantmentScreenMixin extends HandledScreen<EnchantmentScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = ModUtils.id("textures/gui/container/enchanting_table.png");

	@Unique private static final Identifier EBI_ENCHANTMENT_SLOT_TEXTURE = ModUtils.id("container/enchanting_table/enchantment_slot");
	@Unique private static final Identifier EBI_ENCHANTMENT_SLOT_DISABLED_TEXTURE = ModUtils.id("container/enchanting_table/enchantment_slot_disabled");
	@Unique private static final Identifier EBI_ENCHANTMENT_SLOT_HIGHLIGHTED_TEXTURE = ModUtils.id("container/enchanting_table/enchantment_slot_highlighted");
	@Unique private static final Identifier[] EBI_LEVEL_TEXTURES = {
		ModUtils.id("container/enchanting_table/level_1"),
		ModUtils.id("container/enchanting_table/level_2"),
		ModUtils.id("container/enchanting_table/level_3")
	};
	@Unique private static final Identifier[] EBI_LEVEL_DISABLED_TEXTURES = {
		ModUtils.id("container/enchanting_table/level_1_disabled"),
		ModUtils.id("container/enchanting_table/level_2_disabled"),
		ModUtils.id("container/enchanting_table/level_3_disabled")
	};

	private EnchantmentScreenMixin(EnchantmentScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
		),
		index = 0
	)
	private Identifier modifyTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? BIGGER_TEXTURE : original;
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyDisabledSlotTexture1(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_DISABLED_TEXTURE : original;
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 1
		)
	)
	private Identifier modifyDisabledSlotTexture2(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_DISABLED_TEXTURE : original;
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 2
		)
	)
	private Identifier modifyDisabledLevelTexture(Identifier original, @Local(ordinal = 5) int l) {
		return this.client.interactionManager.isTenfoursized() ? EBI_LEVEL_DISABLED_TEXTURES[l] : original;
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 3
		)
	)
	private Identifier modifyHighlightedSlotTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_HIGHLIGHTED_TEXTURE : original;
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 4
		)
	)
	private Identifier modifySlotTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? EBI_ENCHANTMENT_SLOT_TEXTURE : original;
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 5
		)
	)
	private Identifier modifyLevelTexture(Identifier original, @Local(ordinal = 5) int l) {
		return this.client.interactionManager.isTenfoursized() ? EBI_LEVEL_TEXTURES[l] : original;
	}

	@ModifyExpressionValue(method = "drawBackground", at = @At(value = "CONSTANT", args = "intValue=86"))
	private int modify86(int original) {
		return this.client.interactionManager.isTenfoursized() ? 104 : original;
	}

	@ModifyExpressionValue(method = {"drawBackground", "render"}, at = @At(value = "CONSTANT", args = "intValue=108"))
	private int modify108(int original) {
		return this.client.interactionManager.isTenfoursized() ? 126 : original;
	}

	@ModifyExpressionValue(method = "mouseClicked", at = @At(value = "CONSTANT", args = "doubleValue=108.0"))
	private double modify108D(double original) {
		return this.client.interactionManager.isTenfoursized() ? 126.0 : original;
	}
}
