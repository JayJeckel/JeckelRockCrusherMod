package jeckelrockcrushermod.content.rockcrusher;

import java.awt.Point;
import java.awt.Rectangle;

import jeckelcorelibrary.api.processes.ITickProcess;
import jeckelcorelibrary.base.guis.AScreenTileInventory;
import jeckelrockcrushermod.core.Refs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ScreenRockCrusher extends AScreenTileInventory<TileRockCrusher>
{
	public ScreenRockCrusher(EntityPlayer player, TileRockCrusher tile)
	{
		super(player, tile, new ContainerRockCrusher(player, tile), tile, 176, 186);
		this._resource = new ResourceLocation(Refs.ModId, "textures/guis/rock_crusher.png");
	}

	private Rectangle rectFuel = new Rectangle(72, 37, 14, 14);
	private Rectangle rectInputTransfer = new Rectangle(28, 19, 22, 15);
	private Rectangle rectInputConvert0 = new Rectangle(53, 18, 16, 16);
	private Rectangle rectInputConvert1 = new Rectangle(90, 18, 16, 16);
	private Rectangle rectOutputTransfer = new Rectangle(109, 19, 22, 15);

	@Override public ResourceLocation getResourceLocation() { return this._resource; }
	private ResourceLocation _resource;

	@Override protected void onDrawOverlays()
	{
		if (this._tile.fuelConsumer.isProcessing())
		{
			final int scaled = this._tile.fuelConsumer.getTimeScaled(this.rectFuel.height);
			this.drawImageInvertedScaledHeight(this.rectFuel, new Point(176, 0), scaled, true);
		}
		else if (this._tile.fuelTransfer.isProcessing())
		{
			final int scaled = this._tile.fuelTransfer.getTimeScaled(this.rectFuel.height);
			this.drawImageScaledHeight(this.rectFuel, new Point(176, 0), scaled, true);
		}

		if (this._tile.inputTransfer.isProcessing())
		{
			this.drawImage(this.rectInputTransfer, new Point(176, 14));
		}

		if (this._tile.inputConverter.isProcessing())
		{
			final int scaled = this._tile.inputConverter.getTimeScaled(this.rectInputConvert0.width);
			this.drawImageScaledWidth(this.rectInputConvert0, new Point(176, 29), scaled, false);
			this.drawImageScaledWidth(this.rectInputConvert1, new Point(176, 45), scaled, true);

			this.drawImage(this.rectOutputTransfer, new Point(176, 14));
		}
	}

	@Override protected void onDrawTexts()
	{
		this.drawTextLeft(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2);
	}

	@Override protected void onDrawTooltips(int x, int y)
	{
		if (this.rectFuel.contains(x, y))
		{
			final String title = (this._tile.fuelTransfer.isProcessing() ? "Transferring" : "Fueling");
			final ITickProcess process = (this._tile.fuelTransfer.isProcessing() ? this._tile.fuelTransfer : this._tile.fuelConsumer);
			this.drawProcessTooltip(x, y, title, process);
		}
		else if (this.rectInputTransfer.contains(x, y))
		{
			this.drawProcessTooltip(x, y, "Transferring", this._tile.inputTransfer);
		}
		else if (this.rectInputConvert0.contains(x, y) || this.rectInputConvert1.contains(x, y) || this.rectOutputTransfer.contains(x, y))
		{
			this.drawProcessTooltip(x, y, "Converting", this._tile.inputConverter);
		}
	}
}
