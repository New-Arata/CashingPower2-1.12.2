package cp.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntitySimpleVehicleBase extends Entity
{

	public EntitySimpleVehicleBase(World worldIn)
	{
		super(worldIn);
	}

	/** Entity **/
	@Override
	protected void entityInit()
	{

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
	}

	@Override
	public void setInWeb()
	{}

	@Override
	public boolean shouldDismountInWater(Entity rider)
	{
		return false;
	}

	@Override
	@Nullable
	public Entity getControllingPassenger()
	{
		return this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
	}

	@Override
	public boolean canPassengerSteer()
	{
		Entity entity = this.getControllingPassenger();
		return entity instanceof EntityPlayer ? ((EntityPlayer) entity).isUser() : !this.world.isRemote;
	}
}
