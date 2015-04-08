package jeckelrockcrushermod.slots;

import jeckelrockcrushermod.api.ModManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFuelRockCrusher extends Slot
{
    public SlotFuelRockCrusher(IInventory inventory, int id, int x, int y)
    {
        super(inventory, id, x, y);
    }
    
    @Override public boolean isItemValid(ItemStack stack)
    {
    	return ModManager.INSTANCE.RockCrusherFuel.getConsumableTime(stack) > 0;
    }
}
