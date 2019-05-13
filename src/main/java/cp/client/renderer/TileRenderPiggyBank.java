package cp.client.renderer;

import cp.blocks.BlockPiggyBank;
import cp.tile.TileEntityPiggyBank;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileRenderPiggyBank extends TileEntitySpecialRenderer<TileEntityPiggyBank>
{
    private static final ResourceLocation pig = new ResourceLocation("textures/entity/pig/pig.png");
    private ResourceLocation texture;

    private final ModelPig model;

    public TileRenderPiggyBank(ModelPig modelIn)
    {
        this.model = modelIn;
    }

    public void render(TileEntityPiggyBank tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        //EnumFacing enumfacing = EnumFacing.UP;

        if (tile.hasWorld())
        {
            IBlockState iblockstate = this.getWorld().getBlockState(tile.getPos());

            if (iblockstate.getBlock() instanceof BlockPiggyBank)
            {
                //enumfacing = (EnumFacing)iblockstate.getValue(BlockPiggyBank.FACING);
            }
        }

        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        GlStateManager.disableCull();

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            this.bindTexture(pig);
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();

        if (destroyStage < 0)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        }

        GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.0F, 1.0F, 0.0F);
        float f = 0.9995F;
        GlStateManager.scale(0.9995F, 0.9995F, 0.9995F);
        GlStateManager.translate(0.0F, -1.0F, 0.0F);

		BlockPiggyBank block = (BlockPiggyBank) this.getWorld().getBlockState(tile.getPos()).getBlock();

		IBlockState blockState = getWorld().getBlockState(tile.getPos());


        int meta = block.getMetaFromState(blockState);

        switch(meta)
        {
        case 0:
        	GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
        	break;
        case 1:
        	GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        	break;
        case 2:
        	GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        	break;
        case 3:
        	GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
        	break;
        }

        GlStateManager.scale(0.49625F, 0.49625F, 0.49625F);
        GlStateManager.translate(0.0F, 1.5F, 0.0F);
        this.model.head.render(0.0625F);
        GlStateManager.rotate(90F, 1, 0, 0);
        GlStateManager.translate(-0.0F, -0.5625F, -0.8125F);
        this.model.body.render(0.0625F);
        GlStateManager.translate(-0.0F, 0.5625F, 0.8125F);
        GlStateManager.rotate(-90F, 1, 0, 0);

        this.model.leg1.render(0.0625F);
        this.model.leg2.render(0.0625F);
        this.model.leg3.render(0.0625F);
        this.model.leg4.render(0.0625F);

        GlStateManager.enableCull();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
