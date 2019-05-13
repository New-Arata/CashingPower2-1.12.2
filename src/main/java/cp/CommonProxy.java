package cp;

import cp.tile.TileEntityShippingBox;
import cp.tile.container.ContainerShippingBox;
import cp.tile.gui.GuiShippingBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	public void registerItemRenderer(Item item, int meta, String id){}

	public void loadEntity(){}

	public void registerTileEntity()
	{
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);

		if(!world.isBlockLoaded(pos))return null;
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof TileEntityShippingBox)
		{
			return new ContainerShippingBox(player, (TileEntityShippingBox) tile);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);

		if (!world.isBlockLoaded(pos))
			return null;
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityShippingBox)
		{
			return new GuiShippingBox(player, (TileEntityShippingBox) tile);
		}
		return null;
	}

	public void registerClientInformation() {}
}