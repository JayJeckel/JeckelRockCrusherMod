package jeckelrockcrushermod.content;

import java.util.ArrayList;
import java.util.List;

import jeckelcorelibrary.GlobalRefs;
import jeckelcorelibrary.api.managers.IContentManager;
import jeckelcorelibrary.utils.GameRegUtil;
import jeckelrockcrushermod.content.items.ItemCrushedOre;
import jeckelrockcrushermod.content.rockcrusher.BlockRockCrusher;
import jeckelrockcrushermod.content.rockcrusher.ItemBlockRockCrusher;
import jeckelrockcrushermod.content.rockcrusher.TileRockCrusher;
import jeckelrockcrushermod.core.Refs;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class ContentManager implements IContentManager
{
	public static class ModBlocks
	{
		public static Block rock_crusher;
	}

	public static class ModItems
	{
		public static ItemCrushedOre crushed_iron;
		public static ItemCrushedOre crushed_gold;
		public static ItemCrushedOre crushed_copper;
		public static ItemCrushedOre crushed_tin;
		public static ItemCrushedOre crushed_lead;
		public static ItemCrushedOre crushed_silver;
		public static ItemCrushedOre crushed_uranium;
	}

	public static List<ItemCrushedOre> crushedOreList = new ArrayList<ItemCrushedOre>();

	public void pre()
	{
		ModBlocks.rock_crusher = new BlockRockCrusher();
		GameRegUtil.block(ModBlocks.rock_crusher, ItemBlockRockCrusher.class, TileRockCrusher.class);

		ModItems.crushed_iron = new ItemCrushedOre("crushed_iron");
		GameRegUtil.item(ModItems.crushed_iron);
		OreDictionary.registerOre("crushedIron", ModItems.crushed_iron);
		OreDictionary.registerOre("dustIron", ModItems.crushed_iron);

		ModItems.crushed_gold = new ItemCrushedOre("crushed_gold");
		GameRegUtil.item(ModItems.crushed_gold);
		OreDictionary.registerOre("crushedGold", ModItems.crushed_gold);
		OreDictionary.registerOre("dustGold", ModItems.crushed_gold);

		ModItems.crushed_copper = new ItemCrushedOre("crushed_copper");
		GameRegUtil.item(ModItems.crushed_copper);
		OreDictionary.registerOre("crushedCopper", ModItems.crushed_copper);
		OreDictionary.registerOre("dustCopper", ModItems.crushed_copper);

		ModItems.crushed_tin = new ItemCrushedOre("crushed_tin");
		GameRegUtil.item(ModItems.crushed_tin);
		OreDictionary.registerOre("crushedTin", ModItems.crushed_tin);
		OreDictionary.registerOre("dustTin", ModItems.crushed_tin);

		ModItems.crushed_lead = new ItemCrushedOre("crushed_lead");
		GameRegUtil.item(ModItems.crushed_lead);
		OreDictionary.registerOre("crushedLead", ModItems.crushed_lead);
		OreDictionary.registerOre("dustLead", ModItems.crushed_lead);

		ModItems.crushed_silver = new ItemCrushedOre("crushed_silver");
		GameRegUtil.item(ModItems.crushed_silver);
		OreDictionary.registerOre("crushedSilver", ModItems.crushed_silver);
		OreDictionary.registerOre("dustSilver", ModItems.crushed_silver);

		ModItems.crushed_uranium = new ItemCrushedOre("crushed_uranium");
		GameRegUtil.item(ModItems.crushed_uranium);
		OreDictionary.registerOre("crushedUranium", ModItems.crushed_uranium);
		OreDictionary.registerOre("dustUranium", ModItems.crushed_uranium);

		crushedOreList.add(ModItems.crushed_iron);
		crushedOreList.add(ModItems.crushed_gold);
		crushedOreList.add(ModItems.crushed_copper);
		crushedOreList.add(ModItems.crushed_tin);
		crushedOreList.add(ModItems.crushed_lead);
		crushedOreList.add(ModItems.crushed_silver);
		crushedOreList.add(ModItems.crushed_uranium);

		GlobalRefs.getTabManager().addMachineBlock(Refs.ModId, ModBlocks.rock_crusher);
		GlobalRefs.getTabManager().addMachineItem(Refs.ModId, ModItems.crushed_iron);
		GlobalRefs.getTabManager().addMachineItem(Refs.ModId, ModItems.crushed_gold);
		GlobalRefs.getTabManager().addMachineItem(Refs.ModId, ModItems.crushed_copper);
		GlobalRefs.getTabManager().addMachineItem(Refs.ModId, ModItems.crushed_tin);
		GlobalRefs.getTabManager().addMachineItem(Refs.ModId, ModItems.crushed_lead);
		GlobalRefs.getTabManager().addMachineItem(Refs.ModId, ModItems.crushed_silver);
		GlobalRefs.getTabManager().addMachineItem(Refs.ModId, ModItems.crushed_uranium);
	}

	@Override public void initialize()
	{
		GameRegUtil.recipeShaped(new ItemStack(ModBlocks.rock_crusher),
				"OOO",
				"PRP",
				"OGO",
				'O', Blocks.obsidian,
				'P', Blocks.piston,
				'R', Blocks.redstone_block,
				'G', Blocks.stone_pressure_plate);

		GameRegistry.addSmelting(ModItems.crushed_iron, new ItemStack(Items.iron_ingot, 1), 0.5F);
		GameRegistry.addSmelting(ModItems.crushed_gold, new ItemStack(Items.gold_ingot, 1), 0.5F);

		final String[] keys = new String[] { "ingotCopper", "ingotTin", "ingotLead", "ingotSilver", "ingotUranium" };
		for (int index = 0; index < keys.length; index++)
		{
			List<ItemStack> outputs = OreDictionary.getOres(keys[index]);
			if (outputs.size() > 0)
			{
				ItemStack input = new ItemStack(crushedOreList.get(index));
				input.stackSize = 1;
				ItemStack output = outputs.get(0).copy();
				output.stackSize = 1;
				GameRegistry.addSmelting(input, output, 0.5F);
			}
		}
	}

	@Override public void post()
	{
	}
}
