package mekanism.common.tag;

import com.google.common.collect.Table.Cell;
import java.util.Map;
import javax.annotation.Nullable;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.providers.IItemProvider;
import mekanism.common.Mekanism;
import mekanism.common.block.BlockOre;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.FluidRegistryObject;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.registration.impl.SlurryRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismEntityTypes;
import mekanism.common.registries.MekanismFluids;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismInfuseTypes;
import mekanism.common.registries.MekanismItems;
import mekanism.common.registries.MekanismSlurries;
import mekanism.common.registries.MekanismTileEntityTypes;
import mekanism.common.resource.OreType;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import mekanism.common.tags.MekanismTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MekanismTagProvider extends BaseTagProvider {

    public static final INamedTag<EntityType<?>> PVI_COMPAT = EntityTypeTags.bind("per-viam-invenire:replace_vanilla_navigator");
    public static final INamedTag<Fluid> CREATE_NO_INFINITE_FLUID = FluidTags.bind("create:no_infinite_draining");

    public MekanismTagProvider(DataGenerator gen, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, Mekanism.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        addProcessedResources();
        addBeaconTags();
        addBoxBlacklist();
        addWrenches();
        addToTag(MekanismTags.Items.BATTERIES, MekanismItems.ENERGY_TABLET);
        addToTag(MekanismTags.Items.YELLOW_CAKE_URANIUM, MekanismItems.YELLOW_CAKE_URANIUM);
        addRods();
        addFuels();
        addAlloys();
        addCircuits();
        addEndermanBlacklist();
        addEnriched();
        addChests();
        addOres();
        addStorageBlocks();
        addIngots();
        addNuggets();
        addDusts();
        addGems();
        addFluids();
        addGasTags();
        addSlurryTags();
        addInfuseTags();
        addPellets();
        addColorableItems();
        getBlockBuilder(MekanismTags.Blocks.ATOMIC_DISASSEMBLER_ORE).add(Tags.Blocks.ORES, BlockTags.LOGS);
        addToTag(BlockTags.GUARDED_BY_PIGLINS, MekanismBlocks.REFINED_GLOWSTONE_BLOCK, MekanismBlocks.PERSONAL_CHEST);
        addToTag(BlockTags.HOGLIN_REPELLENTS, MekanismBlocks.TELEPORTER, MekanismBlocks.QUANTUM_ENTANGLOPORTER);
        getItemBuilder(ItemTags.PIGLIN_LOVED).add(
              MekanismBlocks.REFINED_GLOWSTONE_BLOCK.getItem(),
              MekanismItems.REFINED_GLOWSTONE_INGOT.getItem(),
              MekanismItems.PROCESSED_RESOURCES.get(ResourceType.DUST, PrimaryResource.GOLD).getItem()
        ).add(
              MekanismTags.Items.ENRICHED_GOLD,
              MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.SHARD, PrimaryResource.GOLD),
              MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.CRYSTAL, PrimaryResource.GOLD),
              MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.DIRTY_DUST, PrimaryResource.GOLD),
              MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.CLUMP, PrimaryResource.GOLD)
        );
        addEntities();
        getBlockBuilder(MekanismTags.Blocks.MINER_BLACKLIST);
    }

    private void addEntities() {
        addToTag(EntityTypeTags.IMPACT_PROJECTILES, MekanismEntityTypes.FLAME);
        addToTag(PVI_COMPAT, MekanismEntityTypes.ROBIT);
    }

    private void addProcessedResources() {
        for (Cell<ResourceType, PrimaryResource, ItemRegistryObject<Item>> item : MekanismItems.PROCESSED_RESOURCES.cellSet()) {
            addToTag(item.getRowKey(), item.getColumnKey(), item.getValue());
            switch (item.getRowKey()) {
                case SHARD:
                    addToTag(MekanismTags.Items.SHARDS, item.getValue());
                    break;
                case CRYSTAL:
                    addToTag(MekanismTags.Items.CRYSTALS, item.getValue());
                    break;
                case DUST:
                    addToTag(Tags.Items.DUSTS, item.getValue());
                    break;
                case DIRTY_DUST:
                    addToTag(MekanismTags.Items.DIRTY_DUSTS, item.getValue());
                    break;
                case CLUMP:
                    addToTag(MekanismTags.Items.CLUMPS, item.getValue());
                    break;
                case INGOT:
                    addToTag(Tags.Items.INGOTS, item.getValue());
                    break;
                case NUGGET:
                    addToTag(Tags.Items.NUGGETS, item.getValue());
                    break;
                default:
                    break;
            }
        }
    }

    private void addToTag(ResourceType type, PrimaryResource resource, IItemProvider... items) {
        addToTag(MekanismTags.Items.PROCESSED_RESOURCES.get(type, resource), items);
    }

    private void addBeaconTags() {
        //Beacon bases
        addToTag(BlockTags.BEACON_BASE_BLOCKS,
              MekanismBlocks.PROCESSED_RESOURCE_BLOCKS.get(PrimaryResource.OSMIUM),
              MekanismBlocks.PROCESSED_RESOURCE_BLOCKS.get(PrimaryResource.COPPER),
              MekanismBlocks.PROCESSED_RESOURCE_BLOCKS.get(PrimaryResource.TIN),
              MekanismBlocks.PROCESSED_RESOURCE_BLOCKS.get(PrimaryResource.LEAD),
              MekanismBlocks.PROCESSED_RESOURCE_BLOCKS.get(PrimaryResource.URANIUM),
              MekanismBlocks.BRONZE_BLOCK,
              MekanismBlocks.REFINED_OBSIDIAN_BLOCK,
              MekanismBlocks.REFINED_GLOWSTONE_BLOCK,
              MekanismBlocks.STEEL_BLOCK
        );
        //Beacon payment items
        addToTag(ItemTags.BEACON_PAYMENT_ITEMS,
              MekanismItems.PROCESSED_RESOURCES.get(ResourceType.INGOT, PrimaryResource.OSMIUM),
              MekanismItems.PROCESSED_RESOURCES.get(ResourceType.INGOT, PrimaryResource.COPPER),
              MekanismItems.PROCESSED_RESOURCES.get(ResourceType.INGOT, PrimaryResource.TIN),
              MekanismItems.PROCESSED_RESOURCES.get(ResourceType.INGOT, PrimaryResource.LEAD),
              MekanismItems.PROCESSED_RESOURCES.get(ResourceType.INGOT, PrimaryResource.URANIUM),
              MekanismItems.BRONZE_INGOT,
              MekanismItems.REFINED_OBSIDIAN_INGOT,
              MekanismItems.REFINED_GLOWSTONE_INGOT,
              MekanismItems.STEEL_INGOT
        );
    }

    private void addBoxBlacklist() {
        addToTag(MekanismTags.Blocks.RELOCATION_NOT_SUPPORTED,
              MekanismBlocks.CARDBOARD_BOX,
              MekanismBlocks.BOUNDING_BLOCK,
              MekanismBlocks.ADVANCED_BOUNDING_BLOCK,
              MekanismBlocks.SECURITY_DESK,
              MekanismBlocks.DIGITAL_MINER,
              MekanismBlocks.SEISMIC_VIBRATOR,
              MekanismBlocks.SOLAR_NEUTRON_ACTIVATOR,
              MekanismBlocks.MODIFICATION_STATION,
              MekanismBlocks.ISOTOPIC_CENTRIFUGE,
              //Don't allow blocks that may have a radioactive substance in them to be picked up as it
              // will effectively dupe the radiation and also leak out into the atmosphere which is not
              // what people want, and means that it is likely someone miss clicked.
              MekanismBlocks.RADIOACTIVE_WASTE_BARREL,
              MekanismBlocks.PRESSURIZED_REACTION_CHAMBER,
              MekanismBlocks.BASIC_PRESSURIZED_TUBE,
              MekanismBlocks.ADVANCED_PRESSURIZED_TUBE,
              MekanismBlocks.ELITE_PRESSURIZED_TUBE,
              MekanismBlocks.ULTIMATE_PRESSURIZED_TUBE
        );
        getBlockBuilder(MekanismTags.Blocks.CARDBOARD_BLACKLIST)
              .add(MekanismTags.Blocks.RELOCATION_NOT_SUPPORTED, BlockTags.BEDS, BlockTags.DOORS);
        TileEntityTypeRegistryObject<?>[] tilesToBlacklist = {
              MekanismTileEntityTypes.CARDBOARD_BOX,
              MekanismTileEntityTypes.BOUNDING_BLOCK,
              MekanismTileEntityTypes.ADVANCED_BOUNDING_BLOCK,
              MekanismTileEntityTypes.SECURITY_DESK,
              MekanismTileEntityTypes.DIGITAL_MINER,
              MekanismTileEntityTypes.SEISMIC_VIBRATOR,
              MekanismTileEntityTypes.SOLAR_NEUTRON_ACTIVATOR,
              MekanismTileEntityTypes.MODIFICATION_STATION,
              MekanismTileEntityTypes.ISOTOPIC_CENTRIFUGE,
              //Don't allow blocks that may have a radioactive substance in them to be picked up as it
              // will effectively dupe the radiation and also leak out into the atmosphere which is not
              // what people want, and means that it is likely someone miss clicked.
              MekanismTileEntityTypes.RADIOACTIVE_WASTE_BARREL,
              MekanismTileEntityTypes.PRESSURIZED_REACTION_CHAMBER,
              MekanismTileEntityTypes.BASIC_PRESSURIZED_TUBE,
              MekanismTileEntityTypes.ADVANCED_PRESSURIZED_TUBE,
              MekanismTileEntityTypes.ELITE_PRESSURIZED_TUBE,
              MekanismTileEntityTypes.ULTIMATE_PRESSURIZED_TUBE
        };
        addToTag(MekanismTags.TileEntityTypes.IMMOVABLE, tilesToBlacklist);
        addToTag(MekanismTags.TileEntityTypes.RELOCATION_NOT_SUPPORTED, tilesToBlacklist);
        getTileEntityTypeBuilder(MekanismTags.TileEntityTypes.CARDBOARD_BLACKLIST)
              .add(MekanismTags.TileEntityTypes.IMMOVABLE, MekanismTags.TileEntityTypes.RELOCATION_NOT_SUPPORTED);
    }

    private void addWrenches() {
        addToTag(MekanismTags.Items.WRENCHES, MekanismItems.CONFIGURATOR);
        getItemBuilder(MekanismTags.Items.TOOLS).add(MekanismTags.Items.TOOLS_WRENCH);
        addToTag(MekanismTags.Items.TOOLS_WRENCH, MekanismItems.CONFIGURATOR);
        getItemBuilder(MekanismTags.Items.CONFIGURATORS).add(MekanismTags.Items.WRENCHES, MekanismTags.Items.TOOLS_WRENCH);
    }

    private void addRods() {
        addToTag(MekanismTags.Items.RODS_PLASTIC, MekanismItems.HDPE_STICK);
        getItemBuilder(Tags.Items.RODS).add(MekanismTags.Items.RODS_PLASTIC);
    }

    private void addFuels() {
        addToTag(MekanismTags.Items.FUELS_BIO, MekanismItems.BIO_FUEL);
        getItemBuilder(MekanismTags.Items.FUELS).add(MekanismTags.Items.FUELS_BIO);
    }

    private void addAlloys() {
        //Alloys Tags that go in the forge domain
        addToTag(MekanismTags.Items.ALLOYS_ADVANCED, MekanismItems.INFUSED_ALLOY);
        addToTag(MekanismTags.Items.ALLOYS_ELITE, MekanismItems.REINFORCED_ALLOY);
        addToTag(MekanismTags.Items.ALLOYS_ULTIMATE, MekanismItems.ATOMIC_ALLOY);
        getItemBuilder(MekanismTags.Items.FORGE_ALLOYS).add(MekanismTags.Items.ALLOYS_ADVANCED, MekanismTags.Items.ALLOYS_ELITE, MekanismTags.Items.ALLOYS_ULTIMATE);
        //Alloy tags that go in our domain
        addToTag(MekanismTags.Items.ALLOYS_BASIC, Items.REDSTONE);
        getItemBuilder(MekanismTags.Items.ALLOYS_INFUSED).add(MekanismTags.Items.ALLOYS_ADVANCED);
        getItemBuilder(MekanismTags.Items.ALLOYS_REINFORCED).add(MekanismTags.Items.ALLOYS_ELITE);
        getItemBuilder(MekanismTags.Items.ALLOYS_ATOMIC).add(MekanismTags.Items.ALLOYS_ULTIMATE);
        getItemBuilder(MekanismTags.Items.ALLOYS).add(MekanismTags.Items.ALLOYS_BASIC, MekanismTags.Items.ALLOYS_INFUSED, MekanismTags.Items.ALLOYS_REINFORCED,
              MekanismTags.Items.ALLOYS_ATOMIC);
    }

    private void addCircuits() {
        addToTag(MekanismTags.Items.CIRCUITS_BASIC, MekanismItems.BASIC_CONTROL_CIRCUIT);
        addToTag(MekanismTags.Items.CIRCUITS_ADVANCED, MekanismItems.ADVANCED_CONTROL_CIRCUIT);
        addToTag(MekanismTags.Items.CIRCUITS_ELITE, MekanismItems.ELITE_CONTROL_CIRCUIT);
        addToTag(MekanismTags.Items.CIRCUITS_ULTIMATE, MekanismItems.ULTIMATE_CONTROL_CIRCUIT);
        getItemBuilder(MekanismTags.Items.CIRCUITS).add(MekanismTags.Items.CIRCUITS_BASIC, MekanismTags.Items.CIRCUITS_ADVANCED, MekanismTags.Items.CIRCUITS_ELITE,
              MekanismTags.Items.CIRCUITS_ULTIMATE);
    }

    private void addEndermanBlacklist() {
        addToTag(Tags.Blocks.ENDERMAN_PLACE_ON_BLACKLIST,
              MekanismBlocks.DYNAMIC_TANK,
              MekanismBlocks.DYNAMIC_VALVE,
              MekanismBlocks.BOILER_CASING,
              MekanismBlocks.BOILER_VALVE,
              MekanismBlocks.PRESSURE_DISPERSER,
              MekanismBlocks.SUPERHEATING_ELEMENT,
              MekanismBlocks.INDUCTION_CASING,
              MekanismBlocks.INDUCTION_PORT,
              MekanismBlocks.THERMAL_EVAPORATION_CONTROLLER,
              MekanismBlocks.THERMAL_EVAPORATION_VALVE,
              MekanismBlocks.THERMAL_EVAPORATION_BLOCK,
              MekanismBlocks.STRUCTURAL_GLASS,
              MekanismBlocks.SPS_CASING,
              MekanismBlocks.SPS_PORT,
              MekanismBlocks.SUPERCHARGED_COIL
        );
    }

    private void addEnriched() {
        addToTag(MekanismTags.Items.ENRICHED_CARBON, MekanismItems.ENRICHED_CARBON);
        addToTag(MekanismTags.Items.ENRICHED_DIAMOND, MekanismItems.ENRICHED_DIAMOND);
        addToTag(MekanismTags.Items.ENRICHED_OBSIDIAN, MekanismItems.ENRICHED_OBSIDIAN);
        addToTag(MekanismTags.Items.ENRICHED_REDSTONE, MekanismItems.ENRICHED_REDSTONE);
        addToTag(MekanismTags.Items.ENRICHED_GOLD, MekanismItems.ENRICHED_GOLD);
        addToTag(MekanismTags.Items.ENRICHED_TIN, MekanismItems.ENRICHED_TIN);
        getItemBuilder(MekanismTags.Items.ENRICHED).add(MekanismTags.Items.ENRICHED_CARBON, MekanismTags.Items.ENRICHED_DIAMOND, MekanismTags.Items.ENRICHED_OBSIDIAN,
              MekanismTags.Items.ENRICHED_REDSTONE, MekanismTags.Items.ENRICHED_GOLD, MekanismTags.Items.ENRICHED_TIN);
    }

    private void addChests() {
        addToTag(MekanismTags.Blocks.CHESTS_ELECTRIC, MekanismBlocks.PERSONAL_CHEST);
        addToTag(MekanismTags.Blocks.CHESTS_PERSONAL, MekanismBlocks.PERSONAL_CHEST);
        getBlockBuilder(Tags.Blocks.CHESTS).add(MekanismTags.Blocks.CHESTS_ELECTRIC, MekanismTags.Blocks.CHESTS_PERSONAL);
    }

    private void addOres() {
        for (Map.Entry<OreType, BlockRegistryObject<BlockOre, ?>> entry : MekanismBlocks.ORES.entrySet()) {
            addToTags(MekanismTags.Items.ORES.get(entry.getKey()), MekanismTags.Blocks.ORES.get(entry.getKey()), entry.getValue());
            getItemBuilder(Tags.Items.ORES).add(MekanismTags.Items.ORES.get(entry.getKey()));
            getBlockBuilder(Tags.Blocks.ORES).add(MekanismTags.Blocks.ORES.get(entry.getKey()));
        }
    }

    private void addStorageBlocks() {
        addToTags(MekanismTags.Items.STORAGE_BLOCKS_BRONZE, MekanismTags.Blocks.STORAGE_BLOCKS_BRONZE, MekanismBlocks.BRONZE_BLOCK);
        addToTags(MekanismTags.Items.STORAGE_BLOCKS_CHARCOAL, MekanismTags.Blocks.STORAGE_BLOCKS_CHARCOAL, MekanismBlocks.CHARCOAL_BLOCK);
        addToTags(MekanismTags.Items.STORAGE_BLOCKS_REFINED_GLOWSTONE, MekanismTags.Blocks.STORAGE_BLOCKS_REFINED_GLOWSTONE, MekanismBlocks.REFINED_GLOWSTONE_BLOCK);
        addToTags(MekanismTags.Items.STORAGE_BLOCKS_REFINED_OBSIDIAN, MekanismTags.Blocks.STORAGE_BLOCKS_REFINED_OBSIDIAN, MekanismBlocks.REFINED_OBSIDIAN_BLOCK);
        addToTags(MekanismTags.Items.STORAGE_BLOCKS_STEEL, MekanismTags.Blocks.STORAGE_BLOCKS_STEEL, MekanismBlocks.STEEL_BLOCK);
        getItemBuilder(Tags.Items.STORAGE_BLOCKS).add(MekanismTags.Items.STORAGE_BLOCKS_BRONZE, MekanismTags.Items.STORAGE_BLOCKS_CHARCOAL,
              MekanismTags.Items.STORAGE_BLOCKS_REFINED_GLOWSTONE, MekanismTags.Items.STORAGE_BLOCKS_REFINED_OBSIDIAN, MekanismTags.Items.STORAGE_BLOCKS_STEEL);
        getBlockBuilder(Tags.Blocks.STORAGE_BLOCKS).add(MekanismTags.Blocks.STORAGE_BLOCKS_BRONZE, MekanismTags.Blocks.STORAGE_BLOCKS_CHARCOAL,
              MekanismTags.Blocks.STORAGE_BLOCKS_REFINED_GLOWSTONE, MekanismTags.Blocks.STORAGE_BLOCKS_REFINED_OBSIDIAN, MekanismTags.Blocks.STORAGE_BLOCKS_STEEL);
        // Dynamic storage blocks
        for (Map.Entry<PrimaryResource, BlockRegistryObject<?, ?>> entry : MekanismBlocks.PROCESSED_RESOURCE_BLOCKS.entrySet()) {
            addToTags(MekanismTags.Items.PROCESSED_RESOURCE_BLOCKS.get(entry.getKey()), MekanismTags.Blocks.RESOURCE_STORAGE_BLOCKS.get(entry.getKey()), entry.getValue());
            getItemBuilder(Tags.Items.STORAGE_BLOCKS).add(MekanismTags.Items.PROCESSED_RESOURCE_BLOCKS.get(entry.getKey()));
            getBlockBuilder(Tags.Blocks.STORAGE_BLOCKS).add(MekanismTags.Blocks.RESOURCE_STORAGE_BLOCKS.get(entry.getKey()));
        }
    }

    private void addIngots() {
        addToTag(MekanismTags.Items.INGOTS_BRONZE, MekanismItems.BRONZE_INGOT);
        addToTag(MekanismTags.Items.INGOTS_REFINED_GLOWSTONE, MekanismItems.REFINED_GLOWSTONE_INGOT);
        addToTag(MekanismTags.Items.INGOTS_REFINED_OBSIDIAN, MekanismItems.REFINED_OBSIDIAN_INGOT);
        addToTag(MekanismTags.Items.INGOTS_STEEL, MekanismItems.STEEL_INGOT);
        getItemBuilder(Tags.Items.INGOTS).add(MekanismTags.Items.INGOTS_BRONZE,
              MekanismTags.Items.INGOTS_REFINED_GLOWSTONE, MekanismTags.Items.INGOTS_REFINED_OBSIDIAN, MekanismTags.Items.INGOTS_STEEL);
    }

    private void addNuggets() {
        addToTag(MekanismTags.Items.NUGGETS_BRONZE, MekanismItems.BRONZE_NUGGET);
        addToTag(MekanismTags.Items.NUGGETS_REFINED_GLOWSTONE, MekanismItems.REFINED_GLOWSTONE_NUGGET);
        addToTag(MekanismTags.Items.NUGGETS_REFINED_OBSIDIAN, MekanismItems.REFINED_OBSIDIAN_NUGGET);
        addToTag(MekanismTags.Items.NUGGETS_STEEL, MekanismItems.STEEL_NUGGET);
        getItemBuilder(Tags.Items.NUGGETS).add(MekanismTags.Items.NUGGETS_BRONZE,
              MekanismTags.Items.NUGGETS_REFINED_GLOWSTONE, MekanismTags.Items.NUGGETS_REFINED_OBSIDIAN, MekanismTags.Items.NUGGETS_STEEL);
    }

    private void addDusts() {
        addToTag(MekanismTags.Items.DUSTS_BRONZE, MekanismItems.BRONZE_DUST);
        addToTag(MekanismTags.Items.DUSTS_CHARCOAL, MekanismItems.CHARCOAL_DUST);
        addToTag(MekanismTags.Items.DUSTS_COAL, MekanismItems.COAL_DUST);
        addToTag(MekanismTags.Items.DUSTS_DIAMOND, MekanismItems.DIAMOND_DUST);
        addToTag(MekanismTags.Items.DUSTS_EMERALD, MekanismItems.EMERALD_DUST);
        addToTag(MekanismTags.Items.DUSTS_NETHERITE, MekanismItems.NETHERITE_DUST);
        addToTag(MekanismTags.Items.DUSTS_LAPIS, MekanismItems.LAPIS_LAZULI_DUST);
        addToTag(MekanismTags.Items.DUSTS_LITHIUM, MekanismItems.LITHIUM_DUST);
        addToTag(MekanismTags.Items.DUSTS_OBSIDIAN, MekanismItems.OBSIDIAN_DUST);
        addToTag(MekanismTags.Items.DUSTS_QUARTZ, MekanismItems.QUARTZ_DUST);
        addToTag(MekanismTags.Items.DUSTS_REFINED_OBSIDIAN, MekanismItems.REFINED_OBSIDIAN_DUST);
        addToTag(MekanismTags.Items.DUSTS_SALT, MekanismItems.SALT);
        addToTag(MekanismTags.Items.DUSTS_STEEL, MekanismItems.STEEL_DUST);
        addToTag(MekanismTags.Items.DUSTS_SULFUR, MekanismItems.SULFUR_DUST);
        addToTag(MekanismTags.Items.DUSTS_WOOD, MekanismItems.SAWDUST);
        addToTag(MekanismTags.Items.DUSTS_FLUORITE, MekanismItems.FLUORITE_DUST);
        getItemBuilder(Tags.Items.DUSTS).add(MekanismTags.Items.DUSTS_BRONZE, MekanismTags.Items.DUSTS_CHARCOAL, MekanismTags.Items.DUSTS_COAL,
              MekanismTags.Items.DUSTS_DIAMOND, MekanismTags.Items.DUSTS_EMERALD, MekanismTags.Items.DUSTS_NETHERITE, MekanismTags.Items.DUSTS_LAPIS,
              MekanismTags.Items.DUSTS_LITHIUM, MekanismTags.Items.DUSTS_OBSIDIAN, MekanismTags.Items.DUSTS_QUARTZ, MekanismTags.Items.DUSTS_REFINED_OBSIDIAN,
              MekanismTags.Items.DUSTS_SALT, MekanismTags.Items.DUSTS_STEEL, MekanismTags.Items.DUSTS_SULFUR, MekanismTags.Items.DUSTS_WOOD,
              MekanismTags.Items.DUSTS_FLUORITE);

        addToTag(Tags.Items.DYES_YELLOW, MekanismItems.SULFUR_DUST);

        getItemBuilder(MekanismTags.Items.SALT).add(MekanismTags.Items.DUSTS_SALT);
        getItemBuilder(MekanismTags.Items.SAWDUST).add(MekanismTags.Items.DUSTS_WOOD);
    }

    private void addGems() {
        addToTag(MekanismTags.Items.GEMS_FLUORITE, MekanismItems.FLUORITE_GEM);
    }

    private void addPellets() {
        addToTag(MekanismTags.Items.PELLETS_ANTIMATTER, MekanismItems.ANTIMATTER_PELLET);
        addToTag(MekanismTags.Items.PELLETS_PLUTONIUM, MekanismItems.PLUTONIUM_PELLET);
        addToTag(MekanismTags.Items.PELLETS_POLONIUM, MekanismItems.POLONIUM_PELLET);
    }

    private void addColorableItems() {
        addToTag(MekanismTags.Items.COLORABLE_WOOL, Blocks.WHITE_WOOL, Blocks.ORANGE_WOOL, Blocks.MAGENTA_WOOL, Blocks.LIGHT_BLUE_WOOL, Blocks.YELLOW_WOOL,
              Blocks.LIME_WOOL, Blocks.PINK_WOOL, Blocks.GRAY_WOOL, Blocks.LIGHT_GRAY_WOOL, Blocks.CYAN_WOOL, Blocks.PURPLE_WOOL, Blocks.BLUE_WOOL, Blocks.BROWN_WOOL,
              Blocks.GREEN_WOOL, Blocks.RED_WOOL, Blocks.BLACK_WOOL);
        addToTag(MekanismTags.Items.COLORABLE_CARPETS, Blocks.WHITE_CARPET, Blocks.ORANGE_CARPET, Blocks.MAGENTA_CARPET, Blocks.LIGHT_BLUE_CARPET, Blocks.YELLOW_CARPET,
              Blocks.LIME_CARPET, Blocks.PINK_CARPET, Blocks.GRAY_CARPET, Blocks.LIGHT_GRAY_CARPET, Blocks.CYAN_CARPET, Blocks.PURPLE_CARPET, Blocks.BLUE_CARPET,
              Blocks.BROWN_CARPET, Blocks.GREEN_CARPET, Blocks.RED_CARPET, Blocks.BLACK_CARPET);
        addToTag(MekanismTags.Items.COLORABLE_BEDS, Blocks.WHITE_BED, Blocks.ORANGE_BED, Blocks.MAGENTA_BED, Blocks.LIGHT_BLUE_BED, Blocks.YELLOW_BED,
              Blocks.LIME_BED, Blocks.PINK_BED, Blocks.GRAY_BED, Blocks.LIGHT_GRAY_BED, Blocks.CYAN_BED, Blocks.PURPLE_BED, Blocks.BLUE_BED, Blocks.BROWN_BED,
              Blocks.GREEN_BED, Blocks.RED_BED, Blocks.BLACK_BED);
        addToTag(MekanismTags.Items.COLORABLE_GLASS, Blocks.GLASS, Blocks.WHITE_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS,
              Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS, Blocks.LIME_STAINED_GLASS, Blocks.PINK_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS,
              Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS,
              Blocks.GREEN_STAINED_GLASS, Blocks.RED_STAINED_GLASS, Blocks.BLACK_STAINED_GLASS);
        addToTag(MekanismTags.Items.COLORABLE_GLASS_PANES, Blocks.GLASS_PANE, Blocks.WHITE_STAINED_GLASS_PANE, Blocks.ORANGE_STAINED_GLASS_PANE,
              Blocks.MAGENTA_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS_PANE, Blocks.LIME_STAINED_GLASS_PANE,
              Blocks.PINK_STAINED_GLASS_PANE, Blocks.GRAY_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS_PANE,
              Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.BLUE_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.GREEN_STAINED_GLASS_PANE,
              Blocks.RED_STAINED_GLASS_PANE, Blocks.BLACK_STAINED_GLASS_PANE);
        addToTag(MekanismTags.Items.COLORABLE_TERRACOTTA, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA,
              Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA,
              Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA,
              Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA);
        addToTag(MekanismTags.Items.COLORABLE_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.ORANGE_CONCRETE, Blocks.MAGENTA_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE,
              Blocks.YELLOW_CONCRETE, Blocks.LIME_CONCRETE, Blocks.PINK_CONCRETE, Blocks.GRAY_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE, Blocks.CYAN_CONCRETE,
              Blocks.PURPLE_CONCRETE, Blocks.BLUE_CONCRETE, Blocks.BROWN_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.RED_CONCRETE, Blocks.BLACK_CONCRETE);
        addToTag(MekanismTags.Items.COLORABLE_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER,
              Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER,
              Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER,
              Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER);
    }

    private void addFluids() {
        addToTag(MekanismTags.Fluids.BRINE, MekanismFluids.BRINE);
        addToTag(MekanismTags.Fluids.CHLORINE, MekanismFluids.CHLORINE);
        addToTag(MekanismTags.Fluids.ETHENE, MekanismFluids.ETHENE);
        addToTag(MekanismTags.Fluids.HEAVY_WATER, MekanismFluids.HEAVY_WATER);
        addToTag(MekanismTags.Fluids.HYDROGEN, MekanismFluids.HYDROGEN);
        addToTag(MekanismTags.Fluids.HYDROGEN_CHLORIDE, MekanismFluids.HYDROGEN_CHLORIDE);
        addToTag(MekanismTags.Fluids.LITHIUM, MekanismFluids.LITHIUM);
        addToTag(MekanismTags.Fluids.OXYGEN, MekanismFluids.OXYGEN);
        addToTag(MekanismTags.Fluids.SODIUM, MekanismFluids.SODIUM);
        addToTag(MekanismTags.Fluids.STEAM, MekanismFluids.STEAM);
        addToTag(MekanismTags.Fluids.SULFUR_DIOXIDE, MekanismFluids.SULFUR_DIOXIDE);
        addToTag(MekanismTags.Fluids.SULFUR_TRIOXIDE, MekanismFluids.SULFUR_TRIOXIDE);
        addToTag(MekanismTags.Fluids.SULFURIC_ACID, MekanismFluids.SULFURIC_ACID);
        addToTag(MekanismTags.Fluids.HYDROFLUORIC_ACID, MekanismFluids.HYDROFLUORIC_ACID);
        //Prevent all our fluids from being duped by create
        for (FluidRegistryObject<?, ?, ?, ?> fluid : MekanismFluids.FLUIDS.getAllFluids()) {
            addToTag(CREATE_NO_INFINITE_FLUID, fluid);
        }
    }

    private void addGasTags() {
        addToTag(MekanismTags.Gases.WATER_VAPOR, MekanismGases.WATER_VAPOR, MekanismGases.STEAM);
        addToTag(MekanismTags.Gases.WASTE_BARREL_DECAY_BLACKLIST, MekanismGases.PLUTONIUM, MekanismGases.POLONIUM);
    }

    private void addSlurryTags(SlurryRegistryObject<?, ?>... slurryRegistryObjects) {
        ForgeRegistryTagBuilder<Slurry> dirtyTagBuilder = getSlurryBuilder(MekanismTags.Slurries.DIRTY);
        ForgeRegistryTagBuilder<Slurry> cleanTagBuilder = getSlurryBuilder(MekanismTags.Slurries.CLEAN);
        for (SlurryRegistryObject<?, ?> slurryRO : slurryRegistryObjects) {
            dirtyTagBuilder.add(slurryRO.getDirtySlurry());
            cleanTagBuilder.add(slurryRO.getCleanSlurry());
        }
        // add dynamic slurry tags
        for (SlurryRegistryObject<?, ?> slurryRO : MekanismSlurries.PROCESSED_RESOURCES.values()) {
            dirtyTagBuilder.add(slurryRO.getDirtySlurry());
            cleanTagBuilder.add(slurryRO.getCleanSlurry());
        }
    }

    private void addInfuseTags() {
        addToTag(MekanismTags.InfuseTypes.CARBON, MekanismInfuseTypes.CARBON);
        addToTag(MekanismTags.InfuseTypes.REDSTONE, MekanismInfuseTypes.REDSTONE);
        addToTag(MekanismTags.InfuseTypes.DIAMOND, MekanismInfuseTypes.DIAMOND);
        addToTag(MekanismTags.InfuseTypes.REFINED_OBSIDIAN, MekanismInfuseTypes.REFINED_OBSIDIAN);
        addToTag(MekanismTags.InfuseTypes.GOLD, MekanismInfuseTypes.GOLD);
        addToTag(MekanismTags.InfuseTypes.TIN, MekanismInfuseTypes.TIN);
        addToTag(MekanismTags.InfuseTypes.FUNGI, MekanismInfuseTypes.FUNGI);
        addToTag(MekanismTags.InfuseTypes.BIO, MekanismInfuseTypes.BIO);
    }
}