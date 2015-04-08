package jeckelrockcrushermod;

import jeckelcorelibrary.base.managers.ConsumableManager;
import jeckelcorelibrary.base.managers.RecipeManager;
import jeckelcorelibrary.core.commands.InfoModCommand;
import jeckelrockcrushermod.api.ModManager;
import jeckelrockcrushermod.content.ContentManager;
import jeckelrockcrushermod.core.Refs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod
(
modid = Refs.ModId,
useMetadata = true,
canBeDeactivated = false,
guiFactory = Refs.ConfigFactoryTypeName
)
public class JeckelRockCrusherMod
{
	@Mod.Instance (value = Refs.ModId)
	public static JeckelRockCrusherMod INSTANCE;

	public JeckelRockCrusherMod() { }

	@Mod.EventHandler
	public void preInitialize(FMLPreInitializationEvent event)
	{
		Refs.pre(INSTANCE, event);
	}

	@Mod.EventHandler
	public void initialize(FMLInitializationEvent event)
	{
		Refs.initialize(event);

		ModManager.INSTANCE.RockCrusherFuel = new ConsumableManager();
		ModManager.INSTANCE.RockCrusherFuel.setConsumable(new ItemStack(Items.coal, 1, 1), 1600);
		ModManager.INSTANCE.RockCrusherFuel.setConsumable(new ItemStack(Items.coal, 1, 0), 3200);
		ModManager.INSTANCE.RockCrusherFuel.setConsumable(new ItemStack(Items.blaze_rod), 4800);
		ModManager.INSTANCE.RockCrusherFuel.setConsumable(new ItemStack(Blocks.coal_block), 32000);
		ModManager.INSTANCE.RockCrusherFuel.setConsumable("fuelCoke", 6400);

		ModManager.INSTANCE.RockCrusherRecipes = new RecipeManager();
		ModManager.INSTANCE.RockCrusherRecipes.addRecipe("glowstone", new ItemStack(Items.glowstone_dust, 4), 200);
		ModManager.INSTANCE.RockCrusherRecipes.addRecipe(new ItemStack(Blocks.quartz_ore), new ItemStack(Items.quartz, 2), 1000);
		ModManager.INSTANCE.RockCrusherRecipes.addRecipe("oreIron", new ItemStack(ContentManager.ModItems.crushed_iron, 2), 800);
		ModManager.INSTANCE.RockCrusherRecipes.addRecipe("oreGold", new ItemStack(ContentManager.ModItems.crushed_gold, 2), 800);
		ModManager.INSTANCE.RockCrusherRecipes.addRecipe("oreCopper", new ItemStack(ContentManager.ModItems.crushed_copper, 2), 800);
		ModManager.INSTANCE.RockCrusherRecipes.addRecipe("oreTin", new ItemStack(ContentManager.ModItems.crushed_tin, 2), 800);
		ModManager.INSTANCE.RockCrusherRecipes.addRecipe("oreSilver", new ItemStack(ContentManager.ModItems.crushed_silver, 2), 800);
		ModManager.INSTANCE.RockCrusherRecipes.addRecipe("oreLead", new ItemStack(ContentManager.ModItems.crushed_lead, 2), 800);
		ModManager.INSTANCE.RockCrusherRecipes.addRecipe("oreUranium", new ItemStack(ContentManager.ModItems.crushed_uranium, 2), 800);

		/*
glowstone
oreIron
oreGold
oreCopper
oreTin
oreSilver
oreLead
oreUranium

crushedIron
crushedGold
crushedCopper
crushedTin
crushedSilver
crushedLead
crushedUranium
		 */
	}

	@Mod.EventHandler
	public void postInitialization(FMLPostInitializationEvent event)
	{
		Refs.post(event);
	}

	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new InfoModCommand(Refs.getMetadata(), Refs.getUpdateChecker(), "Display info about the mod."));
	}
}
