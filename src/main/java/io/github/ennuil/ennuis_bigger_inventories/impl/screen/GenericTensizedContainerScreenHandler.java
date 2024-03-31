package io.github.ennuil.ennuis_bigger_inventories.impl.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class GenericTensizedContainerScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	private final int rows;

	public GenericTensizedContainerScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, int rows) {
		this(type, syncId, playerInventory, new SimpleInventory(10 * rows), rows);
	}

	public static GenericTensizedContainerScreenHandler createGeneric10x1(int syncId, PlayerInventory playerInventory) {
		return new GenericTensizedContainerScreenHandler(ModScreenHandlerTypes.GENERIC_10X1, syncId, playerInventory, 1);
	}

	public static GenericTensizedContainerScreenHandler createGeneric10x2(int syncId, PlayerInventory playerInventory) {
		return new GenericTensizedContainerScreenHandler(ModScreenHandlerTypes.GENERIC_10X2, syncId, playerInventory, 2);
	}

	public static GenericTensizedContainerScreenHandler createGeneric10x3(int syncId, PlayerInventory playerInventory) {
		return new GenericTensizedContainerScreenHandler(ModScreenHandlerTypes.GENERIC_10X3, syncId, playerInventory, 3);
	}

	public static GenericTensizedContainerScreenHandler createGeneric10x4(int syncId, PlayerInventory playerInventory) {
		return new GenericTensizedContainerScreenHandler(ModScreenHandlerTypes.GENERIC_10X4, syncId, playerInventory, 4);
	}

	public static GenericTensizedContainerScreenHandler createGeneric10x5(int syncId, PlayerInventory playerInventory) {
		return new GenericTensizedContainerScreenHandler(ModScreenHandlerTypes.GENERIC_10X5, syncId, playerInventory, 5);
	}

	public static GenericTensizedContainerScreenHandler createGeneric10x6(int syncId, PlayerInventory playerInventory) {
		return new GenericTensizedContainerScreenHandler(ModScreenHandlerTypes.GENERIC_10X6, syncId, playerInventory, 6);
	}

	public static GenericTensizedContainerScreenHandler createGeneric10x3(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		return new GenericTensizedContainerScreenHandler(ModScreenHandlerTypes.GENERIC_10X3, syncId, playerInventory, inventory, 3);
	}

	public static GenericTensizedContainerScreenHandler createGeneric10x6(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		return new GenericTensizedContainerScreenHandler(ModScreenHandlerTypes.GENERIC_10X6, syncId, playerInventory, inventory, 6);
	}

	public GenericTensizedContainerScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int rows) {
		super(type, syncId);
		checkSize(inventory, rows * 10);
		this.inventory = inventory;
		this.rows = rows;
		inventory.onOpen(playerInventory.player);
		int playerInventoryOffsetY = (this.rows - 4) * 18;

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < 10; j++) {
				this.addSlot(new Slot(inventory, j + i * 10, 8 + j * 18, 18 + i * 18));
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				this.addSlot(new Slot(playerInventory, j + i * 10 + 10, 8 + j * 18, 103 + i * 18 + playerInventoryOffsetY));
			}
		}

		for (int i = 0; i < 10; i++) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 161 + playerInventoryOffsetY));
		}
	}


	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		var stack = ItemStack.EMPTY;
		var slot = this.slots.get(fromIndex);

		if (slot != null && slot.hasStack()) {
			var slotStack = slot.getStack();
			stack = slotStack.copy();

			if (fromIndex < this.rows * 10) {
				if (!this.insertItem(slotStack, this.rows * 10, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(slotStack, 0, this.rows * 10, false)) {
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty()) {
				slot.setStackByPlayer(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return stack;
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		this.inventory.onClose(player);
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public int getRows() {
		return this.rows;
	}
}
