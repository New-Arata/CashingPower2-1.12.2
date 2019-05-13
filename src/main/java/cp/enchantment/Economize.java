package cp.enchantment;

import cp.CPEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.util.EnumHelper;

public class Economize extends Enchantment
{
    public Economize()
    {
        /*    貴重さ        , 付与できるアイテムの種別  , 付与できるアイテムの入るスロット*/
        super(Rarity.UNCOMMON, EnumHelper.addEnchantmentType("cashing", CPEnchantments.cashingTool), new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        setName("economize");/*内部名の指定*/
    }

    @Override
	public int getMaxLevel()
	{
		return 2;
	}

}