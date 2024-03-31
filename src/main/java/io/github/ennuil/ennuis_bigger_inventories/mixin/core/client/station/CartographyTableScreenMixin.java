package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.CartographyTableScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CartographyTableScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(CartographyTableScreen.class)
public abstract class CartographyTableScreenMixin extends HandledScreen<CartographyTableScreenHandler> {
	@Unique
	private static final Identifier BIGGER_TEXTURE = new Identifier("ennuis_bigger_inventories", "textures/gui/container/cartography_table.png");

	@Unique private static final Identifier EBI_ERROR_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/cartography_table/error");
	@Unique private static final Identifier EBI_LOCKED_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/cartography_table/locked");
	@Unique private static final Identifier EBI_MAP_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/cartography_table/map");
	@Unique private static final Identifier EBI_DUPLICATED_MAP_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/cartography_table/duplicated_map");
	@Unique private static final Identifier EBI_SCALED_MAP_TEXTURE = new Identifier("ennuis_bigger_inventories", "container/cartography_table/scaled_map");

	private CartographyTableScreenMixin(CartographyTableScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@ModifyArg(
		method = {
			"drawBackground",
			"drawMap(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Integer;Lnet/minecraft/item/map/MapState;ZZZZ)V"
		},
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
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
	private void modifyErrorTexture1(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_ERROR_TEXTURE,  i + 44, y, width, height);
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
	private void modifyErrorTexture2(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 2) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_ERROR_TEXTURE,  i + 44, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawMap(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Integer;Lnet/minecraft/item/map/MapState;ZZZZ)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 0
		)
	)
	private void modifyScaledMapTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 0) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_SCALED_MAP_TEXTURE,  i + 76, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawMap(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Integer;Lnet/minecraft/item/map/MapState;ZZZZ)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 1
		)
	)
	private void modifyDuplicatedMapTexture1(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 0) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_DUPLICATED_MAP_TEXTURE,  i + 76 + 16, y, width, 50);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawMap(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Integer;Lnet/minecraft/item/map/MapState;ZZZZ)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 2
		)
	)
	private void modifyDuplicatedMapTexture2(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 0) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_DUPLICATED_MAP_TEXTURE,  i + 76, y, width, 50);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawMap(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Integer;Lnet/minecraft/item/map/MapState;ZZZZ)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 3
		)
	)
	private void modifyLockedMapTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 0) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_MAP_TEXTURE,  i + 76, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawMap(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Integer;Lnet/minecraft/item/map/MapState;ZZZZ)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 4
		)
	)
	private void modifyLockedLockTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 0) int i, @Local(ordinal = 1) int j) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_LOCKED_TEXTURE,  i + 75 + 52, j + 12 + 48, 10, 14);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "drawMap(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Integer;Lnet/minecraft/item/map/MapState;ZZZZ)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 5
		)
	)
	private void modifyMapTexture(GuiGraphics graphics, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(ordinal = 0) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			graphics.drawGuiTexture(EBI_MAP_TEXTURE,  i + 76, y, width, height);
		} else {
			original.call(graphics, texture, x, y, u, v, width, height);
		}
	}

	@ModifyExpressionValue(
		method = "drawMap(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Integer;Lnet/minecraft/item/map/MapState;ZZZZ)V",
		at = @At(value = "CONSTANT", args = "intValue=86")
	)
	private int modify86(int original) {
		return this.client.interactionManager.isTenfoursized() ? 95 : original;
	}

	@ModifyExpressionValue(
		method = "drawMap(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Integer;Lnet/minecraft/item/map/MapState;ZZZZ)V",
		at = @At(value = "CONSTANT", args = "intValue=85")
	)
	private int modify85(int original) {
		return this.client.interactionManager.isTenfoursized() ? 94 : original;
	}

	@ModifyExpressionValue(
		method = "drawMap(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Integer;Lnet/minecraft/item/map/MapState;ZZZZ)V",
		at = @At(value = "CONSTANT", args = "intValue=70")
	)
	private int modify70(int original) {
		return this.client.interactionManager.isTenfoursized() ? 79 : original;
	}

	@ModifyExpressionValue(
		method = "drawMap(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Integer;Lnet/minecraft/item/map/MapState;ZZZZ)V",
		at = @At(value = "CONSTANT", args = "intValue=71")
	)
	private int modify71(int original) {
		return this.client.interactionManager.isTenfoursized() ? 80 : original;
	}
}
