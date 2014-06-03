package mekanism.tools.common;

import io.netty.buffer.ByteBuf;
import mekanism.common.IModule;
import mekanism.common.Mekanism;
import mekanism.common.Version;
import mekanism.common.recipe.MekanismRecipe;
import mekanism.tools.item.ItemMekanismArmor;
import mekanism.tools.item.ItemMekanismAxe;
import mekanism.tools.item.ItemMekanismHoe;
import mekanism.tools.item.ItemMekanismPaxel;
import mekanism.tools.item.ItemMekanismPickaxe;
import mekanism.tools.item.ItemMekanismShovel;
import mekanism.tools.item.ItemMekanismSword;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "MekanismTools", name = "MekanismTools", version = "6.1.0", dependencies = "required-after:Mekanism")
public class MekanismTools implements IModule
{
	@Instance("MekanismTools")
	public static MekanismTools instance;
	
	/** MekanismTools version number */
	public static Version versionNumber = new Version(6, 0, 4);

	//Enums: Tools
	public static ToolMaterial toolOBSIDIAN;
	public static ToolMaterial toolOBSIDIAN2;
	public static ToolMaterial toolLAZULI;
	public static ToolMaterial toolLAZULI2;
	public static ToolMaterial toolOSMIUM;
	public static ToolMaterial toolOSMIUM2;
	public static ToolMaterial toolBRONZE;
	public static ToolMaterial toolBRONZE2;
	public static ToolMaterial toolGLOWSTONE;
	public static ToolMaterial toolGLOWSTONE2;
	public static ToolMaterial toolSTEEL;
	public static ToolMaterial toolSTEEL2;

	//Enums: Armor
	public static ArmorMaterial armorOBSIDIAN;
	public static ArmorMaterial armorLAZULI;
	public static ArmorMaterial armorOSMIUM;
	public static ArmorMaterial armorBRONZE;
	public static ArmorMaterial armorGLOWSTONE;
	public static ArmorMaterial armorSTEEL;

	//Base Items
	public static Item WoodPaxel;
	public static Item StonePaxel;
	public static Item IronPaxel;
	public static Item DiamondPaxel;
	public static Item GoldPaxel;
	
	//Glowstone Items
	public static Item GlowstonePaxel;
	public static Item GlowstonePickaxe;
	public static Item GlowstoneAxe;
	public static Item GlowstoneShovel;
	public static Item GlowstoneHoe;
	public static Item GlowstoneSword;
	public static Item GlowstoneHelmet;
	public static Item GlowstoneChestplate;
	public static Item GlowstoneLeggings;
	public static Item GlowstoneBoots;
	
	//Redstone Items
	public static Item BronzePaxel;
	public static Item BronzePickaxe;
	public static Item BronzeAxe;
	public static Item BronzeShovel;
	public static Item BronzeHoe;
	public static Item BronzeSword;
	public static Item BronzeHelmet;
	public static Item BronzeChestplate;
	public static Item BronzeLeggings;
	public static Item BronzeBoots;
	
	//Osmium Items
	public static Item OsmiumPaxel;
	public static Item OsmiumPickaxe;
	public static Item OsmiumAxe;
	public static Item OsmiumShovel;
	public static Item OsmiumHoe;
	public static Item OsmiumSword;
	public static Item OsmiumHelmet;
	public static Item OsmiumChestplate;
	public static Item OsmiumLeggings;
	public static Item OsmiumBoots;
	
	//Obsidian Items
	public static Item ObsidianHelmet;
	public static Item ObsidianChestplate;
	public static Item ObsidianLeggings;
	public static Item ObsidianBoots;
	public static Item ObsidianPaxel;
	public static Item ObsidianPickaxe;
	public static Item ObsidianAxe;
	public static Item ObsidianShovel;
	public static Item ObsidianHoe;
	public static Item ObsidianSword;
	
	//Lazuli Items
	public static Item LazuliPaxel;
	public static Item LazuliPickaxe;
	public static Item LazuliAxe;
	public static Item LazuliShovel;
	public static Item LazuliHoe;
	public static Item LazuliSword;
	public static Item LazuliHelmet;
	public static Item LazuliChestplate;
	public static Item LazuliLeggings;
	public static Item LazuliBoots;
	
	//Steel Items
	public static Item SteelPaxel;
	public static Item SteelPickaxe;
	public static Item SteelAxe;
	public static Item SteelShovel;
	public static Item SteelHoe;
	public static Item SteelSword;
	public static Item SteelHelmet;
	public static Item SteelChestplate;
	public static Item SteelLeggings;
	public static Item SteelBoots;
	
	public static double armourSpawnRate;

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//Add this module to the core list
		Mekanism.modulesLoaded.add(this);
		
		//Register this class to the event bus for special mob spawning (mobs with Mekanism armor/tools)
		MinecraftForge.EVENT_BUS.register(this);
		
		//Load this module
		addItems();
		addRecipes();
		
