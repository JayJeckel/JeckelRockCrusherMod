package jeckelrockcrushermod.content.rockcrusher;

import java.util.Random;

import jeckelcorelibrary.api.tiles.ITileFrontSide;
import jeckelcorelibrary.base.blocks.ABlockTile;
import jeckelcorelibrary.utils.DirUtil;
import jeckelrockcrushermod.core.Refs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRockCrusher extends ABlockTile
{
	public BlockRockCrusher()
	{
		super(Refs.ModId, "rock_crusher", Material.iron, Block.soundTypeMetal);
		this._iconSides = new IIcon[6];
	}

	@Override public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileRockCrusher();
	}

	@Override public boolean canHarvestBlock(EntityPlayer player, int meta) { return true; }


	// ##################################################
	//
	// Icon Methods
	//
	// ##################################################

	protected IIcon _iconFront;
	protected IIcon[] _iconSides;

	@SideOnly(Side.CLIENT)
	@Override public void registerBlockIcons(IIconRegister reg)
	{
		this._iconFront = reg.registerIcon(this.textureName + "." + "front");
		this._iconSides[0] = reg.registerIcon(this.textureName + "." + "down");
		this._iconSides[1] = reg.registerIcon(this.textureName + "." + "up");
		for (int i = 2; i < 6; i++)
		{
			this._iconSides[i] = reg.registerIcon(this.textureName);
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getFrontIcon(int side, int meta)
	{
		return this._iconFront;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getSideIcon(int side, int meta, int front)
	{
		if (side == front) { return this.getFrontIcon(side, meta); }
		return this._iconSides[side];
	}

	@SideOnly(Side.CLIENT)
	@Override public IIcon getIcon(int side, int meta)
	{
		return this.getSideIcon(side, meta, DirUtil.DEFAULT_FRONT.ordinal());
	}

	@SideOnly(Side.CLIENT)
	@Override public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
	{
		int front = DirUtil.DEFAULT_FRONT.ordinal();
		int meta = blockAccess.getBlockMetadata(x, y, z);
		TileEntity tile = blockAccess.getTileEntity(x, y, z);
		if (tile != null && tile instanceof ITileFrontSide) { front = ((ITileFrontSide)tile).getFrontSide(); }
		return this.getSideIcon(side, meta, front);
	}


	// ##################################################
	//
	// Particle Methods
	//
	// ##################################################

	@SideOnly(Side.CLIENT)
	@Override public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te == null || !(te instanceof TileRockCrusher)) { return; }

		float xRand, zRand;
		float offset = 0.65F;
		int meta = world.getBlockMetadata(x, y, z);
		TileRockCrusher tile = (TileRockCrusher) te;
		int dir = tile.getFrontSide();

		float xParticle = (float)x + 0.5F;
		float yParticle = (float)y + 1.0F;
		float zParticle = (float)z + 0.5F;


		if (meta == 1 || meta == 3)
		{
			xRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			zRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			world.spawnParticle("smoke", (double)(xParticle + xRand), (double)yParticle, (double)(zParticle + zRand), 0.0D, 0.0D, 0.0D);

			xRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			zRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			world.spawnParticle("smoke", (double)(xParticle + xRand), (double)yParticle, (double)(zParticle + zRand), 0.0D, 0.0D, 0.0D);
		}

		if (meta == 3)
		{
			//yParticle += 0.1;
			yParticle -= 0.5;
			if (dir == 4) { xParticle -= offset; }
			else if (dir == 5) { xParticle += offset; }
			else if (dir == 2) { zParticle -= offset; }
			else if (dir == 3) { zParticle += offset; }
			String particle = "blockcrack_" + Block.getIdFromBlock(Blocks.stone) + "_0";
			xRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			zRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			world.spawnParticle(particle, (double)(xParticle + xRand), (double)yParticle, (double)(zParticle + zRand), 0.0D, 0.0D, 0.0D);

			xRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			zRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			world.spawnParticle(particle, (double)(xParticle + xRand), (double)yParticle, (double)(zParticle + zRand), 0.0D, 0.0D, 0.0D);
		}
	}
}
