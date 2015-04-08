package jeckelrockcrushermod.content.rockcrusher;

import java.util.ArrayList;
import java.util.List;

import jeckelcorelibrary.api.IInventoryDropFilter;
import jeckelcorelibrary.api.guis.ITileGuiActivator;
import jeckelcorelibrary.api.processes.ITickProcess;
import jeckelcorelibrary.api.tiles.ITileInteractable;
import jeckelcorelibrary.api.tiles.ITileProcessor;
import jeckelcorelibrary.base.tiles.ATileInventory;
import jeckelcorelibrary.core.processes.solids.DynamicMultiSolidToSolidConverterProcess;
import jeckelcorelibrary.core.processes.solids.DynamicSolidConsumerProcess;
import jeckelcorelibrary.core.processes.solids.MultiSolidToSolidTransferProcess;
import jeckelrockcrushermod.api.ModManager;
import jeckelrockcrushermod.content.ContentManager;
import jeckelrockcrushermod.core.Refs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class TileRockCrusher
extends ATileInventory
implements ITileInteractable, ITileGuiActivator, IInventoryDropFilter, ITileProcessor
{
	public TileRockCrusher()
	{
		super(17);
		this.setTileName(ContentManager.ModBlocks.rock_crusher.getUnlocalizedName() + ".name");

		this._processes = new ArrayList<ITickProcess>();

		this.fuelConsumer = new DynamicSolidConsumerProcess("fuel_consumer", this, 1, ModManager.INSTANCE.RockCrusherFuel);
		this._processes.add(this.fuelConsumer);

		this.fuelTransfer = new MultiSolidToSolidTransferProcess("fuel_transfer", 10, this,
				new int[] { 14, 15, 16 }, new int[] { 1 });
		this._processes.add(this.fuelTransfer);

		this.inputConverter = new DynamicMultiSolidToSolidConverterProcess("input_converter", this,
				new int[] { 0 }, new int[] { 6, 7, 8, 9, 10, 11, 12, 13 },
				ModManager.INSTANCE.RockCrusherRecipes);
		this._processes.add(this.inputConverter);

		this.inputTransfer = new MultiSolidToSolidTransferProcess("input_transfer", 10, this,
				new int[] { 2, 3, 4, 5 }, new int[] { 0 });
		this._processes.add(this.inputTransfer);
	}

	private final List<ITickProcess> _processes;

	public final ITickProcess fuelConsumer;
	public final MultiSolidToSolidTransferProcess fuelTransfer;

	public final ITickProcess inputConverter;
	public final MultiSolidToSolidTransferProcess inputTransfer;

	@Override public void updateEntity()
	{
		boolean dirty = false;

		if (!this.worldObj.isRemote)
		{
			if (this.fuelConsumer.updateProcess(this.worldObj)) { dirty = true; }

			if (this.fuelConsumer.isProcessing())
			{
				final int inputIndex = this.inputTransfer.getSlotOutputIndex();
				if (inputIndex >= 0 && this.getStackInSlot(inputIndex) == null)
				{
					if (this.inputTransfer.updateProcess(this.worldObj)) { dirty = true; }
				}
				if (this.inputConverter.updateProcess(this.worldObj)) { dirty = true; }
			}

			int inputIndex = this.inputTransfer.getSlotInputIndex();
			final int fuelIndex = this.fuelTransfer.getSlotOutputIndex();
			if (fuelIndex >= 0 && this.getStackInSlot(fuelIndex) == null && (inputIndex >= 0 || this.inputConverter.isProcessing()))
			{
				if (this.fuelTransfer.updateProcess(this.worldObj)) { dirty = true; }
			}

			final int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);

			int state = 0;
			if (this.fuelConsumer.isProcessing()) { state += 1; }
			if (this.inputConverter.isProcessing()) { state += 2; }

			if (meta != state)
			{
				this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, state, 2);
			}

			/*if (this.fuelConsumerProcess.isCycleBegin() || this.fuelConsumerProcess.isCycleEnd()
					|| this.inputConverterProcess.isCycleBegin() || this.inputConverterProcess.isCycleEnd())
			{
				this.getModInfo().getModNetwork().sendToAll(new MessageRockCrusher(this));
			}*/
		}

		if (dirty) { this.markDirty(); }
	}


	// ##################################################
	//
	// ITileInteractable
	//
	// ##################################################

	@Override public void interact(EntityPlayer player, World world, int x, int y, int z, int side)
	{
		if (player.isSneaking()) { return; }
		player.openGui(Refs.getMod(), 0, world, x, y, z);
	}


	// ##################################################
	//
	// ITileGuiActivator
	//
	// ##################################################

	@Override public Object createContainer(EntityPlayer player) { return new ContainerRockCrusher(player, this); }

	@Override public Object createScreen(EntityPlayer player) { return new ScreenRockCrusher(player, this); }


	// ##################################################
	//
	// ITileProcessor
	//
	// ##################################################

	@Override public List<ITickProcess> getProcesses() { return this._processes; }


	// ##################################################
	//
	// IInventoryDropFilter
	//
	// ##################################################

	@Override public boolean canInventoryDropSlot(int index)
	{
		return index != 0 && index != 1;
	}
}
