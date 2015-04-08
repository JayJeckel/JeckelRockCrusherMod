package jeckelrockcrushermod.api;

import jeckelcorelibrary.api.managers.IConsumableManager;
import jeckelcorelibrary.api.managers.IRecipeManager;

public final class ModManager
{
	public static final ModManager INSTANCE = new ModManager();

	private ModManager() { }

	public IRecipeManager RockCrusherRecipes;

	public IConsumableManager RockCrusherFuel;
}
