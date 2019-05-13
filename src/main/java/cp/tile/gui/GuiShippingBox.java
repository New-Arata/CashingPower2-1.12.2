package cp.tile.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.mojang.authlib.GameProfile;

import cp.tile.TileEntityShippingBox;
import cp.tile.container.ContainerShippingBox;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class GuiShippingBox extends GuiContainer {
	private final static ResourceLocation GUI_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");

	private final TileEntityShippingBox tileEntity;
	private static EntityPlayer player;
	private static final List<UUID> warnedFails = new ArrayList<>();

	public GuiShippingBox(EntityPlayer inventory, TileEntityShippingBox tileEntity)
	{
		super(new ContainerShippingBox(inventory, tileEntity));
		this.player = inventory;
		this.tileEntity = tileEntity;
		this.xSize = 222;
		this.ySize = 228;

	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		NBTTagCompound compound = tileEntity.getUpdateTag();

		UUID ownerUuid = UUID.fromString(compound.getString("ownerUUID"));

		String name = this.getLastKnownUsername(ownerUuid);

		if(player.getUniqueID().toString().equals(tileEntity.getOwnerUUID()))
		{
			this.fontRenderer.drawString(I18n.format(this.tileEntity.getName(), new Object[0]).trim(), 8, 6, 0x404040);
		}
		else
		{
			this.fontRenderer.drawString(name + I18n.format("gui.private", new Object[0]) + I18n.format(this.tileEntity.getName(), new Object[0]), 8, 6, 0x404040);
		}


		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, 128, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int xMouse, int yMouse)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

	}

	@Nonnull
	public static String getLastKnownUsername(UUID uuid)
	{
		String ret = UsernameCache.getLastKnownUsername(uuid);

		if(ret == null && !warnedFails.contains(uuid) && FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{ // see if MC/Yggdrasil knows about it?!
			GameProfile gp = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getProfileByUUID(uuid);

			if(gp != null)
			{
				ret = gp.getName();
			}
		}

		if(ret == null && !warnedFails.contains(uuid))
		{
			warnedFails.add(uuid);
		}

		return ret != null ? ret : "<???>";
	}
}