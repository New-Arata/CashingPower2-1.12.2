package cp;

import cp.api.CashingPower2API;
import cp.common.PacketHandler;
import cp.event.CPEventHook;
import cp.event.KeyEvent;
import cp.tile.TileEntityPiggyBank;
import cp.tile.TileEntityShippingBox;
import cp.util.Reference;
import cp.util.handlers.CPRegisterPurchase;
import cp.util.handlers.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, name = Reference.NAME, guiFactory = "cp.client.gui.CPGuiFactory")
public class CashingPower2
{

	@Mod.Instance(Reference.MOD_ID)
	public static CashingPower2 Instance;

	@SidedProxy(modId = Reference.MOD_ID, clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final CreativeTabs tabsCP = CashingPower2API.tabsCP = new CPCreativeTabs("CashingPower2");

	public static final int guiShippingBox = 0;

	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		RegistryHandler.onItemRegister(event);
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		RegistryHandler.onBlockRegister(event);
	}

	@SubscribeEvent
	public void registerEntityEntries(RegistryEvent.Register<EntityEntry> event)
	{
		RegistryHandler.registerEntities();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void regiserModels(ModelRegistryEvent event)
	{
		RegistryHandler.onModelRegister(event);
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		CPEnchantments.init();
		CPConfig.loadConfig();
		CPConfig.syncConfig();
		PacketHandler.init();
		if (event.getSide().isClient())
		{
			proxy.loadEntity();
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{

		CPEntities.init();
		CPAdvancements.init();
		CPRegisterPurchase.init();
		GameRegistry.registerTileEntity(TileEntityShippingBox.class, TileEntityShippingBox.class.getName());
		GameRegistry.registerTileEntity(TileEntityPiggyBank.class, TileEntityPiggyBank.class.getName());
		proxy.registerTileEntity();

		proxy.registerClientInformation();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());

		MinecraftForge.EVENT_BUS.register(new CPEventHook());
		MinecraftForge.EVENT_BUS.register(new CPConfig());
		MinecraftForge.EVENT_BUS.register(new KeyEvent());
		MinecraftForge.EVENT_BUS.register(new CashingPower2API());
		if (event.getSide().isClient())
		{
			//MinecraftForge.EVENT_BUS.register(new KCRenderEvent());
		}
	}
}
