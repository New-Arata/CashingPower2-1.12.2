package cp.util.handlers;

import cp.CPBlocks;
import cp.CPItems;
import cp.CashingPower2;
import cp.entity.passive.EntityMPLich;
import cp.entity.projectile.EntityMPCoin;
import cp.util.IHasModel;
import cp.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class RegistryHandler
{
	public static int entityId;
	public static final ResourceLocation ENTITY_MPCOIN = prefix("mpcoin");

	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(CPItems.ITEMS.toArray(new Item[0]));
	}

	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(CPBlocks.BLOCKS.toArray(new Block[0]));
	}

	public static void onModelRegister(ModelRegistryEvent event)
	{
		CPItems.ITEMS.stream()
		.filter(item -> item instanceof IHasModel)
		.forEach(item -> ((IHasModel)item).registerModel());

		CPBlocks.BLOCKS.stream()
		.filter(block -> block instanceof IHasModel)
		.forEach(block -> ((IHasModel)block).registerModel());
	}

	public static ResourceLocation getKey(String key)
	{
		return new ResourceLocation(Reference.MOD_ID, key);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String key, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(getKey(key), entityClass, name, entityId++, CashingPower2.Instance, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String key, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int primaryColor, int secondaryColor)
	{
		EntityRegistry.registerModEntity(getKey(key), entityClass, name, entityId++, CashingPower2.Instance, trackingRange, updateFrequency, sendsVelocityUpdates, primaryColor, secondaryColor);
	}

	public static void registerMob(Class<? extends Entity> entityClass, String key, String name)
	{
		registerEntity(entityClass, key, name, 128, 3, true);
	}

	public static void registerMob(Class<? extends Entity> entityClass, String key, String name, int primaryColor, int secondaryColor)
	{
		registerEntity(entityClass, key, name, 128, 3, true, primaryColor, secondaryColor);

		EntitySpawnPlacementRegistry.setPlacementType(entityClass, SpawnPlacementType.ON_GROUND);
	}

	public static void registerEntities()
	{
		//mob
		registerMob(EntityMPLich.class, "mplich", "mplich", 0x2F2F2F, 0xFF0000);

		//projectile
		registerEntity(EntityMPCoin.class, "mp_coin", "MPCoin", 150, 5, true);
	}

	private static ResourceLocation prefix(String path)
	{
		return new ResourceLocation(Reference.MOD_ID, path);
	}
}
