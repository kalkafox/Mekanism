package mekanism.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import mekanism.common.CommonProxy;
import mekanism.common.EntityKnife;
import mekanism.common.EntityObsidianTNT;
import mekanism.common.Mekanism;
import mekanism.common.MekanismUtils;
import mekanism.common.TileEntityAdvancedElectricMachine;
import mekanism.common.TileEntityAdvancedSolarGenerator;
import mekanism.common.TileEntityBioGenerator;
import mekanism.common.TileEntityCombiner;
import mekanism.common.TileEntityControlPanel;
import mekanism.common.TileEntityCrusher;
import mekanism.common.TileEntityElectricMachine;
import mekanism.common.TileEntityElectrolyticSeparator;
import mekanism.common.TileEntityEnrichmentChamber;
import mekanism.common.TileEntityGasTank;
import mekanism.common.TileEntityGenerator;
import mekanism.common.TileEntityHeatGenerator;
import mekanism.common.TileEntityHydrogenGenerator;
import mekanism.common.TileEntityPlatinumCompressor;
import mekanism.common.TileEntityPowerUnit;
import mekanism.common.TileEntitySolarGenerator;
import mekanism.common.TileEntityTheoreticalElementizer;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraftforge.client.MinecraftForgeClient;

/**
 * Client proxy for the Mekanism mod.
 * @author AidanBrady
 *
 */
public class ClientProxy extends CommonProxy
{
	@Override
	public int getArmorIndex(String string)
	{
		return RenderingRegistry.addNewArmourRendererPrefix(string);
	}
	
	@Override
	public void registerSpecialTileEntities()
	{
		ClientRegistry.registerTileEntity(TileEntityAdvancedSolarGenerator.class, "AdvancedSolarGenerator", new RenderAdvancedSolarGenerator(new ModelAdvancedSolarGenerator()));
		ClientRegistry.registerTileEntity(TileEntityBioGenerator.class, "BioGenerator", new RenderBioGenerator());
	}
	
	@Override
	public void registerRenderInformation()
	{
		System.out.println("[Mekanism] Beginning render initiative...");
		
		//Preload block/item textures
		MinecraftForgeClient.preloadTexture("/resources/mekanism/textures/items.png");
		MinecraftForgeClient.preloadTexture("/resources/mekanism/textures/terrain.png");
		
		//Preload animated textures
		MinecraftForgeClient.preloadTexture("/resources/mekanism/animate/CompressorFront.png");
		MinecraftForgeClient.preloadTexture("/resources/mekanism/animate/CombinerFront.png");
		MinecraftForgeClient.preloadTexture("/resources/mekanism/animate/ElementizerFront.png");
		MinecraftForgeClient.preloadTexture("/resources/mekanism/animate/ElementizerBack.png");
		MinecraftForgeClient.preloadTexture("/resources/mekanism/animate/ElementizerSide.png");
		MinecraftForgeClient.preloadTexture("/resources/mekanism/animate/HydrogenFront.png");
		MinecraftForgeClient.preloadTexture("/resources/mekanism/animate/HydrogenSide.png");
		
		//Register animated TextureFX
		try {
			TextureFXManager.instance().addAnimation(new TextureAnimatedFX("/resources/mekanism/animate/CompressorFront.png", Mekanism.ANIMATED_TEXTURE_INDEX));
			TextureFXManager.instance().addAnimation(new TextureAnimatedFX("/resources/mekanism/animate/CombinerFront.png", Mekanism.ANIMATED_TEXTURE_INDEX+1));
			TextureFXManager.instance().addAnimation(new TextureAnimatedFX("/resources/mekanism/animate/ElementizerFront.png", Mekanism.ANIMATED_TEXTURE_INDEX+2));
			TextureFXManager.instance().addAnimation(new TextureAnimatedFX("/resources/mekanism/animate/ElementizerBack.png", Mekanism.ANIMATED_TEXTURE_INDEX+3));
			TextureFXManager.instance().addAnimation(new TextureAnimatedFX("/resources/mekanism/animate/ElementizerSide.png", Mekanism.ANIMATED_TEXTURE_INDEX+4));
			TextureFXManager.instance().addAnimation(new TextureAnimatedFX("/resources/mekanism/animate/HydrogenFront.png", Mekanism.ANIMATED_TEXTURE_INDEX+5));
			TextureFXManager.instance().addAnimation(new TextureAnimatedFX("/resources/mekanism/animate/HydrogenSide.png", Mekanism.ANIMATED_TEXTURE_INDEX+6));
		} catch (IOException e) {
			System.err.println("[Mekanism] Error registering animation with FML: " + e.getMessage());
		}
		
		//Register entity rendering handlers
		RenderingRegistry.registerEntityRenderingHandler(EntityObsidianTNT.class, new RenderObsidianTNT());
		RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, new RenderKnife());
		
		//Register block handler
		RenderingRegistry.registerBlockHandler(new RenderHandler());
		
		System.out.println("[Mekanism] Render initiative complete.");
	}
	
	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	@Override
	public void loadUtilities()
	{
		System.out.println("[Mekanism] Beginning utility initiative...");
		new ThreadSendData();
		System.out.println("[Mekanism] Utility initiative complete.");
	}
	
	@Override
	public GuiScreen getClientGui(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		switch(ID)
		{
			case 0:
				return new GuiStopwatch(player);
			case 1:
				return new GuiCredits();
			case 2:
				return new GuiWeatherOrb(player);
			case 3:
				return new GuiElectricMachine(player.inventory, (TileEntityElectricMachine)tileEntity);
			case 4:
				return new GuiAdvancedElectricMachine(player.inventory, (TileEntityAdvancedElectricMachine)tileEntity);
			case 5:
				return new GuiAdvancedElectricMachine(player.inventory, (TileEntityAdvancedElectricMachine)tileEntity);
			case 6:
				return new GuiElectricMachine(player.inventory, (TileEntityElectricMachine)tileEntity);
			case 7:
				return new GuiTheoreticalElementizer(player.inventory, (TileEntityTheoreticalElementizer)tileEntity);
			case 8:
				return new GuiPowerUnit(player.inventory, (TileEntityPowerUnit)tileEntity);
			case 9:
				return new GuiHeatGenerator(player.inventory, (TileEntityHeatGenerator)tileEntity);
			case 10:
				return new GuiSolarGenerator(player.inventory, (TileEntitySolarGenerator)tileEntity);
			case 11:
				return new GuiElectrolyticSeparator(player.inventory, (TileEntityElectrolyticSeparator)tileEntity);
			case 12:
				return new GuiHydrogenGenerator(player.inventory, (TileEntityHydrogenGenerator)tileEntity);
			case 13:
				return new GuiBioGenerator(player.inventory, (TileEntityBioGenerator)tileEntity);
			case 14:
				return new GuiControlPanel((TileEntityControlPanel)tileEntity, player, world);
			case 15:
				return new GuiGasTank(player.inventory, (TileEntityGasTank)tileEntity);
		}
		return null;
	}
	
	@Override
	public void loadTickHandler()
	{
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
	}
	
	@Override
	public void loadSoundHandler()
	{
		Mekanism.audioHandler = new SoundHandler();
	}
}
