package cp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.mojang.authlib.GameProfile;

import cp.CPEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import shift.mceconomy3.api.MCEconomyAPI;

public interface ICashingTool
{
	public static void checkPlayerMP(EntityPlayer entityPlayer, ItemStack stack, int useMP)
	{
		int hasMP = MCEconomyAPI.getPlayerMP(entityPlayer);
		if (canPlayerMP(entityPlayer, stack, useMP))
		{
			MCEconomyAPI.reducePlayerMP(entityPlayer, useMP, false);
		}
	}

	public static boolean canPlayerMP(EntityPlayer entityPlayer, ItemStack stack, int useMP)
	{
		int hasMP = MCEconomyAPI.getPlayerMP(entityPlayer);
		return (hasMP >= useMP);
	}

	public static boolean canUsingTool(EntityPlayer player, ItemStack stack, int useMP)
	{
		return canPlayerMP(player, stack, useCost(stack, useMP));
	}

	public static int useCost(ItemStack stack, int useMP)
	{
		int enchantLevel = EnchantmentHelper.getEnchantmentLevel(CPEnchantments.ECONOMIZE, stack);
		return (int)MathHelper.absMax(1, useMP - (useMP * (enchantLevel * 0.16)));
	}

	@Nonnull
	public static String getLastKnownUsername(UUID uuid)
	{
		List<UUID> warnedFails = new ArrayList<>();
		String ret = UsernameCache.getLastKnownUsername(uuid);

		if(ret == null && !warnedFails.contains(uuid) && FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{ // see if MC/Yggdrasil knows about it?!
			GameProfile gp = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getProfileByUUID(uuid);

			if(gp != null)
			{
				ret = gp.getName();
			}
		}

		if(ret == null && !warnedFails.contains(uuid))
		{
			warnedFails.add(uuid);
		}

		return ret != null ? ret : "<???>";
	}
}
