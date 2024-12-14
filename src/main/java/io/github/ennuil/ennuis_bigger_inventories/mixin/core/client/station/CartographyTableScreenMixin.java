package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CartographyTableScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CartographyTableMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
@Mixin(CartographyTableScreen.class)
public abstract class CartographyTableScreenMixin extends AbstractContainerScreen<CartographyTableMenu> {
	@Unique private static final ResourceLocation EBI_TEXTURE = ModUtils.id("textures/gui/container/cartography_table.png");
	@Unique private static final ResourceLocation EBI_ERROR_SPRITE = ModUtils.id("container/cartography_table/error");
	@Unique private static final ResourceLocation EBI_LOCKED_SPRITE = ModUtils.id("container/cartography_table/locked");
	@Unique private static final ResourceLocation EBI_MAP_SPRITE = ModUtils.id("container/cartography_table/map");
	@Unique private static final ResourceLocation EBI_DUPLICATED_MAP_SPRITE = ModUtils.id("container/cartography_table/duplicated_map");
	@Unique private static final ResourceLocation EBI_SCALED_MAP_SPRITE = ModUtils.id("container/cartography_table/scaled_map");

	private CartographyTableScreenMixin(CartographyTableMenu menu, Inventory inventory, Component title) {
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
	private ResourceLocation modifyErrorTexture1(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ERROR_SPRITE : original;
	}

	@ModifyArg(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 1
		)
	)
	private ResourceLocation modifyErrorTexture2(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_ERROR_SPRITE : original;
	}

	@ModifyArg(
		method = "renderResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 0
		)
	)
	private ResourceLocation modifyScaledMapTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_SCALED_MAP_SPRITE : original;
	}

	@WrapOperation(
		method = "renderResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 1
		)
	)
	private void modifyDuplicatedMapTexture1(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 0) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, EBI_DUPLICATED_MAP_SPRITE,  i + 76 + 16, y, width, 50);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "renderResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 2
		)
	)
	private void modifyDuplicatedMapTexture2(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 0) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			graphics.blitSprite(function, EBI_DUPLICATED_MAP_SPRITE,  i + 76, y, width, 50);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	@ModifyArg(
		method = "renderResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 3
		)
	)
	private ResourceLocation modifyLockedMapTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_MAP_SPRITE : original;
	}

	@ModifyArg(
		method = "renderResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 4
		)
	)
	private ResourceLocation modifyLockedLockTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_LOCKED_SPRITE : original;
	}

	@ModifyArg(
		method = "renderResultingMap",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 5
		)
	)
	private ResourceLocation modifyMapTexture(ResourceLocation original) {
		return this.minecraft.gameMode.isTenfoursized() ? EBI_MAP_SPRITE : original;
	}

	@ModifyExpressionValue(method = "renderBg", at = @At(value = "CONSTANT", args = "intValue=35"))
	private int modify35(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 44 : original;
	}

	@ModifyExpressionValue(method = "renderResultingMap", at = @At(value = "CONSTANT", args = "intValue=67"))
	private int modify67(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 76 : original;
	}

	@ModifyExpressionValue(method = "renderResultingMap", at = @At(value = "CONSTANT", args = "intValue=86"))
	private int modify86(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 95 : original;
	}

	@ModifyExpressionValue(method = "renderResultingMap", at = @At(value = "CONSTANT", args = "intValue=85"))
	private int modify85(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 94 : original;
	}

	@ModifyExpressionValue(method = "renderResultingMap", at = @At(value = "CONSTANT", args = "intValue=70"))
	private int modify70(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 79 : original;
	}

	@ModifyExpressionValue(method = "renderResultingMap", at = @At(value = "CONSTANT", args = "intValue=71"))
	private int modify71(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 80 : original;
	}

	@ModifyExpressionValue(method = "renderResultingMap", at = @At(value = "CONSTANT", args = "intValue=118"))
	private int modify118(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 127 : original;
	}
}
