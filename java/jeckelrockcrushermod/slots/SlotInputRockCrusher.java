package jeckelrockcrushermod.slots;

import jeckelrockcrushermod.api.ModManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInputRockCrusher extends Slot
{
    public SlotInputRockCrusher(IInventory inventory, int id, int x, int y)
    {
        super(inventory, id, x, y);
    }
    
    @Override public boolean isItemValid(ItemStack stack)
    {
    	return ModManager.INSTANCE.RockCrusherRecipes.getOutput(stack) != null;
    }
}
