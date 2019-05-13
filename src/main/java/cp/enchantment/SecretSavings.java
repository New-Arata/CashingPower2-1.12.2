package cp.enchantment;

import cp.CPEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.util.EnumHelper;

public class SecretSavings extends Enchantment
{
    public SecretSavings()
    {
        /*    貴重さ        , 付与できるアイテムの種別  , 付与できるアイテムの入るスロット*/
        super(Rarity.RARE, EnumHelper.addEnchantmentType("cashing", CPEnchantments.cashingTool), new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        setName("secret_savings");/*内部名の指定*/
    }

    @Override
	public int getMaxLevel()
	{
		return 1;
	}

}