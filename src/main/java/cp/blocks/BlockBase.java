package cp.blocks;

import cp.CPBlocks;
import cp.CPItems;
import cp.CashingPower2;
import cp.api.CashingPower2API;
import cp.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;


public class BlockBase extends Block implements IHasModel
{
	public BlockBase(String name, Material material, SoundType sound)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setSoundType(sound);
		this.setCreativeTab(CashingPower2API.tabsCP);

		CPBlocks.BLOCKS.add(this);
		CPItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModel()
	{
		CashingPower2.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "Inventory");
	}
}
