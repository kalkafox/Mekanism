package mekanism.client;

import org.lwjgl.opengl.GL11;

import mekanism.common.ContainerGasTank;
import mekanism.common.TileEntityGasTank;
import net.minecraft.src.*;

public class GuiGasTank extends GuiContainer
{
	private TileEntityGasTank tileEntity;
	
	private int guiWidth;
	private int guiHeight;
	
	public GuiGasTank(InventoryPlayer inventory, TileEntityGasTank tentity)
	{
		super(new ContainerGasTank(inventory, tentity));
		tileEntity = tentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String capacityInfo = tileEntity.gasStored + "/" + tileEntity.MAX_GAS;
		fontRenderer.drawString("Gas Tank", 43, 6, 0x404040);
		fontRenderer.drawString(capacityInfo, 45, 40, 0x404040);
		fontRenderer.drawString("Gas: " + tileEntity.gasType.name, 45, 49, 0x404040);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 0x404040);
	}
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int texture = mc.renderEngine.getTexture("/resources/mekanism/gui/GuiGasTank.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(texture);
        
        guiWidth = (width - xSize) / 2;
        guiHeight = (height - ySize) / 2;
        drawTexturedModalRect(guiWidth, guiHeight, 0, 0, xSize, ySize);
        int scale = (int)(((double)tileEntity.gasStored / tileEntity.MAX_GAS) * 72);
        drawTexturedModalRect(guiWidth + 65, guiHeight + 17, 176, 0, scale, 20);
    }
}
