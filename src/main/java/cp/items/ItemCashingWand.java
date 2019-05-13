package cp.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import cp.CPItems;
import cp.CashingPower2;
import cp.api.CashingPower2API;
import cp.entity.projectile.EntityMPCoin;
import cp.util.ICashingTool;
import cp.util.IHasModel;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shift.mceconomy3.api.MCEconomyAPI;

public class ItemCashingWand extends ItemBow implements IHasModel, ICashingTool
{
	private final int useMP;
	private final float bulletDamage;
	private boolean canDestroy;

	public ItemCashingWand(String name, Item.ToolMaterial material, float bulletDamageIn)
	{
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CashingPower2API.tabsCP);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.useMP = material == CPItems.CASHING ? 10 : 30;
        this.bulletDamage = bulletDamageIn;

        CPItems.ITEMS.add(this);
	}

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	if(isSelected && entityIn instanceof EntityPlayer && entityIn instanceof EntityPlayerMP)
    	{
    		EntityPlayer player = (EntityPlayer)entityIn;

    		this.canDestroy = ICashingTool.canUsingTool(player, stack, useMP);
    	}
    }

	/**
	 * Called when the equipped item is right clicked.
	 */
    @Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		if(!this.canDestroy && !playerIn.capabilities.isCreativeMode) {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		}

		worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.NEUTRAL, 0.5F, (itemRand.nextFloat() * 0.2F + 1.0F));

		if(EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) == 0)
		{
			playerIn.getCooldownTracker().setCooldown(this, 10);
		}

		if(!playerIn.capabilities.isCreativeMode) {
			MCEconomyAPI.reducePlayerMP(playerIn, ICashingTool.useCost(stack, useMP), false);

			if (MCEconomyAPI.getPlayerMP(playerIn) < ICashingTool.useCost(stack, useMP) * 2)
			{
				playerIn.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
			}
		}
		if (!worldIn.isRemote)
		{
			int power =  EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
			int punch = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
			boolean flame = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0;
			worldIn.spawnEntity(new EntityMPCoin(worldIn, playerIn, this.bulletDamage + (0.5F * (power + 1)), punch, flame));
		}

		playerIn.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
		if(!this.canDestroy)
		{
			return true;
		}

		ICashingTool.checkPlayerMP(player, stack, ICashingTool.useCost(stack, useMP));

		if (MCEconomyAPI.getPlayerMP(player) < ICashingTool.useCost(stack, useMP) * 2)
		{
			player.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
		}

        return super.onLeftClickEntity(stack, player, entity);
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
