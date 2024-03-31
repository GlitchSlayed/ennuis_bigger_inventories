package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
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
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(EnchantmentScreen.class)
public abstract class EnchantmentScreenMixin extends HandledScreen<EnchantmentScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = new Identifier("ennuis_bigger_inventories", "textures/gui/container/enchanting_table.png");

	@Unique private static final Identifier EBI_EMPTY_SLOT_LAPIS_LAZULI_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/enchanting_table/empty_slot_lapis_lazuli");
	@Unique private static final Identifier EBI_ENCHANTMENT_SLOT_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/enchanting_table/enchantment_slot");
	@Unique private static final Identifier EBI_ENCHANTMENT_SLOT_DISABLED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/enchanting_table/enchantment_slot_disabled");
	@Unique private static final Identifier EBI_ENCHANTMENT_SLOT_HIGHLIGHTED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/enchanting_table/enchantment_slot_highlighted");
	@Unique private static final Identifier EBI_LEVEL_1_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/enchanting_table/level_1");
	@Unique private static final Identifier EBI_LEVEL_1_DISABLED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/enchanting_table/level_1_disabled");
	@Unique private static final Identifier EBI_LEVEL_2_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/enchanting_table/level_2");
	@Unique private static final Identifier EBI_LEVEL_2_DISABLED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/enchanting_table/level_2_disabled");
	@Unique private static final Identifier EBI_LEVEL_3_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/enchanting_table/level_3");
	@Unique private static final Identifier EBI_LEVEL_3_DISABLED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/enchanting_table/level_3_disabled");

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

	@Inject(
		method = "drawBackground",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/screen/EnchantmentScreenHandler;getLapisCount()I",
			shift = At.Shift.AFTER
		)
	)
	private void drawLapisSlot(GuiGraphics graphics, float delta, int mouseX, int mouseY, CallbackInfo ci, @Local(ordinal = 2) int i, @Local(ordinal = 3) int j, @Local(ordinal = 4) int k) {
		if (k == 0 && this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_EMPTY_SLOT_LAPIS_LAZULI_TEXTURE, i + 35, j + 47, 16, 16);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 1
		)
	)
	private void modifyDisabledSlotTexture1(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_ENCHANTMENT_SLOT_DISABLED_TEXTURE,  x, y, 126, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 2
		)
	)
	private void modifyDisabledSlotTexture2(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_ENCHANTMENT_SLOT_DISABLED_TEXTURE,  x, y, 126, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 3
		)
	)
	private void modifyDisabledLevelTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 5) int l) {
		if (this.client.interactionManager.isTenfoursized()) {
			var levelTexture = switch (l) {
				case 1 -> EBI_LEVEL_2_DISABLED_TEXTURE;
				case 2 -> EBI_LEVEL_3_DISABLED_TEXTURE;
                default -> EBI_LEVEL_1_DISABLED_TEXTURE;
            };
			graphics.drawGuiTexture(levelTexture,  x, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 4
		)
	)
	private void modifyHighlightedSlotTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_ENCHANTMENT_SLOT_HIGHLIGHTED_TEXTURE,  x, y, 126, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 5
		)
	)
	private void modifySlotTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_ENCHANTMENT_SLOT_TEXTURE,  x, y, 126, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 6
		)
	)
	private void modifyLevelTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 5) int l) {
		if (this.client.interactionManager.isTenfoursized()) {
			var levelTexture = switch (l) {
				case 1 -> EBI_LEVEL_2_TEXTURE;
				case 2 -> EBI_LEVEL_3_TEXTURE;
				default -> EBI_LEVEL_1_TEXTURE;
			};
			graphics.drawGuiTexture(levelTexture,  x, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
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
