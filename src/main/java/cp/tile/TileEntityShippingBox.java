package cp.tile;

import java.util.ArrayList;

import javax.annotation.Nullable;

import cp.util.handlers.CPWorldTimeHandler;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import shift.mceconomy3.api.MCEconomyAPI;

public class TileEntityShippingBox extends TileEntity implements ITickable, ISidedInventory
{
	private static final int[] slots = new int[54];
	private String ownerUUID;
	private int purchaseMP;
	private int lastMP;

	private NonNullList<ItemStack> chestItemStacks = NonNullList.<ItemStack> withSize(54, ItemStack.EMPTY);

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.chestItemStacks = NonNullList.<ItemStack> withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.chestItemStacks);
		this.ownerUUID = compound.getString("ownerUUID");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, this.chestItemStacks);
		compound.setString("ownerUUID", this.ownerUUID);
		return compound;
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		this.writeToNBT(nbtTagCompound);
		return new SPacketUpdateTileEntity(pos, -50, nbtTagCompound);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void update()
    {
		if(CPWorldTimeHandler.getHour(this.getWorld()) == 6 && CPWorldTimeHandler.getMinute(this.getWorld()) == 0)
		{
            for (int i = 0; i < this.chestItemStacks.size(); ++i)
            {
                ItemStack itemstack = this.chestItemStacks.get(i);

                if(!itemstack.isEmpty() && MCEconomyAPI.getPurchase(itemstack) >= 0)
                {
                	this.setField(1, this.getField(1) + MCEconomyAPI.getPurchase(itemstack) * itemstack.getCount());

                	this.chestItemStacks.get(i).shrink(this.chestItemStacks.get(i).getCount());

                	this.markDirty();
                }
            }

            if(this.getField(1) > 0)
            {
                ItemStack fireworks = this.getFireworks();

                EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(world, this.getPos().getX() + 0.5F, this.getPos().getY() + 1.2F, this.getPos().getZ() + 0.5F, fireworks);
                if(!world.isRemote)
                {
                	world.spawnEntity(entityfireworkrocket);
                }

                this.setField(0, this.getField(0) + this.getField(1));

                this.setField(1, 0);
            }
		}

//		EntityPlayer entityplayer = this.world.getClosestPlayer((double)((float)this.pos.getX() + 0.5F), (double)((float)this.pos.getY() + 0.5F), (double)((float)this.pos.getZ() + 0.5F), 3.0D, false);
//
//		this.getEntityData().setInteger("targetMob", .nextInt(CPEventHook.getWorldMobSize()));
//
//		if(entityplayer != null)
//		{
//		    String s = I18n.format("entity." + EntityList.getTranslationName(CPEventHook.mobs.get().spawnedID) + ".name");
//		    int i = ((EntityPlayerMP)entityplayer).getStatFile().readStat(CPEventHook.mobs.get().killEntityStat);
//
//		    entityplayer.sendMessage(new TextComponentTranslation(s + "を倒した数: " + i, new Object[0]));
//		}
    }

	@Override
	public int getSizeInventory()
	{
		return this.chestItemStacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.chestItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.chestItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.chestItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = this.chestItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.chestItemStacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag)
		{
			this.markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
    {
        return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }
	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.chestItemStacks)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return slots;
	}
	static
    {
        for (int i = 0; i < slots.length; slots[i] = i++)
        {
            ;
        }
    }

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return false;
    }

	@Override
	public void openInventory(EntityPlayer player){}

	@Override
	public void closeInventory(EntityPlayer player){}

	@Override
	public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.purchaseMP;
            case 1:
            	return this.lastMP;
            default:
                return 0;
        }
    }

	@Override
	 public void setField(int id, int value)
    {
		switch(id)
		{
			case 0:
				this.purchaseMP = value;
				break;
			case 1:
				this.lastMP = value;
            default: break;
		}
    }

	@Override
	public int getFieldCount()
    {
        return 2;
    }

	public void setOwnerUUID(String owner)
	{
		this.ownerUUID = owner;
	}

	public String getOwnerUUID()
	{
		return this.ownerUUID;
	}

	@Override
	public void clear()
	{
		this.chestItemStacks.clear();
	}

	@Override
	public String getName()
	{
		return "gui.shippingbox";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}


    private ItemStack getFireworks() {

        ItemStack fireworks = new ItemStack(Items.FIREWORKS);

        NBTTagCompound nbttagcompound = new NBTTagCompound();//Explosion

        ArrayList arraylist = new ArrayList();
        arraylist.add(Integer.valueOf(ItemDye.DYE_COLORS[11]));
        arraylist.add(Integer.valueOf(ItemDye.DYE_COLORS[11]));
        arraylist.add(Integer.valueOf(ItemDye.DYE_COLORS[14]));
        int[] aint1 = new int[arraylist.size()];

        for (int l2 = 0; l2 < aint1.length; ++l2) {
            aint1[l2] = ((Integer) arraylist.get(l2)).intValue();
        }

        if (CPWorldTimeHandler.getHour(this.getWorld()) == 6) {
            nbttagcompound.setBoolean("Flicker", true);
        } else {
            nbttagcompound.setBoolean("Trail", true);
        }

        nbttagcompound.setIntArray("Colors", aint1);
        nbttagcompound.setByte("Type", (byte) 0);

        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        NBTTagCompound nbttagcompound2 = new NBTTagCompound();
        NBTTagList nbttaglist = new NBTTagList();

        nbttaglist.appendTag(nbttagcompound);

        nbttagcompound1.setTag("Explosions", nbttaglist);
        nbttagcompound1.setByte("Flight", (byte) 1);
        nbttagcompound2.setTag("Fireworks", nbttagcompound1);

        fireworks.setTagCompound(nbttagcompound2);

        return fireworks;

    }
}