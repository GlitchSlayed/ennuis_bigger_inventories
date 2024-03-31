package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HorseScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(HorseScreen.class)
public abstract class HorseScreenMixin extends HandledScreen<HorseScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = new Identifier("ennuis_bigger_inventories", "textures/gui/container/horse.png");

	@Unique private static final Identifier EBI_CHEST_SLOTS_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/horse/chest_slots");
	@Unique private static final Identifier EBI_ARMOR_SLOT_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/horse/armor_slot");
	@Unique private static final Identifier EBI_SADDLE_SLOT_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/horse/saddle_slot");
	@Unique private static final Identifier EBI_LLAMA_ARMOR_SLOT_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/horse/llama_armor_slot");

	private HorseScreenMixin(HorseScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 0
		),
		index = 0
	)
	private Identifier modifyTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized() ? BIGGER_TEXTURE : original;
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 1
		)
	)
	private void modifyChestSlotsTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_CHEST_SLOTS_TEXTURE, 90, 54, 0, 0, i + 90, y, width, height);
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
	private void modifySaddleSlotTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_SADDLE_SLOT_TEXTURE, i + 14, y, width, height);
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
	private void modifyLlamaArmorSlotTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_LLAMA_ARMOR_SLOT_TEXTURE, i + 14, y, width, height);
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
	private void modifyArmorSlotTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_ARMOR_SLOT_TEXTURE, i + 14, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;drawEntity(Lnet/minecraft/client/gui/GuiGraphics;IIIFFLnet/minecraft/entity/LivingEntity;)V"
		),
		index = 1
	)
	private int modify51(int original, @Local(ordinal = 2) int i) {
		return this.client.interactionManager.isTenfoursized() ? i + 60 : original;
	}
}
