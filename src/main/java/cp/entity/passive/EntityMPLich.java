package cp.entity.passive;

import javax.annotation.Nullable;

import cp.CPItems;
import cp.event.CPEventHook;
import cp.util.Reference;
import cp.util.handlers.CPWorldTimeHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityMPLich extends AbstractSkeleton
{
	public static final ResourceLocation LOOT_TABLE = new ResourceLocation(Reference.MOD_ID, "entities/lich");

	public EntityMPLich(World worldIn)
	{
		super(worldIn);
		this.isImmuneToFire = true;

		if(CPEventHook.mobs != null)
		{
			this.getEntityData().setInteger("targetMob", this.rand.nextInt(CPEventHook.getWorldMobSize()));
			this.getEntityData().setBoolean("targetDaily", true);
		}
	}

	public static void registerFixesLich(DataFixer fixer)
	{
		EntityLiving.registerFixesMob(fixer, EntityMPLich.class);
	}

    protected boolean shouldBurnInDay()
    {
        return false;
    }

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45000001788139344D);
	}

	@Override
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    }

	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_STRAY_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_STRAY_HURT;
	}

	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_STRAY_DEATH;
	}

	protected SoundEvent getStepSound()
	{
		return SoundEvents.ENTITY_STRAY_STEP;
	}

	@Nullable
	protected ResourceLocation getLootTable()
	{
		return LOOT_TABLE;
	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 */
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
		ItemStack itemstack = new ItemStack(CPItems.RICH_WAND);
		this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, itemstack);
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(CPWorldTimeHandler.getHour(this.getEntityWorld()) == 6 && CPWorldTimeHandler.getMinute(this.getEntityWorld()) == 0 && this.ticksExisted % 10 == 0)
		{
			this.getEntityData().setInteger("targetMob", this.rand.nextInt(CPEventHook.getWorldMobSize()));
			this.getEntityData().setBoolean("targetDaily", true);
		}

	}

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (!this.world.isRemote)
        {
			if(player instanceof EntityPlayerMP)
			{
						if(player != null)
						{
							String s = I18n.format("entity." + EntityList.getTranslationName(CPEventHook.mobs.get(this.getEntityData().getInteger("targetMob")).spawnedID) + ".name");
							int i = ((EntityPlayerMP)player).getStatFile().readStat(CPEventHook.mobs.get(this.getEntityData().getInteger("targetMob")).killEntityStat);

							if(this.getEntityData().getBoolean("targetDaily"))
							{
							    if(this.getEntityData().getInteger(player.getUniqueID().toString() + s) + 3 <= i)
							    {
							    	player.sendMessage(new TextComponentTranslation("<" + this.getName() + "> "+ s + "を退治してくれたんだね、ありがとう！", new Object[0]));
							    	this.getEntityData().setInteger(player.getUniqueID().toString() + s, i);
							    	this.getEntityData().setBoolean("targetDaily", false);
							    }
							    else
							    {
							    	player.sendMessage(new TextComponentTranslation("<" + this.getName() + "> "+ s + "をあと" + (this.getEntityData().getInteger(player.getUniqueID().toString() + s) + 3 - i) + "体退治してくれないか？", new Object[0]));
							    }
							}
							else
							{
								player.sendMessage(new TextComponentTranslation("<" + this.getName() + "> "+ "今日はありがとう、また明日頼むよ", new Object[0]));
							}
						}
		    }
        }
        return true;
    }

	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
	}

	/**
	 * Enchants Entity's current equipments based on given DifficultyInstance
	 */
	protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
	}
}