package cp.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import cp.CPItems;
import cp.CashingPower2;
import cp.api.CashingPower2API;
import cp.util.ICashingTool;
import cp.util.IHasModel;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shift.mceconomy3.api.MCEconomyAPI;

public class ItemCashingSword extends ItemSword implements IHasModel, ICashingTool
{
	private final int useMP;
	private boolean canDestroy;

	public ItemCashingSword(String name, Item.ToolMaterial material)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CashingPower2API.tabsCP);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.useMP = material == CPItems.CASHING ? 5 : 25;

        CPItems.ITEMS.add(this);
	}
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	if(isSelected && entityIn instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entityIn;

    		this.canDestroy = ICashingTool.canUsingTool(player, stack, useMP);
    	}
    }

	@Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
		if(!this.canDestroy)
		{
			return true;
		}

		ICashingTool.checkPlayerMP(player, stack, ICashingTool.useCost(stack, useMP));

		if (MCEconomyAPI.getPlayerMP(player) <ICashingTool.useCost(stack, useMP) * 2)
		{
			player.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
		}

        return super.onLeftClickEntity(stack, player, entity);
    }

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{

		if(entityLiving instanceof EntityPlayer)
		{
			if(state.getBlock() instanceof BlockBush)
			{
				return true;
			}

			ICashingTool.checkPlayerMP((EntityPlayer) entityLiving, stack, ICashingTool.useCost(stack, useMP));

			if (MCEconomyAPI.getPlayerMP((EntityPlayer) entityLiving) < ICashingTool.useCost(stack, useMP) * 2)
			{
				entityLiving.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
			}
			return true;
		}
		return false;
	}

    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
    	if(state.getBlock() instanceof BlockBush)
    	{
    		return super.getDestroySpeed(stack, state);
    	}

        return this.canDestroy ? super.getDestroySpeed(stack, state) : 0.0F;
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return true;
    }

	@Override
	public void registerModel()
	{
		CashingPower2.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add(ChatFormatting.GOLD + "Cost: " + ICashingTool.useCost(stack, useMP) + "MP");
	}
}
