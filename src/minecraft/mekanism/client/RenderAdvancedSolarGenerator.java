package mekanism.client;

import mekanism.common.TileEntityAdvancedSolarGenerator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;

import org.lwjgl.opengl.GL11;

public class RenderAdvancedSolarGenerator extends TileEntitySpecialRenderer
{
	private ModelAdvancedSolarGenerator arrayModel;
	
	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8)
	{
		renderAModelAt((TileEntityAdvancedSolarGenerator)var1, var2, var4, var6, 1F);
	}
	
	public RenderAdvancedSolarGenerator(ModelAdvancedSolarGenerator model)
	{
		arrayModel = model;
	}

	private void renderAModelAt(TileEntityAdvancedSolarGenerator tileEntity, double x, double y, double z, float f)
	{	    
	    GL11.glPushMatrix();
	    GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
	    bindTextureByName("/resources/mekanism/render/AdvancedSolarGenerator.png");
	    
	    switch(tileEntity.facing)
	    {
		    case 2: GL11.glRotatef(90, 0.0F, 1.0F, 0.0F); break;
			case 3: GL11.glRotatef(270, 0.0F, 1.0F, 0.0F); break;
			case 4: GL11.glRotatef(180, 0.0F, 1.0F, 0.0F); break;
			case 5: GL11.glRotatef(0, 0.0F, 1.0F, 0.0F); break;
	    }
	    
	    GL11.glRotatef(180, 0f, 0f, 1f);
	    arrayModel.render(0.0F, 0.0625F);
	    GL11.glPopMatrix();
	}
}
