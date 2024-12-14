package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Collection;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends AbstractContainerScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
	@Unique private static final ResourceLocation EBI_SCROLLER_SPRITE = ModUtils.id("container/creative_inventory/scroller");
	@Unique private static final ResourceLocation EBI_SCROLLER_DISABLED_SPRITE = ModUtils.id("container/creative_inventory/scroller_disabled");

	@Unique
	private boolean shiftSavedToolbars = false;

	@Unique
	private static final ResourceLocation[] EBI_SELECTED_TOP_TABS = {
		ModUtils.id("container/creative_inventory/tab_top_selected_1"),
		ModUtils.id("container/creative_inventory/tab_top_selected_2"),
		ModUtils.id("container/creative_inventory/tab_top_selected_3"),
		ModUtils.id("container/creative_inventory/tab_top_selected_4"),
		ModUtils.id("container/creative_inventory/tab_top_selected_5"),
		ModUtils.id("container/creative_inventory/tab_top_selected_6"),
		ModUtils.id("container/creative_inventory/tab_top_selected_7"),
	};

	@Unique
	private static final ResourceLocation[] EBI_UNSELECTED_TOP_TABS = {
		ModUtils.id("container/creative_inventory/tab_top_unselected_1"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_2"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_3"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_4"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_5"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_6"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_7"),
	};

	@Unique
	private static final ResourceLocation[] EBI_SELECTED_BOTTOM_TABS = {
		ModUtils.id("container/creative_inventory/tab_bottom_selected_1"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_2"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_3"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_4"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_5"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_6"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_7"),
	};

	@Unique
	private static final ResourceLocation[] EBI_UNSELECTED_BOTTOM_TABS = {
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_1"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_2"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_3"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_4"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_5"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_6"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_7"),
	};

	@Shadow
	protected abstract boolean canScroll();

	@Shadow
	protected abstract void selectTab(CreativeModeTab group);

	@Shadow
	private static CreativeModeTab selectedTab;

	@Shadow
	private float scrollOffs;

	private CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=195"))
	private int modifyBackgroundWidth(int original, LocalPlayer player) {
		return player.getInventory().isTenfoursized() ? 213 : original;
	}

	@ModifyExpressionValue(method = "slotClicked", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNinesOnMouseClick(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "selectTab",
		at = @At(
			value = "CONSTANT",
			args = "intValue=9",
			ordinal = 0
		)
	)
	private int modifyNinesOnSelectTab1(int original) {
		// This should always be 10
		return 10;
	}

	@ModifyExpressionValue(
		method = "selectTab",
		at = @At(
			value = "CONSTANT",
			args = "intValue=9",
			ordinal = 1
		)
	)
	private int modifyNinesOnSelectTab2(int original) {
		// This one shouldn't *unless* the inventory is shifted
		return this.minecraft.gameMode.isTenfoursized() || this.shiftSavedToolbars ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "selectTab",
		at = @At(
			value = "CONSTANT",
			args = "intValue=0",
			ordinal = 1
		)
	)
	private int modifyZero(int original) {
		// Then we modify a zero!
		return !this.minecraft.gameMode.isTenfoursized() && this.shiftSavedToolbars ? 1 : original;
	}


	@ModifyExpressionValue(
		method = "selectTab",
		at = @At(
			value = "CONSTANT",
			args = "intValue=9"
		),
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "intValue=9",
				ordinal = 4
			),
			to = @At(
				value = "CONSTANT",
				args = "intValue=9",
				ordinal = 5
			)
		)
	)
	private int modifyNinesOnSelectTab3(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = {"slotClicked", "selectTab"}, at = @At(value = "CONSTANT", args = "intValue=36"))
	private int modify36(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 9 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "handleHotbarLoadOrSave", at = @At(value = "CONSTANT", args = "intValue=36"))
	private static int modify36OnHandleHotbarLoadOrSave(int original, Minecraft client) {
		return client.gameMode.isTenfoursized() ? 9 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "handleHotbarLoadOrSave", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getSelectionSize()I"))
	private static int modifyGetSelectionSizeOnHandleHotbarLoadOrSave(int original) {
		return 10;
	}

	@ModifyArg(method = "handleHotbarLoadOrSave", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;setItem(ILnet/minecraft/world/item/ItemStack;)V"))
	private static int modifySetItemToOffHand(int original, @Local(argsOnly = true) Minecraft client) {
		if (original == 9 && !client.gameMode.isTenfoursized()) {
			return 40;
		}

        return original;
    }

	// 45 in this case is the offhand slot
	@ModifyExpressionValue(method = "selectTab", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 9 + 10 * 4 : original;
	}

	// It isn't here though!
	@ModifyExpressionValue(method = "slotClicked", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45Constant2(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 10 * 5 : original;
	}

	// Coordinates time!
	@ModifyExpressionValue(method = "selectTab", at = @At(value = "CONSTANT", args = "intValue=35", ordinal = 0))
	private int modify35Constant(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 53 : original;
	}

	@ModifyExpressionValue(method = "selectTab", at = @At(value = "CONSTANT", args = "intValue=54", ordinal = 0))
	private int modify54Constant(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 72 : original;
	}

	@ModifyExpressionValue(method = "init", at = @At(value = "CONSTANT", args = "intValue=82", ordinal = 0))
	private int modify82Constant(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 100 : original;
	}

	@ModifyExpressionValue(method = "selectTab", at = @At(value = "CONSTANT", args = "intValue=173"))
	private int modifyDeleteSlotX(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 191 : original;
	}

	@ModifyExpressionValue(method = "insideScrollbar", at = @At(value = "CONSTANT", args = "intValue=175"))
	private int modifyScrollbarX(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 193 : original;
	}

	@ModifyExpressionValue(method = "renderBg", at = @At(value = "CONSTANT", args = "intValue=73"))
	private int modifyPlayerX(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 91 : original;
	}

	@ModifyExpressionValue(method = "renderBg", at = @At(value = "CONSTANT", args = "intValue=105"))
	private int modifyPlayerX2(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 123 : original;
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
		return this.minecraft.gameMode.isTenfoursized()
			? ModUtils.id(original.getPath())
			: original;
	}

	@WrapOperation(
		method = "renderBg",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"
		)
	)
	private void modifyScrollerTexture(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			var scrollerTexture = this.canScroll() ? EBI_SCROLLER_SPRITE : EBI_SCROLLER_DISABLED_SPRITE;
			graphics.blitSprite(function, scrollerTexture, x + 18, y, width, height);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "renderTabButton",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"
		)
	)
	private void modifyTabTexture(GuiGraphics graphics, Function<ResourceLocation, RenderType> function, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 0) boolean bl, @Local(ordinal = 1) boolean bl2, @Local(ordinal = 0) int i) {
		if (this.minecraft.gameMode.isTenfoursized()) {
			var tabTextures = bl2
				? (bl ? EBI_SELECTED_TOP_TABS : EBI_UNSELECTED_TOP_TABS)
				: (bl ? EBI_SELECTED_BOTTOM_TABS : EBI_UNSELECTED_BOTTOM_TABS);
			graphics.blitSprite(function, tabTextures[Mth.clamp(i, 0, tabTextures.length)], x, y, width, height);
		} else {
			original.call(graphics, function, texture, x, y, width, height);
		}
	}

	// The section where Saved Hotbars are saved
	// This is also the part where Loom is being a massive pain in the arse with the refmapless mode
	// Ignore this error!!!
	@WrapOperation(
		method = "selectTab",
		at = {
			@At(
				value = "INVOKE",
				target = "Lnet/minecraft/class_2371;addAll(Ljava/util/Collection;)Z",
				ordinal = 0,
				remap = false
			),
			@At(
				value = "INVOKE",
				target = "Lnet/minecraft/core/NonNullList;addAll(Ljava/util/Collection;)Z",
				ordinal = 0
			)
		},
		allow = 1
	)
	private boolean addHotbarContentsPartially(NonNullList<ItemStack> instance, Collection<ItemStack> collection, Operation<Boolean> original) {
		if (!this.minecraft.gameMode.isTenfoursized()) {
			// FIXME - This is dirty!
			for (int i = 0; i < 9; i++) {
				instance.add((collection.stream().toList()).get(i + (this.shiftSavedToolbars ? 1 : 0)));
			}

			// The result doesn't really matter
			return true;
		} else {
			return original.call(instance, collection);
		}
	}

	// TODO - I feel like these two should make a click sound of some sort
	@ModifyReturnValue(method = "keyPressed", at = @At(value = "RETURN", ordinal = 1))
	private boolean make10TenShift(boolean original, int keyCode, int scanCode) {
		if (selectedTab.getType() == CreativeModeTab.Type.HOTBAR && !this.minecraft.gameMode.isTenfoursized()) {
			if (this.minecraft.options.keyHotbarSlots[9].matches(keyCode, scanCode)) {
				float lastScrollPosition = this.scrollOffs;
				this.shiftSavedToolbars = !this.shiftSavedToolbars;
				this.selectTab(selectedTab);

				this.scrollOffs = lastScrollPosition;
				this.menu.scrollTo(lastScrollPosition);
				this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));

				return true;
			}
		}

		return original;
	}

	@WrapOperation(method = "mouseReleased", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;selectTab(Lnet/minecraft/world/item/CreativeModeTab;)V"))
	private void makeTabClickShift(CreativeModeInventoryScreen instance, CreativeModeTab group, Operation<Void> original) {
		if (group.getType() == CreativeModeTab.Type.HOTBAR && selectedTab == group && !this.minecraft.gameMode.isTenfoursized()) {
			float lastScrollPosition = this.scrollOffs;
			this.shiftSavedToolbars = !this.shiftSavedToolbars;
			this.selectTab(group);

			this.scrollOffs = lastScrollPosition;
			this.menu.scrollTo(lastScrollPosition);
			this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		} else {
			original.call(instance, group);
		}
	}

	@ModifyArg(
		method = "renderLabels",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I"
		)
	)
	private Component modifyRenderedLabel(Component original) {
		if (selectedTab.getType() == CreativeModeTab.Type.HOTBAR) {
			if (!this.minecraft.gameMode.isTenfoursized()) {
                return this.shiftSavedToolbars
					? Component.translatable("ennuis_bigger_inventories.item_group.saved_hotbars.shifted_right", original)
					: Component.translatable("ennuis_bigger_inventories.item_group.saved_hotbars.shifted_left", original);
			}
		}

		return original;
	}
}
