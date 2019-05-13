package cp.blocks;

import cp.CPSoundEvents;
import cp.CashingPower2;
import cp.tile.TileEntityShippingBox;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import shift.mceconomy3.api.MCEconomyAPI;

public class BlockShippingBox extends BlockBase
{


	private String ownerUUID;

	public BlockShippingBox(String name)
	{
		super(name, Material.WOOD, SoundType.WOOD);
		this.setHardness(0.9F);
	}
	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityShippingBox)
        {
        	TileEntityShippingBox tile = (TileEntityShippingBox) worldIn.getTileEntity(pos);

        	tile.setOwnerUUID(placer.getUniqueID().toString());
        }
    }

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityShippingBox)
        {
        	TileEntityShippingBox tile = (TileEntityShippingBox) worldIn.getTileEntity(pos);

			if (worldIn.isRemote)
			{
				return true;
			}
			else
			{
				tileentity.markDirty();
            	if(tile.getOwnerUUID().equals(playerIn.getUniqueID().toString()))
            	{
					if(!playerIn.isSneaking())
					{
						playerIn.openGui(CashingPower2.Instance, CashingPower2.guiShippingBox, worldIn, pos.getX(), pos.getY(), pos.getZ());
					}
					else
					{
		            	if(tile.getField(0) > 0)
		            	{
		            		worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, CPSoundEvents.MPCOIN, SoundCategory.PLAYERS, 0.3F, 1.0F);
		            		MCEconomyAPI.addPlayerMP(playerIn, tile.getField(0), false);
		            		tile.setField(0, 0);
		            	}
		            	tileentity.markDirty();
					}
            	}
            	else
            	{
            		playerIn.sendStatusMessage(new TextComponentTranslation("This shipping box isn't mine", new Object[0]), true);
            	}
	        }
		}
        return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityShippingBox)
		{
			TileEntityShippingBox tile = (TileEntityShippingBox)tileentity;

			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityShippingBox) tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityShippingBox();
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
}
