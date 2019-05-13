package cp.tile;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPiggyBank extends TileEntity
{
	private int purchaseMP;

    public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.purchaseMP = compound.getInteger("purchaseMP");
	}

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("purchaseMP", this.purchaseMP);
		return compound;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}


	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		this.writeToNBT(nbtTagCompound);
		return new SPacketUpdateTileEntity(pos, 11, nbtTagCompound);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	public int getPurchaseMP()
	{
		return this.purchaseMP;
	}

	public void setPurchaseMP(int mp)
	{
		this.purchaseMP = mp;
	}
}