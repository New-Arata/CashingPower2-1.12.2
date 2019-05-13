package cp.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageKeyPressedHandler implements IMessageHandler<MessageKeyPressed, IMessage> {
	@Override
	public IMessage onMessage(MessageKeyPressed message, MessageContext ctx) {
		EntityPlayer entityPlayer = ctx.getServerHandler().player;
		ItemStack armorChest = entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack armorLegs = entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		World world = ctx.getServerHandler().player.world;
		//受け取ったMessageクラスのkey変数の数字をチャットに出力

		if (message.key == 1)
		{
		}

		return null;
	}
}