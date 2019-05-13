package cp.client.renderer.entity;

import cp.client.renderer.entity.layer.LayerLichClothing;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLich extends RenderSkeleton
{
	private static final ResourceLocation LICH_TEXTURE = new ResourceLocation("cp", "textures/entities/lich.png");

	public RenderLich(RenderManager manager)
	{
		super(manager);
		this.addLayer(new LayerLichClothing(this));
	}

	@Override
	protected void preRenderCallback(AbstractSkeleton entity, float ticks)
	{
		GlStateManager.scale(1.1F, 1.1F, 1.1F);
	}

	@Override
	protected ResourceLocation getEntityTexture(AbstractSkeleton entity)
	{
		return LICH_TEXTURE;
	}
}