		//Finalization
		Mekanism.logger.info("Loaded MekanismTools module.");
	}
	
	public void addRecipes()
	{
		//Crafting Recipes
		//Base
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(WoodPaxel, 1), new Object[] {
			"XYZ", " T ", " T ", Character.valueOf('X'), Items.wooden_axe, Character.valueOf('Y'), Items.wooden_pickaxe, Character.valueOf('Z'), Items.wooden_shovel, Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(StonePaxel, 1), new Object[] {
			"XYZ", " T ", " T ", Character.valueOf('X'), Items.stone_axe, Character.valueOf('Y'), Items.stone_pickaxe, Character.valueOf('Z'), Items.stone_shovel, Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(IronPaxel, 1), new Object[] {
			"XYZ", " T ", " T ", Character.valueOf('X'), Items.iron_axe, Character.valueOf('Y'), Items.iron_pickaxe, Character.valueOf('Z'), Items.iron_shovel, Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(DiamondPaxel, 1), new Object[] {
			"XYZ", " T ", " T ", Character.valueOf('X'), Items.diamond_axe, Character.valueOf('Y'), Items.diamond_pickaxe, Character.valueOf('Z'), Items.diamond_shovel, Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(GoldPaxel, 1), new Object[] {
			"XYZ", " T ", " T ", Character.valueOf('X'), Items.golden_axe, Character.valueOf('Y'), Items.golden_pickaxe, Character.valueOf('Z'), Items.golden_shovel, Character.valueOf('T'), Items.stick
		}));
		
		//Obsidian
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(ObsidianHelmet, 1), new Object[] {
			"***", "* *", Character.valueOf('*'), "ingotRefinedObsidian"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(ObsidianChestplate, 1), new Object[] {
			"* *", "***", "***", Character.valueOf('*'), "ingotRefinedObsidian"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(ObsidianLeggings, 1), new Object[] {
			"***", "* *", "* *", Character.valueOf('*'), "ingotRefinedObsidian"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(ObsidianBoots, 1), new Object[] {
			"* *", "* *", Character.valueOf('*'), "ingotRefinedObsidian"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(ObsidianPaxel, 1), new Object[] {
			"XYZ", " T ", " T ", Character.valueOf('X'), ObsidianAxe, Character.valueOf('Y'), ObsidianPickaxe, Character.valueOf('Z'), ObsidianShovel, Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(ObsidianPickaxe, 1), new Object[] {
			"XXX", " T ", " T ", Character.valueOf('X'), "ingotRefinedObsidian", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(ObsidianAxe, 1), new Object[] {
			"XX", "XT", " T", Character.valueOf('X'), "ingotRefinedObsidian", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(ObsidianShovel, 1), new Object[] {
			"X", "T", "T", Character.valueOf('X'), "ingotRefinedObsidian", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(ObsidianHoe, 1), new Object[] {
			"XX", " T", " T", Character.valueOf('X'), "ingotRefinedObsidian", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(ObsidianSword, 1), new Object[] {
			"X", "X", "T", Character.valueOf('X'), "ingotRefinedObsidian", Character.valueOf('T'), Items.stick
		}));
		
		//Glowstone
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(GlowstonePaxel, 1), new Object[] {
			"XYZ", " T ", " T ", Character.valueOf('X'), GlowstoneAxe, Character.valueOf('Y'), GlowstonePickaxe, Character.valueOf('Z'), GlowstoneShovel, Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(GlowstonePickaxe, 1), new Object[] {
			"XXX", " T ", " T ", Character.valueOf('X'), "ingotRefinedGlowstone", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(GlowstoneAxe, 1), new Object[] {
			"XX", "XT", " T", Character.valueOf('X'), "ingotRefinedGlowstone", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(GlowstoneShovel, 1), new Object[] {
			"X", "T", "T", Character.valueOf('X'), "ingotRefinedGlowstone", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(GlowstoneHoe, 1), new Object[] {
			"XX", " T", " T", Character.valueOf('X'), "ingotRefinedGlowstone", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(GlowstoneSword, 1), new Object[] {
			"X", "X", "T", Character.valueOf('X'), "ingotRefinedGlowstone", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(GlowstoneHelmet, 1), new Object[] {
			"***", "* *", Character.valueOf('*'), "ingotRefinedGlowstone"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(GlowstoneChestplate, 1), new Object[] {
			"* *", "***", "***", Character.valueOf('*'), "ingotRefinedGlowstone"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(GlowstoneLeggings, 1), new Object[] {
			"***", "* *", "* *", Character.valueOf('*'), "ingotRefinedGlowstone"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(GlowstoneBoots, 1), new Object[] {
			"* *", "* *", Character.valueOf('*'), "ingotRefinedGlowstone"
		}));
		
		//Lazuli
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(LazuliHelmet, 1), new Object[] {
			"***", "* *", Character.valueOf('*'), new ItemStack(Items.dye, 1, 4)
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(LazuliChestplate, 1), new Object[] {
			"* *", "***", "***", Character.valueOf('*'), new ItemStack(Items.dye, 1, 4)
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(LazuliLeggings, 1), new Object[] {
			"***", "* *", "* *", Character.valueOf('*'), new ItemStack(Items.dye, 1, 4)
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(LazuliBoots, 1), new Object[] {
			"* *", "* *", Character.valueOf('*'), new ItemStack(Items.dye, 1, 4)
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(LazuliPaxel, 1), new Object[] {
			"XYZ", " T ", " T ", Character.valueOf('X'), LazuliAxe, Character.valueOf('Y'), LazuliPickaxe, Character.valueOf('Z'), LazuliShovel, Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(LazuliPickaxe, 1), new Object[] {
			"XXX", " T ", " T ", Character.valueOf('X'), new ItemStack(Items.dye, 1, 4), Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(LazuliAxe, 1), new Object[] {
			"XX", "XT", " T", Character.valueOf('X'), new ItemStack(Items.dye, 1, 4), Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(LazuliShovel, 1), new Object[] {
			"X", "T", "T", Character.valueOf('X'), new ItemStack(Items.dye, 1, 4), Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(LazuliHoe, 1), new Object[] {
			"XX", " T", " T", Character.valueOf('X'), new ItemStack(Items.dye, 1, 4), Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(LazuliSword, 1), new Object[] {
			"X", "X", "T", Character.valueOf('X'), new ItemStack(Items.dye, 1, 4), Character.valueOf('T'), Items.stick
		}));
		
		//Osmium
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(OsmiumPaxel, 1), new Object[] {
			"XYZ", " T ", " T ", Character.valueOf('X'), OsmiumAxe, Character.valueOf('Y'), OsmiumPickaxe, Character.valueOf('Z'), OsmiumShovel, Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(OsmiumPickaxe, 1), new Object[] {
			"XXX", " T ", " T ", Character.valueOf('X'), "ingotOsmium", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(OsmiumAxe, 1), new Object[] {
			"XX", "XT", " T", Character.valueOf('X'), "ingotOsmium", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(OsmiumShovel, 1), new Object[] {
			"X", "T", "T", Character.valueOf('X'), "ingotOsmium", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(OsmiumHoe, 1), new Object[] {
			"XX", " T", " T", Character.valueOf('X'), "ingotOsmium", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(OsmiumSword, 1), new Object[] {
			"X", "X", "T", Character.valueOf('X'), "ingotOsmium", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(OsmiumHelmet, 1), new Object[] {
			"***", "* *", Character.valueOf('*'), "ingotOsmium"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(OsmiumChestplate, 1), new Object[] {
			"* *", "***", "***", Character.valueOf('*'), "ingotOsmium"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(OsmiumLeggings, 1), new Object[] {
			"***", "* *", "* *", Character.valueOf('*'), "ingotOsmium"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(OsmiumBoots, 1), new Object[] {
			"* *", "* *", Character.valueOf('*'), "ingotOsmium"
		}));
		
		//Bronze
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(BronzePaxel, 1), new Object[] {
			"XYZ", " T ", " T ", Character.valueOf('X'), BronzeAxe, Character.valueOf('Y'), BronzePickaxe, Character.valueOf('Z'), BronzeShovel, Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(BronzePickaxe, 1), new Object[] {
			"XXX", " T ", " T ", Character.valueOf('X'), "ingotBronze", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(BronzeAxe, 1), new Object[] {
			"XX", "XT", " T", Character.valueOf('X'), "ingotBronze", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(BronzeShovel, 1), new Object[] {
			"X", "T", "T", Character.valueOf('X'), "ingotBronze", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(BronzeHoe, 1), new Object[] {
			"XX", " T", " T", Character.valueOf('X'), "ingotBronze", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(BronzeSword, 1), new Object[] {
			"X", "X", "T", Character.valueOf('X'), "ingotBronze", Character.valueOf('T'), Items.stick
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(BronzeHelmet, 1), new Object[] {
			"***", "* *", Character.valueOf('*'), "ingotBronze"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(BronzeChestplate, 1), new Object[] {
			"* *", "***", "***", Character.valueOf('*'), "ingotBronze"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(BronzeLeggings, 1), new Object[] {
			"***", "* *", "* *", Character.valueOf('*'), "ingotBronze"
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(BronzeBoots, 1), new Object[] {
			"* *", "* *", Character.valueOf('*'), "ingotBronze"
		}));
		
		//Steel
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(SteelPaxel, 1), new Object[] {
			"XYZ", " I ", " I ", Character.valueOf('X'), SteelAxe, Character.valueOf('Y'), SteelPickaxe, Character.valueOf('Z'), SteelShovel, Character.valueOf('I'), Items.iron_ingot
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(SteelPickaxe, 1), new Object[] {
			"XXX", " I ", " I ", Character.valueOf('X'), "ingotSteel", Character.valueOf('I'), Items.iron_ingot
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(SteelAxe, 1), new Object[] {
			"XX", "XI", " I", Character.valueOf('X'), "ingotSteel", Character.valueOf('I'), Items.iron_ingot
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(SteelShovel, 1), new Object[] {
			"X", "I", "I", Character.valueOf('X'), "ingotSteel", Character.valueOf('I'), Items.iron_ingot
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(SteelHoe, 1), new Object[] {
			"XX", " I", " I", Character.valueOf('X'), "ingotSteel", Character.valueOf('I'), Items.iron_ingot
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(SteelSword, 1), new Object[] {
			"X", "X", "I", Character.valueOf('X'), "ingotSteel", Character.valueOf('I'), Items.iron_ingot
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(SteelHelmet, 1), new Object[] {
			"***", "I I", Character.valueOf('*'), "ingotSteel", Character.valueOf('I'), Items.iron_ingot
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(SteelChestplate, 1), new Object[] {
			"I I", "*I*", "***", Character.valueOf('*'), "ingotSteel", Character.valueOf('I'), Items.iron_ingot
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(SteelLeggings, 1), new Object[] {
			"I*I", "* *", "* *", Character.valueOf('*'), "ingotSteel", Character.valueOf('I'), Items.iron_ingot
		}));
		CraftingManager.getInstance().getRecipeList().add(new MekanismRecipe(new ItemStack(SteelBoots, 1), new Object[] {
			"I *", "* I", Character.valueOf('*'), "ingotSteel", Character.valueOf('I'), Items.iron_ingot
		}));
	}
	
	public void addItems()
	{
		//Declarations
		Mekanism.configuration.load();

		//Tools
		toolOBSIDIAN = EnumHelper.addToolMaterial("OBSIDIAN"
				, Mekanism.configuration.get("tool-balance", "obsidian-harvestLevel", 3).getInt()
				, Mekanism.configuration.get("tool-balance", "obsidian-maxUses", 2500).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "obsidian-efficiency", 20d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "obsidian-damage", 10).getInt()
				, Mekanism.configuration.get("tool-balance", "obsidian-enchantability", 100).getInt()
		);
		toolOBSIDIAN2 = EnumHelper.addToolMaterial("OBSIDIAN2"
				, Mekanism.configuration.get("tool-balance", "obsidian-paxel-harvestLevel", 3).getInt()
				, Mekanism.configuration.get("tool-balance", "obsidian-paxel-maxUses", 3000).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "obsidian-paxel-efficiency", 25d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "obsidian-paxel-damage", 10).getInt()
				, Mekanism.configuration.get("tool-balance", "obsidian-paxel-enchantability", 100).getInt()
		);
		toolLAZULI = EnumHelper.addToolMaterial("LAZULI"
				, Mekanism.configuration.get("tool-balance", "lazuli-harvestLevel", 2).getInt()
				, Mekanism.configuration.get("tool-balance", "lazuli-maxUses", 200).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "lazuli-efficiency", 5d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "lazuli-damage", 2).getInt()
				, Mekanism.configuration.get("tool-balance", "lazuli-enchantability", 22).getInt()
		);
		toolLAZULI2 = EnumHelper.addToolMaterial("LAZULI2"
				, Mekanism.configuration.get("tool-balance", "lazuli-paxel-harvestLevel", 2).getInt()
				, Mekanism.configuration.get("tool-balance", "lazuli-paxel-maxUses", 250).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "lazuli-paxel-efficiency", 6d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "lazuli-paxel-damage", 4).getInt()
				, Mekanism.configuration.get("tool-balance", "lazuli-paxel-enchantability", 50).getInt()
		);
		toolOSMIUM = EnumHelper.addToolMaterial("OSMIUM"
				, Mekanism.configuration.get("tool-balance", "osmium-harvestLevel", 2).getInt()
				, Mekanism.configuration.get("tool-balance", "osmium-maxUses", 500).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "osmium-efficiency", 10d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "osmium-damage", 4).getInt()
				, Mekanism.configuration.get("tool-balance", "osmium-enchantability", 30).getInt()
		);
		toolOSMIUM2 = EnumHelper.addToolMaterial("OSMIUM2"
				, Mekanism.configuration.get("tool-balance", "osmium-paxel-harvestLevel", 3).getInt()
				, Mekanism.configuration.get("tool-balance", "osmium-paxel-maxUses", 700).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "osmium-paxel-efficiency", 12d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "osmium-paxel-damage", 5).getInt()
				, Mekanism.configuration.get("tool-balance", "osmium-paxel-enchantability", 40).getInt()
		);
		toolBRONZE = EnumHelper.addToolMaterial("BRONZE"
				, Mekanism.configuration.get("tool-balance", "bronze-harvestLevel", 2).getInt()
				, Mekanism.configuration.get("tool-balance", "bronze-maxUses", 800).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "bronze-efficiency", 14d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "bronze-damage", 6).getInt()
				, Mekanism.configuration.get("tool-balance", "bronze-enchantability", 100).getInt()
		);
		toolBRONZE2 = EnumHelper.addToolMaterial("BRONZE2"
				, Mekanism.configuration.get("tool-balance", "bronze-paxel-harvestLevel", 3).getInt()
				, Mekanism.configuration.get("tool-balance", "bronze-paxel-maxUses", 1100).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "bronze-paxel-efficiency", 16d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "bronze-paxel-damage", 10).getInt()
				, Mekanism.configuration.get("tool-balance", "bronze-paxel-enchantability", 100).getInt()
		);
		toolGLOWSTONE = EnumHelper.addToolMaterial("GLOWSTONE"
				, Mekanism.configuration.get("tool-balance", "glowstone-harvestLevel", 2).getInt()
				, Mekanism.configuration.get("tool-balance", "glowstone-maxUses", 300).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "glowstone-efficiency", 14d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "glowstone-damage", 5).getInt()
				, Mekanism.configuration.get("tool-balance", "glowstone-enchantability", 80).getInt()
		);
		toolGLOWSTONE2 = EnumHelper.addToolMaterial("GLOWSTONE2"
				, Mekanism.configuration.get("tool-balance", "glowstone-paxel-harvestLevel", 2).getInt()
				, Mekanism.configuration.get("tool-balance", "glowstone-paxel-maxUses", 450).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "glowstone-paxel-efficiency", 18d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "glowstone-paxel-damage", 5).getInt()
				, Mekanism.configuration.get("tool-balance", "glowstone-paxel-enchantability", 100).getInt()
		);
		toolSTEEL = EnumHelper.addToolMaterial("STEEL"
				, Mekanism.configuration.get("tool-balance", "steel-harvestLevel", 3).getInt()
				, Mekanism.configuration.get("tool-balance", "steel-maxUses", 850).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "steel-efficiency", 14d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "steel-damage", 4).getInt()
				, Mekanism.configuration.get("tool-balance", "steel-enchantability", 100).getInt()
		);
		toolSTEEL2 = EnumHelper.addToolMaterial("STEEL2"
				, Mekanism.configuration.get("tool-balance", "steel-paxel-harvestLevel", 3).getInt()
				, Mekanism.configuration.get("tool-balance", "steel-paxel-maxUses", 1250).getInt()
				, (float)Mekanism.configuration.get("tool-balance", "steel-paxel-efficiency", 18d).getDouble(0)
				, Mekanism.configuration.get("tool-balance", "steel-paxel-damage", 8).getInt()
				, Mekanism.configuration.get("tool-balance", "steel-paxel-enchantability", 100).getInt()
		);

		//Armors
		armorOBSIDIAN = EnumHelper.addArmorMaterial("OBSIDIAN"
				, Mekanism.configuration.get("armor-balance", "obsidian-durability", 50).getInt()
				, new int[]
				{
						Mekanism.configuration.get("armor-balance", "obsidian-reduction-amount-head", 5).getInt()
						, Mekanism.configuration.get("armor-balance", "obsidian-reduction-amount-chest", 12).getInt()
						, Mekanism.configuration.get("armor-balance", "obsidian-reduction-amount-legs", 8).getInt()
						, Mekanism.configuration.get("armor-balance", "obsidian-reduction-amount-feet", 5).getInt()
				}
				, Mekanism.configuration.get("armor-balance", "obsidian-enchantability", 50).getInt()
		);
		armorLAZULI = EnumHelper.addArmorMaterial("LAZULI"
				, Mekanism.configuration.get("armor-balance", "lazuli-durability", 13).getInt()
				, new int[]
				{
						Mekanism.configuration.get("armor-balance", "lazuli-reduction-amount-head", 2).getInt()
						, Mekanism.configuration.get("armor-balance", "lazuli-reduction-amount-chest", 5).getInt()
						, Mekanism.configuration.get("armor-balance", "lazuli-reduction-amount-legs", 6).getInt()
						, Mekanism.configuration.get("armor-balance", "lazuli-reduction-amount-feet", 2).getInt()
				}
				, Mekanism.configuration.get("armor-balance", "lazuli-enchantability", 50).getInt()
		);
		armorOSMIUM = EnumHelper.addArmorMaterial("OSMIUM"
				, Mekanism.configuration.get("armor-balance", "osmium-durability", 30).getInt()
				, new int[]
				{
						Mekanism.configuration.get("armor-balance", "osmium-reduction-amount-head", 3).getInt()
						, Mekanism.configuration.get("armor-balance", "osmium-reduction-amount-chest", 5).getInt()
						, Mekanism.configuration.get("armor-balance", "osmium-reduction-amount-legs", 6).getInt()
						, Mekanism.configuration.get("armor-balance", "osmium-reduction-amount-feet", 3).getInt()
				}
				, Mekanism.configuration.get("armor-balance", "osmium-enchantability", 50).getInt()
		);
		armorBRONZE = EnumHelper.addArmorMaterial("BRONZE"
				, Mekanism.configuration.get("armor-balance", "bronze-durability", 35).getInt()
				, new int[]
				{
						Mekanism.configuration.get("armor-balance", "bronze-reduction-amount-head", 3).getInt()
						, Mekanism.configuration.get("armor-balance", "bronze-reduction-amount-chest", 6).getInt()
						, Mekanism.configuration.get("armor-balance", "bronze-reduction-amount-legs", 5).getInt()
						, Mekanism.configuration.get("armor-balance", "bronze-reduction-amount-feet", 2).getInt()
				}
				, Mekanism.configuration.get("armor-balance", "bronze-enchantability", 50).getInt()
		);
		armorGLOWSTONE = EnumHelper.addArmorMaterial("GLOWSTONE"
				, Mekanism.configuration.get("armor-balance", "glowstone-durability", 18).getInt()
				, new int[]
				{
						Mekanism.configuration.get("armor-balance", "glowstone-reduction-amount-head", 3).getInt()
						, Mekanism.configuration.get("armor-balance", "glowstone-reduction-amount-chest", 7).getInt()
						, Mekanism.configuration.get("armor-balance", "glowstone-reduction-amount-legs", 6).getInt()
						, Mekanism.configuration.get("armor-balance", "glowstone-reduction-amount-feet", 3).getInt()
				}
				, Mekanism.configuration.get("armor-balance", "glowstone-enchantability", 50).getInt()
		);
		armorSTEEL = EnumHelper.addArmorMaterial("STEEL"
				, Mekanism.configuration.get("armor-balance", "steel-durability", 40).getInt()
				, new int[]
				{
						Mekanism.configuration.get("armor-balance", "steel-reduction-amount-head", 3).getInt()
						, Mekanism.configuration.get("armor-balance", "steel-reduction-amount-chest", 7).getInt()
						, Mekanism.configuration.get("armor-balance", "steel-reduction-amount-legs", 6).getInt()
						, Mekanism.configuration.get("armor-balance", "steel-reduction-amount-feet", 3).getInt()
				}
				, Mekanism.configuration.get("armor-balance", "steel-enchantability", 50).getInt()
		);

		armourSpawnRate = Mekanism.configuration.get(Configuration.CATEGORY_GENERAL, "mob-armour-spawn-rate", 0.03).getDouble(0.03);

		//Bronze
		BronzeHelmet = (new ItemMekanismArmor(armorBRONZE, Mekanism.proxy.getArmorIndex("bronze"), 0)).setUnlocalizedName("BronzeHelmet");
		BronzeChestplate = (new ItemMekanismArmor(armorBRONZE, Mekanism.proxy.getArmorIndex("bronze"), 1)).setUnlocalizedName("BronzeChestplate");
		BronzeLeggings = (new ItemMekanismArmor(armorBRONZE, Mekanism.proxy.getArmorIndex("bronze"), 2)).setUnlocalizedName("BronzeLeggings");
		BronzeBoots = (new ItemMekanismArmor(armorBRONZE, Mekanism.proxy.getArmorIndex("bronze"), 3)).setUnlocalizedName("BronzeBoots");
		BronzePaxel = new ItemMekanismPaxel(toolBRONZE2).setUnlocalizedName("BronzePaxel");
		BronzePickaxe = new ItemMekanismPickaxe(toolBRONZE).setUnlocalizedName("BronzePickaxe");
		BronzeAxe = new ItemMekanismAxe(toolBRONZE).setUnlocalizedName("BronzeAxe");
		BronzeShovel = new ItemMekanismShovel(toolBRONZE).setUnlocalizedName("BronzeShovel");
		BronzeHoe = new ItemMekanismHoe(toolBRONZE).setUnlocalizedName("BronzeHoe");
		BronzeSword = new ItemMekanismSword(toolBRONZE).setUnlocalizedName("BronzeSword");
		//TODO wat do?
		//MinecraftForge.setToolClass(BronzePaxel, "paxel", toolBRONZE2.getHarvestLevel());
		//MinecraftForge.setToolClass(BronzePickaxe, "pickaxe", toolBRONZE.getHarvestLevel());
		//MinecraftForge.setToolClass(BronzeAxe, "axe", toolBRONZE.getHarvestLevel());
		//MinecraftForge.setToolClass(BronzeShovel, "shovel", toolBRONZE.getHarvestLevel());
		
		//Osmium
		OsmiumHelmet = (new ItemMekanismArmor(armorOSMIUM, Mekanism.proxy.getArmorIndex("osmium"), 0)).setUnlocalizedName("OsmiumHelmet");
		OsmiumChestplate = (new ItemMekanismArmor(armorOSMIUM, Mekanism.proxy.getArmorIndex("osmium"), 1)).setUnlocalizedName("OsmiumChestplate");
		OsmiumLeggings = (new ItemMekanismArmor(armorOSMIUM, Mekanism.proxy.getArmorIndex("osmium"), 2)).setUnlocalizedName("OsmiumLeggings");
		OsmiumBoots = (new ItemMekanismArmor(armorOSMIUM, Mekanism.proxy.getArmorIndex("osmium"), 3)).setUnlocalizedName("OsmiumBoots");
		OsmiumPaxel = new ItemMekanismPaxel(toolOSMIUM2).setUnlocalizedName("OsmiumPaxel");
		OsmiumPickaxe = new ItemMekanismPickaxe(toolOSMIUM).setUnlocalizedName("OsmiumPickaxe");
		OsmiumAxe = new ItemMekanismAxe(toolOSMIUM).setUnlocalizedName("OsmiumAxe");
		OsmiumShovel = new ItemMekanismShovel(toolOSMIUM).setUnlocalizedName("OsmiumShovel");
		OsmiumHoe = new ItemMekanismHoe(toolOSMIUM).setUnlocalizedName("OsmiumHoe");
		OsmiumSword = new ItemMekanismSword(toolOSMIUM).setUnlocalizedName("OsmiumSword");
		
		//MinecraftForge.setToolClass(OsmiumPaxel, "paxel", toolOSMIUM2.getHarvestLevel());
		//MinecraftForge.setToolClass(OsmiumPickaxe, "pickaxe", toolOSMIUM.getHarvestLevel());
		//MinecraftForge.setToolClass(OsmiumAxe, "axe", toolOSMIUM.getHarvestLevel());
		//MinecraftForge.setToolClass(OsmiumShovel, "shovel", toolOSMIUM.getHarvestLevel());
		
		//Obsidian
		ObsidianHelmet = (new ItemMekanismArmor(armorOBSIDIAN, Mekanism.proxy.getArmorIndex("obsidian"), 0)).setUnlocalizedName("ObsidianHelmet");
		ObsidianChestplate = (new ItemMekanismArmor(armorOBSIDIAN, Mekanism.proxy.getArmorIndex("obsidian"), 1)).setUnlocalizedName("ObsidianChestplate");
		ObsidianLeggings = (new ItemMekanismArmor(armorOBSIDIAN, Mekanism.proxy.getArmorIndex("obsidian"), 2)).setUnlocalizedName("ObsidianLeggings");
		ObsidianBoots = (new ItemMekanismArmor(armorOBSIDIAN, Mekanism.proxy.getArmorIndex("obsidian"), 3)).setUnlocalizedName("ObsidianBoots");
		ObsidianPaxel = new ItemMekanismPaxel(toolOBSIDIAN2).setUnlocalizedName("ObsidianPaxel");
		ObsidianPickaxe = new ItemMekanismPickaxe(toolOBSIDIAN).setUnlocalizedName("ObsidianPickaxe");
		ObsidianAxe = new ItemMekanismAxe(toolOBSIDIAN).setUnlocalizedName("ObsidianAxe");
		ObsidianShovel = new ItemMekanismShovel(toolOBSIDIAN).setUnlocalizedName("ObsidianShovel");
		ObsidianHoe = new ItemMekanismHoe(toolOBSIDIAN).setUnlocalizedName("ObsidianHoe");
		ObsidianSword = new ItemMekanismSword(toolOBSIDIAN).setUnlocalizedName("ObsidianSword");
		
		//MinecraftForge.setToolClass(ObsidianPaxel, "paxel", toolOBSIDIAN2.getHarvestLevel());
		//MinecraftForge.setToolClass(ObsidianPickaxe, "pickaxe", toolOBSIDIAN.getHarvestLevel());
		//MinecraftForge.setToolClass(ObsidianAxe, "axe", toolOBSIDIAN.getHarvestLevel());
		//MinecraftForge.setToolClass(ObsidianShovel, "shovel", toolOBSIDIAN.getHarvestLevel());
		
		//Lazuli
		LazuliPaxel = new ItemMekanismPaxel(toolLAZULI2).setUnlocalizedName("LazuliPaxel");
		LazuliPickaxe = new ItemMekanismPickaxe(toolLAZULI).setUnlocalizedName("LazuliPickaxe");
		LazuliAxe = new ItemMekanismAxe(toolLAZULI).setUnlocalizedName("LazuliAxe");
		LazuliShovel = new ItemMekanismShovel(toolLAZULI).setUnlocalizedName("LazuliShovel");
		LazuliHoe = new ItemMekanismHoe(toolLAZULI).setUnlocalizedName("LazuliHoe");
		LazuliSword = new ItemMekanismSword(toolLAZULI).setUnlocalizedName("LazuliSword");
		LazuliHelmet = (new ItemMekanismArmor(armorLAZULI, Mekanism.proxy.getArmorIndex("lazuli"), 0)).setUnlocalizedName("LazuliHelmet");
		LazuliChestplate = (new ItemMekanismArmor(armorLAZULI, Mekanism.proxy.getArmorIndex("lazuli"), 1)).setUnlocalizedName("LazuliChestplate");
		LazuliLeggings = (new ItemMekanismArmor(armorLAZULI, Mekanism.proxy.getArmorIndex("lazuli"), 2)).setUnlocalizedName("LazuliLeggings");
		LazuliBoots = (new ItemMekanismArmor(armorLAZULI, Mekanism.proxy.getArmorIndex("lazuli"), 3)).setUnlocalizedName("LazuliBoots");
		
		//MinecraftForge.setToolClass(LazuliPaxel, "paxel", toolLAZULI2.getHarvestLevel());
		//MinecraftForge.setToolClass(LazuliPickaxe, "pickaxe", toolLAZULI.getHarvestLevel());
		//MinecraftForge.setToolClass(LazuliAxe, "axe", toolLAZULI.getHarvestLevel());
		//MinecraftForge.setToolClass(LazuliShovel, "shovel", toolLAZULI.getHarvestLevel());
		
		//Glowstone
		GlowstonePaxel = new ItemMekanismPaxel(toolGLOWSTONE2).setUnlocalizedName("GlowstonePaxel");
		GlowstonePickaxe = new ItemMekanismPickaxe(toolGLOWSTONE).setUnlocalizedName("GlowstonePickaxe");
		GlowstoneAxe = new ItemMekanismAxe(toolGLOWSTONE).setUnlocalizedName("GlowstoneAxe");
		GlowstoneShovel = new ItemMekanismShovel(toolGLOWSTONE).setUnlocalizedName("GlowstoneShovel");
		GlowstoneHoe = new ItemMekanismHoe(toolGLOWSTONE).setUnlocalizedName("GlowstoneHoe");
		GlowstoneSword = new ItemMekanismSword(toolGLOWSTONE).setUnlocalizedName("GlowstoneSword");
		GlowstoneHelmet = new ItemMekanismArmor(armorGLOWSTONE, Mekanism.proxy.getArmorIndex("glowstone"), 0).setUnlocalizedName("GlowstoneHelmet");
		GlowstoneChestplate = new ItemMekanismArmor(armorGLOWSTONE, Mekanism.proxy.getArmorIndex("glowstone"), 1).setUnlocalizedName("GlowstoneChestplate");
		GlowstoneLeggings = new ItemMekanismArmor(armorGLOWSTONE, Mekanism.proxy.getArmorIndex("glowstone"), 2).setUnlocalizedName("GlowstoneLeggings");
		GlowstoneBoots = new ItemMekanismArmor(armorGLOWSTONE, Mekanism.proxy.getArmorIndex("glowstone"), 3).setUnlocalizedName("GlowstoneBoots");
		
		//MinecraftForge.setToolClass(GlowstonePaxel, "paxel", toolGLOWSTONE2.getHarvestLevel());
		//MinecraftForge.setToolClass(GlowstonePickaxe, "pickaxe", toolGLOWSTONE.getHarvestLevel());
		//MinecraftForge.setToolClass(GlowstoneAxe, "axe", toolGLOWSTONE.getHarvestLevel());
		//MinecraftForge.setToolClass(GlowstoneShovel, "shovel", toolGLOWSTONE.getHarvestLevel());
		
		//Base Paxels
		WoodPaxel = new ItemMekanismPaxel(ToolMaterial.WOOD).setUnlocalizedName("WoodPaxel");
		StonePaxel = new ItemMekanismPaxel(ToolMaterial.STONE).setUnlocalizedName("StonePaxel");
		IronPaxel = new ItemMekanismPaxel(ToolMaterial.IRON).setUnlocalizedName("IronPaxel");
		DiamondPaxel = new ItemMekanismPaxel(ToolMaterial.EMERALD).setUnlocalizedName("DiamondPaxel");
		GoldPaxel = new ItemMekanismPaxel(ToolMaterial.GOLD).setUnlocalizedName("GoldPaxel");
		
		//MinecraftForge.setToolClass(WoodPaxel, "paxel", ToolMaterial.WOOD.getHarvestLevel());
		//MinecraftForge.setToolClass(StonePaxel, "paxel", ToolMaterial.STONE.getHarvestLevel());
		//MinecraftForge.setToolClass(IronPaxel, "paxel", ToolMaterial.IRON.getHarvestLevel());
		//MinecraftForge.setToolClass(DiamondPaxel, "paxel", ToolMaterial.EMERALD.getHarvestLevel());
		//MinecraftForge.setToolClass(GoldPaxel, "paxel", ToolMaterial.GOLD.getHarvestLevel());
		
		//Steel
		SteelPaxel = new ItemMekanismPaxel(toolSTEEL2).setUnlocalizedName("SteelPaxel");
		SteelPickaxe = new ItemMekanismPickaxe(toolSTEEL).setUnlocalizedName("SteelPickaxe");
		SteelAxe = new ItemMekanismAxe(toolSTEEL).setUnlocalizedName("SteelAxe");
		SteelShovel = new ItemMekanismShovel(toolSTEEL).setUnlocalizedName("SteelShovel");
		SteelHoe = new ItemMekanismHoe(toolSTEEL).setUnlocalizedName("SteelHoe");
		SteelSword = new ItemMekanismSword(toolSTEEL).setUnlocalizedName("SteelSword");
		SteelHelmet = new ItemMekanismArmor(armorSTEEL, Mekanism.proxy.getArmorIndex("steel"), 0).setUnlocalizedName("SteelHelmet");
		SteelChestplate = new ItemMekanismArmor(armorSTEEL, Mekanism.proxy.getArmorIndex("steel"), 1).setUnlocalizedName("SteelChestplate");
		SteelLeggings = new ItemMekanismArmor(armorSTEEL, Mekanism.proxy.getArmorIndex("steel"), 2).setUnlocalizedName("SteelLeggings");
		SteelBoots = new ItemMekanismArmor(armorSTEEL, Mekanism.proxy.getArmorIndex("steel"), 3).setUnlocalizedName("SteelBoots");
		
		//MinecraftForge.setToolClass(SteelPaxel, "paxel", toolSTEEL2.getHarvestLevel());
		//MinecraftForge.setToolClass(SteelPickaxe, "pickaxe", toolSTEEL.getHarvestLevel());
		//MinecraftForge.setToolClass(SteelAxe, "axe", toolSTEEL.getHarvestLevel());
		//MinecraftForge.setToolClass(SteelShovel, "shovel", toolSTEEL.getHarvestLevel());
		
		Mekanism.configuration.save();
		
		//Registrations
		//Base
		GameRegistry.registerItem(WoodPaxel, "WoodPaxel");
		GameRegistry.registerItem(StonePaxel, "StonePaxel");
		GameRegistry.registerItem(IronPaxel, "IronPaxel");
		GameRegistry.registerItem(DiamondPaxel, "DiamondPaxel");
		GameRegistry.registerItem(GoldPaxel, "GoldPaxel");
		
		//Obsidian
		GameRegistry.registerItem(ObsidianHelmet, "ObsidianHelmet");
		GameRegistry.registerItem(ObsidianChestplate, "ObsidianChestplate");
		GameRegistry.registerItem(ObsidianLeggings, "ObsidianLeggings");
		GameRegistry.registerItem(ObsidianBoots, "ObsidianBoots");
		GameRegistry.registerItem(ObsidianPaxel, "ObsidianPaxel");
		GameRegistry.registerItem(ObsidianPickaxe, "ObsidianPickaxe");
		GameRegistry.registerItem(ObsidianAxe, "ObsidianAxe");
		GameRegistry.registerItem(ObsidianShovel, "ObsidianShovel");
		GameRegistry.registerItem(ObsidianHoe, "ObsidianHoe");
		GameRegistry.registerItem(ObsidianSword, "ObsidianSword");
		
		//Lazuli
		GameRegistry.registerItem(LazuliHelmet, "LapisLazuliHelmet");
		GameRegistry.registerItem(LazuliChestplate, "LapisLazuliChestplate");
		GameRegistry.registerItem(LazuliLeggings, "LapisLazuliLeggings");
		GameRegistry.registerItem(LazuliBoots, "LapisLazuliBoots");
		GameRegistry.registerItem(LazuliPaxel, "LapisLazuliPaxel");
		GameRegistry.registerItem(LazuliPickaxe, "LapisLazuliPickaxe");
		GameRegistry.registerItem(LazuliAxe, "LapisLazuliAxe");
		GameRegistry.registerItem(LazuliShovel, "LapisLazuliShovel");
		GameRegistry.registerItem(LazuliHoe, "LapisLazuliHoe");
		GameRegistry.registerItem(LazuliSword, "LapisLazuliSword");
		
		//Osmium
		GameRegistry.registerItem(OsmiumHelmet, "OsmiumHelmet");
		GameRegistry.registerItem(OsmiumChestplate, "OsmiumChestplate");
		GameRegistry.registerItem(OsmiumLeggings, "OsmiumLeggings");
		GameRegistry.registerItem(OsmiumBoots, "OsmiumBoots");
		GameRegistry.registerItem(OsmiumPaxel, "OsmiumPaxel");
		GameRegistry.registerItem(OsmiumPickaxe, "OsmiumPickaxe");
		GameRegistry.registerItem(OsmiumAxe, "OsmiumAxe");
		GameRegistry.registerItem(OsmiumShovel, "OsmiumShovel");
		GameRegistry.registerItem(OsmiumHoe, "OsmiumHoe");
		GameRegistry.registerItem(OsmiumSword, "OsmiumSword");
		
		//Redstone
		GameRegistry.registerItem(BronzeHelmet, "BronzeHelmet");
		GameRegistry.registerItem(BronzeChestplate, "BronzeChestplate");
		GameRegistry.registerItem(BronzeLeggings, "BronzeLeggings");
		GameRegistry.registerItem(BronzeBoots, "BronzeBoots");
		GameRegistry.registerItem(BronzePaxel, "BronzePaxel");
		GameRegistry.registerItem(BronzePickaxe, "BronzePickaxe");
		GameRegistry.registerItem(BronzeAxe, "BronzeAxe");
		GameRegistry.registerItem(BronzeShovel, "BronzeShovel");
		GameRegistry.registerItem(BronzeHoe, "BronzeHoe");
		GameRegistry.registerItem(BronzeSword, "BronzeSword");
		
		//Glowstone
		GameRegistry.registerItem(GlowstonePaxel, "GlowstonePaxel");
		GameRegistry.registerItem(GlowstonePickaxe, "GlowstonePickaxe");
		GameRegistry.registerItem(GlowstoneAxe, "GlowstoneAxe");
		GameRegistry.registerItem(GlowstoneShovel, "GlowstoneShovel");
		GameRegistry.registerItem(GlowstoneHoe, "GlowstoneHoe");
		GameRegistry.registerItem(GlowstoneSword, "GlowstoneSword");
		GameRegistry.registerItem(GlowstoneHelmet, "GlowstoneHelmet");
		GameRegistry.registerItem(GlowstoneChestplate, "GlowstoneChestplate");
		GameRegistry.registerItem(GlowstoneLeggings, "GlowstoneLeggings");
		GameRegistry.registerItem(GlowstoneBoots, "GlowstoneBoots");
		
		//Steel
		GameRegistry.registerItem(SteelPaxel, "SteelPaxel");
		GameRegistry.registerItem(SteelPickaxe, "SteelPickaxe");
		GameRegistry.registerItem(SteelAxe, "SteelAxe");
		GameRegistry.registerItem(SteelShovel, "SteelShovel");
		GameRegistry.registerItem(SteelHoe, "SteelHoe");
		GameRegistry.registerItem(SteelSword, "SteelSword");
		GameRegistry.registerItem(SteelHelmet, "SteelHelmet");
		GameRegistry.registerItem(SteelChestplate, "SteelChestplate");
		GameRegistry.registerItem(SteelLeggings, "SteelLeggings");
		GameRegistry.registerItem(SteelBoots, "SteelBoots");
	}
	

	public void onLivingSpecialSpawn(LivingSpawnEvent event)
	{
		double chance = event.world.rand.nextDouble();
		int armorType = event.world.rand.nextInt(4);
		
		if(chance < armourSpawnRate)
		{
			if(event.entityLiving instanceof EntityZombie || event.entityLiving instanceof EntitySkeleton)
			{
				int sword = event.world.rand.nextInt(100);
				int helmet = event.world.rand.nextInt(100);
				int chestplate = event.world.rand.nextInt(100);
				int leggings = event.world.rand.nextInt(100);
				int boots = event.world.rand.nextInt(100);
				
				if(armorType == 0)
				{
					if(event.entityLiving instanceof EntityZombie && sword < 50) event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(GlowstoneSword));
					if(helmet < 50) event.entityLiving.setCurrentItemOrArmor(1, new ItemStack(GlowstoneHelmet));
					if(chestplate < 50) event.entityLiving.setCurrentItemOrArmor(2, new ItemStack(GlowstoneChestplate));
					if(leggings < 50) event.entityLiving.setCurrentItemOrArmor(3, new ItemStack(GlowstoneLeggings));
					if(boots < 50) event.entityLiving.setCurrentItemOrArmor(4, new ItemStack(GlowstoneBoots));
				}
				else if(armorType == 1)
				{
					if(event.entityLiving instanceof EntityZombie && sword < 50) event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(LazuliSword));
					if(helmet < 50) event.entityLiving.setCurrentItemOrArmor(1, new ItemStack(LazuliHelmet));
					if(chestplate < 50) event.entityLiving.setCurrentItemOrArmor(2, new ItemStack(LazuliChestplate));
					if(leggings < 50) event.entityLiving.setCurrentItemOrArmor(3, new ItemStack(LazuliLeggings));
					if(boots < 50) event.entityLiving.setCurrentItemOrArmor(4, new ItemStack(LazuliBoots));
				}
				else if(armorType == 2)
				{
					if(event.entityLiving instanceof EntityZombie && sword < 50) event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(OsmiumSword));
					if(helmet < 50) event.entityLiving.setCurrentItemOrArmor(1, new ItemStack(OsmiumHelmet));
					if(chestplate < 50) event.entityLiving.setCurrentItemOrArmor(2, new ItemStack(OsmiumChestplate));
					if(leggings < 50) event.entityLiving.setCurrentItemOrArmor(3, new ItemStack(OsmiumLeggings));
					if(boots < 50) event.entityLiving.setCurrentItemOrArmor(4, new ItemStack(OsmiumBoots));
				}
				else if(armorType == 3)
				{
					if(event.entityLiving instanceof EntityZombie && sword < 50) event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(SteelSword));
					if(helmet < 50) event.entityLiving.setCurrentItemOrArmor(1, new ItemStack(SteelHelmet));
					if(chestplate < 50) event.entityLiving.setCurrentItemOrArmor(2, new ItemStack(SteelChestplate));
					if(leggings < 50) event.entityLiving.setCurrentItemOrArmor(3, new ItemStack(SteelLeggings));
					if(boots < 50) event.entityLiving.setCurrentItemOrArmor(4, new ItemStack(SteelBoots));
				}
				else if(armorType == 4)
				{
					if(event.entityLiving instanceof EntityZombie && sword < 50) event.entityLiving.setCurrentItemOrArmor(0, new ItemStack(BronzeSword));
					if(helmet < 50) event.entityLiving.setCurrentItemOrArmor(1, new ItemStack(BronzeHelmet));
					if(chestplate < 50) event.entityLiving.setCurrentItemOrArmor(2, new ItemStack(BronzeChestplate));
					if(leggings < 50) event.entityLiving.setCurrentItemOrArmor(3, new ItemStack(BronzeLeggings));
					if(boots < 50) event.entityLiving.setCurrentItemOrArmor(4, new ItemStack(BronzeBoots));
				}
			}
		}
	}

	@Override
	public Version getVersion()
	{
		return versionNumber;
	}

	@Override
	public String getName() 
	{
		return "Tools";
	}

	@Override
	public void writeConfig(ByteBuf dataStream) {}

	@Override
	public void readConfig(ByteBuf dataStream) {}
}
