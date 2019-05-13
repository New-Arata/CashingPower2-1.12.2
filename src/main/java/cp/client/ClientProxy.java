package cp.client;

import cp.CPItems;
import cp.CommonProxy;
import cp.client.renderer.TileRenderPiggyBank;
import cp.client.renderer.entity.RenderLich;
import cp.entity.passive.EntityMPLich;
import cp.entity.projectile.EntityMPCoin;
import cp.tile.TileEntityPiggyBank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	@Override
	public void registerTileEntity()
	{
		ClientRegistry.registerTileEntity(TileEntityPiggyBank.class, TileEntityPiggyBank.class.getName(), new TileRenderPiggyBank(new ModelPig()));
	}

	@Override
	public void loadEntity()
	{
		//mob
		RenderingRegistry.registerEntityRenderingHandler(EntityMPLich.class, RenderLich::new);

		//projectile
		RenderingRegistry.registerEntityRenderingHandler(EntityMPCoin.class, m -> new RenderSnowball<>(m, CPItems.MPCOIN, Minecraft.getMinecraft().getRenderItem()));
	}

	@Override
    public void registerClientInformation()
	{
    }

}