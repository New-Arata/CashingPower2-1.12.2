package cp.entity.projectile;

import net.minecraft.block.BlockBush;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMPCoin extends EntityThrowable
{
	private int livingTick;
	private int knockbackStrength;
	private float damage;
	private boolean setFlame;

	public EntityMPCoin(World worldIn)
	{
		super(worldIn);
	}

	public EntityMPCoin(World worldIn, EntityLivingBase throwerIn, float damageIn, int punchIn, boolean setFlame)
	{
		super(worldIn, throwerIn);
		this.damage = damageIn;
		this.knockbackStrength = punchIn;
		this.setFlame = setFlame;
		shoot(throwerIn, throwerIn.rotationPitch, throwerIn.rotationYaw, 0.0F, 0.5F, 0.5F);
	}

	public EntityMPCoin(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public static void registerFixesSnowball(DataFixer fixer)
	{
		EntityThrowable.registerFixesThrowable(fixer, "MPCoin");
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (!this.world.isRemote && (this.world.getDifficulty() == EnumDifficulty.PEACEFUL || this.livingTick > 200))
		{
			this.setDead();
			this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
		}
		if (livingTick % 1 == 0)
			makeTrail();
		this.livingTick++;
		if(this.setFlame)
		{
			this.setFire(1);
		}
	}

	private void makeTrail()
	{
		if (this.thrower instanceof EntityPlayer)
		{
			this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX - this.motionX + (rand.nextDouble() / 5),
					this.posY - this.motionY + 0.15D, this.posZ - this.motionZ + (rand.nextDouble() / 5),
					0.7D + (rand.nextFloat() / 5), 0.1D + (rand.nextDouble() / 5), 0.4D + (rand.nextDouble() / 1));
		} else
		{
			this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX - this.motionX + (rand.nextDouble() / 5),
					this.posY - this.motionY + 0.15D, this.posZ - this.motionZ + (rand.nextDouble() / 5),
					0.8D + (rand.nextFloat() / 5), 0.8D + (rand.nextDouble() / 5), 0.3D + (rand.nextDouble() / 5));
		}
	}

	@Override
	protected float getGravityVelocity()
	{
		return 0.0F;
	}

	@Override
    public boolean isInWater()
    {
        return false;
    }


	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result)
	{
		if(result.entityHit == null)
		{

			BlockPos blockpos = result.getBlockPos();
            if (this.world.getBlockState(blockpos).getBlock() instanceof BlockBush)
            {
            	return;
            }
		}


		this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);

		if (!this.world.isRemote)
		{
			if (result.entityHit != null && result.entityHit != this.thrower)
			{
				result.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), damage);
				if (this.knockbackStrength > 0)
				{
					float sqrt = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

					if (sqrt > 0.0F && result.entityHit instanceof EntityLivingBase)
					{
						result.entityHit.addVelocity(this.motionX * (double) this.knockbackStrength * 0.6D / (double) sqrt, 0.1D, this.motionZ * (double) this.knockbackStrength * 0.6D / (double) sqrt);
					}
				}
				if(this.setFlame)
				{
					result.entityHit.setFire(5);
				}
			}

			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}
	}
}