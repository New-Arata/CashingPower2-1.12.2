package cp.items;

import cp.CPSoundEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import shift.mceconomy3.api.MCEconomyAPI;

public class ItemMPCoin extends ItemBase
{

	public ItemMPCoin(String name)
	{
		super(name);
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, CPSoundEvents.MPCOIN, SoundCategory.PLAYERS, 0.3F, 1.0F);
		MCEconomyAPI.addPlayerMP(playerIn, stack.getCount(), false);

		if (!playerIn.capabilities.isCreativeMode)
		{
			stack.shrink(stack.getCount());
		}

		playerIn.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
}
