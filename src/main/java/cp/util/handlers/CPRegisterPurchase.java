package cp.util.handlers;

import cp.CPBlocks;
import cp.CPItems;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.item.ItemStack;
import shift.mceconomy3.api.MCEconomyAPI;

public class CPRegisterPurchase
{
	public static void init()
	{
	    /**
	     * Minecraft
	     */
        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntityHusk.class, 4);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntityStray.class, 7);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntityCreeper.class, 6);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntityEndermite.class, 40);

        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntityGuardian.class, 32);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntitySlime.class, 1);

        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntityMagmaCube.class, 2);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntityWitherSkeleton.class, 8);

        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntityShulker.class, 40);

        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntityVindicator.class, 64);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntityEvoker.class, 600);

        MCEconomyAPI.SHOP_MANAGER.addPurchaseEntity(EntityElderGuardian.class, 1200);

        /**
         * Cashing Power2
         */

        //Items
        MCEconomyAPI.SHOP_MANAGER.addPurchaseItem(new ItemStack(CPItems.CASHING_SWORD), -1);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseItem(new ItemStack(CPItems.CASHING_SHOVEL), -1);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseItem(new ItemStack(CPItems.CASHING_PICKAXE), -1);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseItem(new ItemStack(CPItems.CASHING_AXE), -1);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseItem(new ItemStack(CPItems.CASHING_HOE), -1);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseItem(new ItemStack(CPItems.CASHING_WAND), -1);
        MCEconomyAPI.SHOP_MANAGER.addPurchaseItem(new ItemStack(CPItems.MPCOIN), 1);

        //Blocks
        MCEconomyAPI.SHOP_MANAGER.addPurchaseItem(new ItemStack(CPBlocks.SHIPPING_BOX), 0);
	}
}
