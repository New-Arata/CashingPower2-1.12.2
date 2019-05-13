package cp.items;

import cp.CPItems;
import cp.CashingPower2;
import cp.api.CashingPower2API;
import cp.util.IHasModel;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel
{
	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CashingPower2API.tabsCP);

		CPItems.ITEMS.add(this);
	}

	@Override
	public void registerModel()
	{
		CashingPower2.proxy.registerItemRenderer(this, 0, "inventory");
	}
}