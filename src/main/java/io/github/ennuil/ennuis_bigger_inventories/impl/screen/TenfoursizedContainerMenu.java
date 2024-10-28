package io.github.ennuil.ennuis_bigger_inventories.impl.screen;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TenfoursizedContainerMenu extends AbstractContainerMenu {
	private final Container container;
	private final int containerRows;

	public TenfoursizedContainerMenu(MenuType<?> type, int syncId, Inventory inventory, int containerRows) {
		this(type, syncId, inventory, new SimpleContainer(10 * containerRows), containerRows);
	}

	public static TenfoursizedContainerMenu oneRow(int syncId, Inventory inventory) {
		return new TenfoursizedContainerMenu(ModMenuTypes.GENERIC_10X1, syncId, inventory, 1);
	}

	public static TenfoursizedContainerMenu twoRows(int syncId, Inventory inventory) {
		return new TenfoursizedContainerMenu(ModMenuTypes.GENERIC_10X2, syncId, inventory, 2);
	}

	public static TenfoursizedContainerMenu threeRows(int syncId, Inventory inventory) {
		return new TenfoursizedContainerMenu(ModMenuTypes.GENERIC_10X3, syncId, inventory, 3);
	}

	public static TenfoursizedContainerMenu fourRows(int syncId, Inventory inventory) {
		return new TenfoursizedContainerMenu(ModMenuTypes.GENERIC_10X4, syncId, inventory, 4);
	}

	public static TenfoursizedContainerMenu fiveRows(int syncId, Inventory inventory) {
		return new TenfoursizedContainerMenu(ModMenuTypes.GENERIC_10X5, syncId, inventory, 5);
	}

	public static TenfoursizedContainerMenu sixRows(int syncId, Inventory inventory) {
		return new TenfoursizedContainerMenu(ModMenuTypes.GENERIC_10X6, syncId, inventory, 6);
	}

	public static TenfoursizedContainerMenu threeRows(int syncId, Inventory inventory, Container container) {
		return new TenfoursizedContainerMenu(ModMenuTypes.GENERIC_10X3, syncId, inventory, container, 3);
	}

	public static TenfoursizedContainerMenu sixRows(int syncId, Inventory inventory, Container container) {
		return new TenfoursizedContainerMenu(ModMenuTypes.GENERIC_10X6, syncId, inventory, container, 6);
	}

	public TenfoursizedContainerMenu(MenuType<?> type, int syncId, Inventory inventory, Container container, int containerRows) {
		super(type, syncId);
		checkContainerSize(container, containerRows * 10);
		this.container = container;
		this.containerRows = containerRows;
		container.startOpen(inventory.player);
		int inventoryOffsetY = 18 + this.containerRows * 18 + 13;

		// TODO - Split me into a method!
		for (int i = 0; i < this.containerRows; i++) {
			for (int j = 0; j < 10; j++) {
				this.addSlot(new Slot(container, j + i * 10, 8 + j * 18, 18 + i * 18));
			}
		}

		this.addStandardInventorySlots(inventory, 8, inventoryOffsetY);
	}


	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int fromIndex) {
		var stack = ItemStack.EMPTY;
		var slot = this.slots.get(fromIndex);

		if (slot != null && slot.hasItem()) {
			var slotStack = slot.getItem();
			stack = slotStack.copy();

			if (fromIndex < this.containerRows * 10) {
				if (!this.moveItemStackTo(slotStack, this.containerRows * 10, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(slotStack, 0, this.containerRows * 10, false)) {
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return stack;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.container.stopOpen(player);
	}

	public Container getContainer() {
		return this.container;
	}

	public int getRowCount() {
		return this.containerRows;
	}
}
