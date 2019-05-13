package cp.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeyEvent
{
	public static int modeV;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onUPEvent(LivingUpdateEvent event)
	{
		EntityLivingBase target = event.getEntityLiving();
		ItemStack armorChest = target.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack armorLegs = target.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		if(target instanceof EntityPlayer)
		{
			if(target != null)
			{
				EntityPlayer player = (EntityPlayer)target;

				if(armorChest != null)
				{
					//PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(1));
				}
			}
		}

	}
}
