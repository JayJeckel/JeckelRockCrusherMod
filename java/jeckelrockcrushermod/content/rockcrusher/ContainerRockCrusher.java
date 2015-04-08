package jeckelrockcrushermod.content.rockcrusher;

import jeckelcorelibrary.base.guis.AContainerTileInventory;
import jeckelcorelibrary.core.slots.SlotDisplay;
import jeckelcorelibrary.core.slots.SlotOutput;
import jeckelrockcrushermod.slots.SlotFuelRockCrusher;
import jeckelrockcrushermod.slots.SlotInputRockCrusher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerRockCrusher extends AContainerTileInventory<TileRockCrusher>
{
	public ContainerRockCrusher(EntityPlayer player, TileRockCrusher tile)
	{
		super(player, tile, tile, 176, 186);

		int cols, rows;

		// Internal Inventory

		// Process Input Slot
		this.addSlotToContainer(new SlotDisplay(this._inventory, 0, 71, 18));
		// Process Fuel Slot
		this.addSlotToContainer(new SlotDisplay(this._inventory, 1, 71, 54));
		// Input Slots
		cols = 1;
		rows = 4;
		this.addInventorySlots(this._inventory, 8, 18, cols, rows, 2, SlotInputRockCrusher.class);
		// Output Slots
		cols = 2;
		rows = 4;
		this.addInventorySlots(this._inventory, 134, 18, cols, rows, 6, SlotOutput.class);
		// Fuel Slots
		cols = 3;
		rows = 1;
		this.addInventorySlots(this._inventory, 53, 72, cols, rows, 14, SlotFuelRockCrusher.class);

		// Player Inventory
		this.addPlayerInventorySlots(this._player.inventory, 8, this._height);
		this.addPlayerHotbarSlots(this._player.inventory, 8, this._height);
	}

	@Override protected int getMergeSlotCount(final int slotIndex)
	{
		switch (slotIndex) { case 2: { return 4; } case 14: { return 3; } default: { return 0; } }
	}

	@Override protected boolean isValidSlotItem(final EntityPlayer player, final int slotIndex, final ItemStack stack)
	{
		return this.getSlot(slotIndex).isItemValid(stack);
	}
}
