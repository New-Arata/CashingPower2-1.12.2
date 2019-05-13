package cp;

import com.google.common.base.Predicate;

import cp.enchantment.Economize;
import cp.enchantment.SecretSavings;
import cp.util.ICashingTool;
import cp.util.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CPEnchantments
{
	public static Enchantment ECONOMIZE;
	public static Enchantment SECRET_SAVINGS;


	public static Predicate<Item> cashingTool = new Predicate<Item>()
	{
		@Override public boolean apply(Item item)
		{
			return item instanceof ICashingTool;
		}
	};

	public static void init()
	{
		ECONOMIZE = new Economize();
		ForgeRegistries.ENCHANTMENTS.register(ECONOMIZE.setRegistryName(Reference.MOD_ID, "economize"));

		SECRET_SAVINGS = new SecretSavings();
		ForgeRegistries.ENCHANTMENTS.register(SECRET_SAVINGS.setRegistryName(Reference.MOD_ID, "secret_savings"));
	}

    public static int getSecretSavingsModifier(EntityLivingBase player)
    {
        return EnchantmentHelper.getMaxEnchantmentLevel(CPEnchantments.SECRET_SAVINGS, player);
    }

}
