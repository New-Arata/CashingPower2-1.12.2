package cp.event;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import cp.CPAdvancements;
import cp.CPEnchantments;
import cp.CPItems;
import cp.CPSoundEvents;
import cp.blocks.BlockPiggyBank;
import cp.tile.TileEntityPiggyBank;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import shift.mceconomy3.api.MCEconomyAPI;

public class CPEventHook
{
	@SubscribeEvent
	public void CPPlayerTickEvent(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		World world = player.world;
		if (!world.isRemote)
		{
			if(MCEconomyAPI.getPlayerMP(player) > 0)
			{
				CPAdvancements.HAS_PLAYER_MP_1.trigger((EntityPlayerMP) player);
			}
			if(MCEconomyAPI.getPlayerMP(player) >= 1000000)
			{
				CPAdvancements.HAS_PLAYER_MP_2.trigger((EntityPlayerMP) player);
			}
			if(MCEconomyAPI.getPlayerMP(player) >= 1000000000)
			{
				CPAdvancements.HAS_PLAYER_MP_3.trigger((EntityPlayerMP) player);
			}

			return;
		}
	}

	@SubscribeEvent
	public void onBlockBreak(BreakEvent event)
	{
		EntityPlayer entityPlayer = event.getPlayer();

		if (entityPlayer == null || !(entityPlayer instanceof EntityPlayerMP))
		{
			return;
		}

		EntityPlayerMP player = (EntityPlayerMP) entityPlayer;
		IBlockState state = event.getState();
		if (state.getBlock() instanceof BlockPiggyBank)
		{
			World world = event.getWorld();
			BlockPos pos = event.getPos();
			TileEntityPiggyBank tile = (TileEntityPiggyBank) world.getTileEntity(pos);

			world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PIG_DEATH, SoundCategory.BLOCKS, 1.0F, 1.0F);

			if(tile.getPurchaseMP() > 0)
			{
				world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, CPSoundEvents.MPCOIN, SoundCategory.PLAYERS, 0.3F, 1.0F);
				MCEconomyAPI.addPlayerMP(player, tile.getPurchaseMP(), false);
			}
		}
	}

	@SubscribeEvent
	public void CPPickupEvent(EntityItemPickupEvent event)
	{
			EntityPlayer player = event.getEntityPlayer();
			World world = player.world;
			ItemStack stack = event.getItem().getItem();

			if (stack != null && stack.getItem() == CPItems.MPCOIN)
			{
				world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, CPSoundEvents.MPCOIN, SoundCategory.PLAYERS, 0.3F, 1.0F);
				MCEconomyAPI.addPlayerMP(player, stack.getCount(), false);

				event.getItem().setDead();
				event.setCanceled(true);
			}
	}

	@SubscribeEvent
	public void CPHurtEvent(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		World world = entity.world;

		if(entity != null && !(entity instanceof EntityPlayer))
		{
			if(event.getSource().getTrueSource() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

				Random rand = new Random();
				int i = CPEnchantments.getSecretSavingsModifier(player);
				int j = 0;
				if(entity.getCreatureAttribute() == EnumCreatureAttribute.ILLAGER)
				{
					if(rand.nextInt(5) == 0 && !world.isRemote)
					{
						entity.entityDropItem(new ItemStack(Items.EMERALD, 1), 1);
					}
				}
				else if(entity instanceof EntityVillager)
				{
					if(rand.nextInt(20) == 0 && !world.isRemote)
					{
						entity.entityDropItem(new ItemStack(Items.EMERALD, 1), 1);
					}
				}
				else if(i > 0 && rand.nextInt(2) == 0)
				{
					if(entity instanceof IMob)
					{
						while(j + rand.nextInt(5) > 0)
						{
							if(!world.isRemote)
							{
								entity.entityDropItem(new ItemStack(CPItems.MPCOIN), 1);
								world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, CPSoundEvents.MPCOIN, SoundCategory.PLAYERS, 0.1F, 1.0F);
							}

							j--;
						}
					}
				}
			}
		}
	}

	public static List<EntityList.EntityEggInfo> mobs = Lists.<EntityList.EntityEggInfo>newArrayList();

	/** 一部を除いた全Mobのリストを取得する */
	@SubscribeEvent
	public void getWorldMobList(Load load)
	{
		World worldIn = load.getWorld();

        for (EntityEggInfo entitylist$entityegginfo : EntityList.ENTITY_EGGS.values())
        {
        	EntityEntry entry = net.minecraftforge.fml.common.registry.ForgeRegistries.ENTITIES.getValue(entitylist$entityegginfo.spawnedID);

	        Entity entity = entry.newInstance(worldIn);

	        if(entity instanceof IMob && entity.isNonBoss() && !(entity instanceof AbstractIllager) && !(entity instanceof EntityVex) && !(entity instanceof EntityGolem) && !(entity instanceof EntityGuardian))
	        {
	        	mobs.add(entitylist$entityegginfo);
	        }
        }
	}

	public static int getWorldMobSize()
	{
		return mobs.size() -1;
	}
}
