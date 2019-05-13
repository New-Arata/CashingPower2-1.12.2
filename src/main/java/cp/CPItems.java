package cp;

import java.util.ArrayList;
import java.util.List;

import cp.items.ItemCashingAxe;
import cp.items.ItemCashingHoe;
import cp.items.ItemCashingPickaxe;
import cp.items.ItemCashingShovel;
import cp.items.ItemCashingSword;
import cp.items.ItemCashingWand;
import cp.items.ItemMPCoin;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class CPItems
{
	public static final List<Item> ITEMS = new ArrayList<Item>();

	//IRON(2, 250, 6.0F, 2.0F, 14),
	public static final ToolMaterial CASHING = EnumHelper.addToolMaterial("cashing", 2, 1, 6.0F, 2.0F, 14);
	public static final ToolMaterial RICH = EnumHelper.addToolMaterial("rich", 3, 1, 8.0F, 3.0F, 10);


	//item
	public static final Item MPCOIN = new ItemMPCoin("mp_coin");
	public static final Item CASHING_SWORD = new ItemCashingSword("cashing_sword", CASHING);
	public static final Item CASHING_SHOVEL = new ItemCashingShovel("cashing_shovel", CASHING);
	public static final Item CASHING_PICKAXE = new ItemCashingPickaxe("cashing_pickaxe", CASHING);
	public static final Item CASHING_AXE = new ItemCashingAxe("cashing_axe", CASHING);
	public static final Item CASHING_HOE = new ItemCashingHoe("cashing_hoe", CASHING);
	public static final Item CASHING_WAND = new ItemCashingWand("cashing_wand", CASHING, 3.0F);

	public static final Item RICH_WAND = new ItemCashingWand("rich_wand", RICH, 6.0F);

	//armor
}
