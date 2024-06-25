package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Collection;

@ClientOnly
@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
	@Unique private static final Identifier EBI_SCROLLER_TEXTURE = ModUtils.id("container/creative_inventory/scroller");
	@Unique private static final Identifier EBI_SCROLLER_DISABLED_TEXTURE = ModUtils.id("container/creative_inventory/scroller_disabled");

	@Unique
	private boolean shiftSavedToolbars = false;

	@Unique
	private static final Identifier[] EBI_TAB_TOP_SELECTED_TEXTURES = {
		ModUtils.id("container/creative_inventory/tab_top_selected_1"),
		ModUtils.id("container/creative_inventory/tab_top_selected_2"),
		ModUtils.id("container/creative_inventory/tab_top_selected_3"),
		ModUtils.id("container/creative_inventory/tab_top_selected_4"),
		ModUtils.id("container/creative_inventory/tab_top_selected_5"),
		ModUtils.id("container/creative_inventory/tab_top_selected_6"),
		ModUtils.id("container/creative_inventory/tab_top_selected_7"),
	};

	@Unique
	private static final Identifier[] EBI_TAB_TOP_UNSELECTED_TEXTURES = {
		ModUtils.id("container/creative_inventory/tab_top_unselected_1"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_2"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_3"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_4"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_5"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_6"),
		ModUtils.id("container/creative_inventory/tab_top_unselected_7"),
	};

	@Unique
	private static final Identifier[] EBI_TAB_BOTTOM_SELECTED_TEXTURES = {
		ModUtils.id("container/creative_inventory/tab_bottom_selected_1"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_2"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_3"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_4"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_5"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_6"),
		ModUtils.id("container/creative_inventory/tab_bottom_selected_7"),
	};

	@Unique
	private static final Identifier[] EBI_TAB_BOTTOM_UNSELECTED_TEXTURES = {
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_1"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_2"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_3"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_4"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_5"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_6"),
		ModUtils.id("container/creative_inventory/tab_bottom_unselected_7"),
	};

	@Shadow
	protected abstract boolean hasScrollbar();

	@Shadow
	protected abstract void setSelectedTab(ItemGroup group);

	@Shadow
	private static ItemGroup selectedTab;

	@Shadow
	private float scrollPosition;

	private CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}

	@ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=195"))
	private int modifyBackgroundWidth(int original, ClientPlayerEntity player) {
		return player.getInventory().isTenfoursized() ? 213 : original;
	}

	@ModifyExpressionValue(method = "onMouseClick", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNinesOnMouseClick(int original) {
		return this.client.interactionManager.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "setSelectedTab",
		at = @At(
			value = "CONSTANT",
			args = "intValue=9",
			ordinal = 0
		)
	)
	private int modifyNinesOnSetSelectedTab1(int original) {
		// This should always be 10
		return 10;
	}

	@ModifyExpressionValue(
		method = "setSelectedTab",
		at = @At(
			value = "CONSTANT",
			args = "intValue=9",
			ordinal = 1
		)
	)
	private int modifyNinesOnSetSelectedTab2(int original) {
		// This one shouldn't *unless* the inventory is shifted
		return this.client.interactionManager.isTenfoursized() || this.shiftSavedToolbars ? 10 : original;
	}

	@ModifyExpressionValue(
		method = "setSelectedTab",
		at = @At(
			value = "CONSTANT",
			args = "intValue=0",
			ordinal = 1
		)
	)
	private int modifyZero(int original) {
		// Then we modify a zero!
		return !this.client.interactionManager.isTenfoursized() && this.shiftSavedToolbars ? 1 : original;
	}


	@ModifyExpressionValue(
		method = "setSelectedTab",
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
	private int modifyNinesOnSetSelectedTab3(int original) {
		return this.client.interactionManager.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = {"onMouseClick", "setSelectedTab"}, at = @At(value = "CONSTANT", args = "intValue=36"))
	private int modify36(int original) {
		return this.client.interactionManager.isTenfoursized() ? 9 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "onHotbarKeyPress", at = @At(value = "CONSTANT", args = "intValue=36"))
	private static int modify36OnHotbarKeyPress(int original, MinecraftClient client) {
		return client.interactionManager.isTenfoursized() ? 9 + 10 * 3 : original;
	}

	@ModifyExpressionValue(method = "onHotbarKeyPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getHotbarSize()I"))
	private static int modifyGetHotbarSizeOnHotbarKeyPress(int original) {
		return 10;
	}

	@ModifyArg(method = "onHotbarKeyPress", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;setStack(ILnet/minecraft/item/ItemStack;)V"))
	private static int modifySetStackToOffHand(int original, @Local(argsOnly = true) MinecraftClient client) {
		if (original == 9 && !client.interactionManager.isTenfoursized()) {
			return 40;
		}

        return original;
    }

	// 45 in this case is the offhand slot
	@ModifyExpressionValue(method = "setSelectedTab", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45(int original) {
		return this.client.interactionManager.isTenfoursized() ? 9 + 10 * 4 : original;
	}

	// It isn't here though!
	@ModifyExpressionValue(method = "onMouseClick", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45Constant2(int original) {
		return this.client.interactionManager.isTenfoursized() ? 10 * 5 : original;
	}

	// Coordinates time!
	@ModifyExpressionValue(method = "setSelectedTab", at = @At(value = "CONSTANT", args = "intValue=35", ordinal = 0))
	private int modify35Constant(int original) {
		return this.client.interactionManager.isTenfoursized() ? 53 : original;
	}

	@ModifyExpressionValue(method = "setSelectedTab", at = @At(value = "CONSTANT", args = "intValue=54", ordinal = 0))
	private int modify54Constant(int original) {
		return this.client.interactionManager.isTenfoursized() ? 72 : original;
	}

	@ModifyExpressionValue(method = "init", at = @At(value = "CONSTANT", args = "intValue=82", ordinal = 0))
	private int modify82Constant(int original) {
		return this.client.interactionManager.isTenfoursized() ? 100 : original;
	}

	@ModifyExpressionValue(method = "setSelectedTab", at = @At(value = "CONSTANT", args = "intValue=173"))
	private int modifyDeleteSlotX(int original) {
		return this.client.interactionManager.isTenfoursized() ? 191 : original;
	}

	@ModifyExpressionValue(method = "isClickInScrollbar", at = @At(value = "CONSTANT", args = "intValue=175"))
	private int modifyScrollbarX(int original) {
		return this.client.interactionManager.isTenfoursized() ? 193 : original;
	}

	@ModifyExpressionValue(method = "drawBackground", at = @At(value = "CONSTANT", args = "intValue=73"))
	private int modifyPlayerX(int original) {
		return this.client.interactionManager.isTenfoursized() ? 91 : original;
	}

	@ModifyExpressionValue(method = "drawBackground", at = @At(value = "CONSTANT", args = "intValue=105"))
	private int modifyPlayerX2(int original) {
		return this.client.interactionManager.isTenfoursized() ? 123 : original;
	}

	@ModifyArg(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 0
		)
	)
	private Identifier modifyTexture(Identifier original) {
		return this.client.interactionManager.isTenfoursized()
			? ModUtils.id(original.getPath())
			: original;
	}

	@WrapOperation(
		method = "drawBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private void modifyScrollerTexture(GuiGraphics graphics, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
		if (this.client.interactionManager.isTenfoursized()) {
			var scrollerTexture = this.hasScrollbar() ? EBI_SCROLLER_TEXTURE : EBI_SCROLLER_DISABLED_TEXTURE;
			graphics.drawGuiTexture(scrollerTexture, x + 18, y, width, height);
		} else {
			original.call(graphics, texture, x, y, width, height);
		}
	}

	@WrapOperation(
		method = "renderTabIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private void modifyTabTexture(GuiGraphics graphics, Identifier texture, int x, int y, int width, int height, Operation<Void> original, @Local(ordinal = 0) boolean bl, @Local(ordinal = 1) boolean bl2, @Local(ordinal = 0) int i) {
		if (this.client.interactionManager.isTenfoursized()) {
			var tabTextures = bl2
				? (bl ? EBI_TAB_TOP_SELECTED_TEXTURES : EBI_TAB_TOP_UNSELECTED_TEXTURES)
				: (bl ? EBI_TAB_BOTTOM_SELECTED_TEXTURES : EBI_TAB_BOTTOM_UNSELECTED_TEXTURES);
			graphics.drawGuiTexture(tabTextures[MathHelper.clamp(i, 0, tabTextures.length)], x, y, width, height);
		} else {
			original.call(graphics, texture, x, y, width, height);
		}
	}

	// The section where Saved Hotbars are saved
	// This is also the part where Loom is being a massive pain in the arse with the refmapless mode
	@WrapOperation(
		method = "setSelectedTab",
		at = {
			@At(
				value = "INVOKE",
				target = "Lnet/minecraft/class_2371;addAll(Ljava/util/Collection;)Z",
				ordinal = 0,
				remap = false
			),
			@At(
				value = "INVOKE",
				target = "Lnet/minecraft/util/collection/DefaultedList;addAll(Ljava/util/Collection;)Z",
				ordinal = 0
			)
		},
		allow = 1
	)
	private boolean addHotbarContentsPartially(DefaultedList<ItemStack> instance, Collection<ItemStack> collection, Operation<Boolean> original) {
		if (!this.client.interactionManager.isTenfoursized()) {
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
		if (selectedTab.getType() == ItemGroup.Type.HOTBAR && !this.client.interactionManager.isTenfoursized()) {
			if (this.client.options.hotbarKeys[9].matchesKey(keyCode, scanCode)) {
				float lastScrollPosition = this.scrollPosition;
				this.shiftSavedToolbars = !this.shiftSavedToolbars;
				this.setSelectedTab(selectedTab);

				this.scrollPosition = lastScrollPosition;
				this.handler.scrollItems(lastScrollPosition);
				this.client.getSoundManager().play(PositionedSoundInstance.create(SoundEvents.UI_BUTTON_CLICK, 1.0F));

				return true;
			}
		}

		return original;
	}

	@WrapOperation(method = "mouseReleased", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen;setSelectedTab(Lnet/minecraft/item/ItemGroup;)V"))
	private void makeTabClickShift(CreativeInventoryScreen instance, ItemGroup group, Operation<Void> original) {
		if (group.getType() == ItemGroup.Type.HOTBAR && selectedTab == group && !this.client.interactionManager.isTenfoursized()) {
			float lastScrollPosition = this.scrollPosition;
			this.shiftSavedToolbars = !this.shiftSavedToolbars;
			this.setSelectedTab(group);

			this.scrollPosition = lastScrollPosition;
			this.handler.scrollItems(lastScrollPosition);
			this.client.getSoundManager().play(PositionedSoundInstance.create(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		} else {
			original.call(instance, group);
		}
	}

	@ModifyArg(
		method = "drawForeground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I"
		)
	)
	private Text modifyRenderedName(Text original) {
		if (selectedTab.getType() == ItemGroup.Type.HOTBAR) {
			if (!this.client.interactionManager.isTenfoursized()) {
                return this.shiftSavedToolbars
					? Text.translatable("ennuis_bigger_inventories.item_group.saved_hotbars.shifted_right", original)
					: Text.translatable("ennuis_bigger_inventories.item_group.saved_hotbars.shifted_left", original);
			}
		}

		return original;
	}
}
