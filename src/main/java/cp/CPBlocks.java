package cp;

import java.util.ArrayList;
import java.util.List;

import cp.blocks.BlockPiggyBank;
import cp.blocks.BlockShippingBox;
import net.minecraft.block.Block;

public class CPBlocks
{
	public static List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block SHIPPING_BOX = new BlockShippingBox("shipping_box");
	public static final Block PIGGY_BANK = new BlockPiggyBank("piggy_bank");
}
