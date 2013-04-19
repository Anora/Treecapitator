package bspkrs.treecapitator;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTextureTile;
import net.minecraft.item.ItemStack;
import bspkrs.util.BlockID;
import bspkrs.util.CommonUtils;
import bspkrs.util.Coord;

public final class TreeCapitator
{
    public final static String                         VERSION_NUMBER                     = "1.5.1.r02";
    
    public final static String                         remoteTreeConfigURLDesc            = "Incomplete - do not use";
    // "Leave this URL as is to get the latest tree definitions from my master list.\nFeel free to start your own remote list to share with your friends or send your suggestions to me for the master list!";
    public static String                               remoteTreeConfigURL                = "http://bspk.rs/Minecraft/1.5.1/treeCapitatorTreeConfig.txt";
    public final static String                         remoteBlockIDConfigDesc            = "Incomplete - do not use";
    // "Values downloaded from: " + remoteTreeConfigURL;
    public static String                               remoteBlockIDConfig                = "";
    public final static String                         localBlockIDListDesc               = "Automatically generated:";
    public static String                               localBlockIDList                   = "";
    public final static String                         useRemoteTreeConfigDesc            = "Incomplete - do not use";
    // "Set to true to use the remote block ID list (must also set allowGetOnlineTreeConfig to true), false to use local config.";
    public static boolean                              useRemoteTreeConfig                = false;
    public final static String                         allowGetRemoteTreeConfigDesc       = "Incomplete - do not use";
    // "Set to true to allow TreeCapitator to retrieve the remote block ID list, false to disable.";
    public static boolean                              allowGetRemoteTreeConfig           = false;
    
    public final static String                         enableEnchantmentModeDesc          = "Toggle for whether or not to use the Treecapitating enchantment as opposed to requiring an item to be in the axeIDList to chop a tree.";
    public static boolean                              enableEnchantmentMode              = false;
    public final static String                         requireItemInAxeListForEnchantDesc = "Whether or not to check axeIDList for an item when determining if a given item can be imbued with the Treecapitating enchantment.\n" +
                                                                                                  "NOTE: when set to false, any ItemTool type item (pickaxes, shovels, etc) with a high enough enchantability level can get the enchantment, not just axes.";
    public static boolean                              requireItemInAxeListForEnchant     = false;
    public final static String                         axeIDListDesc                      = "IDs of items that can chop down trees. Use ',' to split item id from metadata and ';' to split items.";
    public static String                               axeIDList                          = Item.axeWood.itemID + "; " + Item.axeStone.itemID + "; " + Item.axeIron.itemID + "; " + Item.axeGold.itemID + "; " + Item.axeDiamond.itemID;
    public final static String                         needItemDesc                       = "Whether you need an item from the axeIDList to chop down a tree. Disabling will let you chop trees with any item.";
    public static boolean                              needItem                           = true;
    public final static String                         onlyDestroyUpwardsDesc             = "Setting this to false will allow the chopping to move downward as well as upward (and blocks below the one you break will be chopped)";
    public static boolean                              onlyDestroyUpwards                 = true;
    public final static String                         destroyLeavesDesc                  = "Enabling this will make leaves be destroyed when trees are chopped.";
    public static boolean                              destroyLeaves                      = true;
    public final static String                         requireLeafDecayCheckDesc          = "When true TreeCapitator will only instantly decay leaves that have actually been marked for decay.\n" +
                                                                                                  "Set to false if you want leaves to be destroyed regardless of their decay status (hint: or for \"leaf\" blocks that are not really leaves).";
    public static boolean                              requireLeafDecayCheck              = true;
    public final static String                         shearLeavesDesc                    = "Enabling this will cause destroyed leaves to be sheared when a shearing item is in the hotbar (ignored if destroyLeaves is false).";
    public static boolean                              shearLeaves                        = false;
    public final static String                         shearVinesDesc                     = "Enabling this will shear /some/ of the vines on a tree when a shearing item is in the hotbar (ignored if destroyLeaves is false).";
    public static boolean                              shearVines                         = false;
    public final static String                         shearIDListDesc                    = "IDs of items that when placed in the hotbar will allow leaves to be sheared when shearLeaves is true.\n" +
                                                                                                  "Use ',' to split item id from metadata and ';' to split items.";
    public static String                               shearIDList                        = Item.shears.itemID + "";
    public final static String                         logHardnessNormalDesc              = "The hardness of Strings.LOGS for when you are using items that won't chop down the trees.";
    public static float                                logHardnessNormal                  = 2.0F;
    public final static String                         logHardnessModifiedDesc            = "The hardness of Strings.LOGS for when you are using items that can chop down trees.";
    public static float                                logHardnessModified                = 4.0F;
    public final static String                         disableInCreativeDesc              = "Flag to disable tree chopping in Creative mode";
    public static boolean                              disableInCreative                  = false;
    public final static String                         disableCreativeDropsDesc           = "Flag to disable drops in Creative mode";
    public static boolean                              disableCreativeDrops               = false;
    public final static String                         allowItemDamageDesc                = "Enable to cause item damage based on number of blocks destroyed";
    public static boolean                              allowItemDamage                    = true;
    public final static String                         allowMoreBlocksThanDamageDesc      = "Enable to allow chopping down the entire tree even if your item does not have enough damage remaining to cover the number of blocks.";
    public static boolean                              allowMoreBlocksThanDamage          = false;
    public final static String                         damageMultiplierDesc               = "Axes and shears will take damage this many times for each log broken.\n" +
                                                                                                  "Remaining damage is rounded and applied to tools when a tree is finished.";
    public static float                                damageMultiplier                   = 1.0F;
    public final static String                         useIncreasingItemDamageDesc        = "Set to true to have the per-block item damage amount increase after every increaseDamageEveryXBlocks blocks are broken.";
    public static boolean                              useIncreasingItemDamage            = false;
    public final static String                         increaseDamageEveryXBlocksDesc     = "When useIncreasingItemDamage=true the damage applied per block broken will increase each time this many blocks are broken in a tree.";
    public static int                                  increaseDamageEveryXBlocks         = 15;
    public final static String                         damageIncreaseAmountDesc           = "When useIncreasingItemDamage=true the damage applied per block broken will increase by this amount every increaseDamageEveryXBlocks blocks broken in a tree.";
    public static float                                damageIncreaseAmount               = 1.0F;
    public final static String                         sneakActionDesc                    = "Set sneakAction = \"disable\" to disable tree chopping while sneaking,\n" +
                                                                                                  "set sneakAction = \"enable\" to only enable tree chopping while sneaking,\n" +
                                                                                                  "set sneakAction = \"none\" to have tree chopping enabled regardless of sneaking.";
    public static String                               sneakAction                        = "disable";
    public final static String                         maxBreakDistanceDesc               = "The maximum horizontal distance that the log breaking effect will travel (use -1 for no limit).";
    public static int                                  maxBreakDistance                   = 16;
    public final static String                         allowSmartTreeDetectionDesc        = "Set to false to disable TreeCapitator Smart Tree Detection.\n" +
                                                                                                  "Smart Tree Detection counts the number of leaf blocks that are adjacent to the\n" +
                                                                                                  "top-most connected log block at the x, z location of a log you've broken. If\n" +
                                                                                                  "there are at least minLeavesToID leaf blocks within maxLeafIDDist blocks then\n" +
                                                                                                  "TreeCapitator considers it a tree and allows chopping.\n" +
                                                                                                  "WARNING: Disabling Smart Tree Detection will remove the only safeguard against\n" +
                                                                                                  "accidentally destroying a log structure.  Make sure you know what you're doing!";
    public static boolean                              allowSmartTreeDetection            = true;
    public final static String                         maxLeafIDDistDesc                  = "If a tree's top log is not close enough to leaf blocks, the tree will not be chopped.\n" +
                                                                                                  "Increasing this value will search further.  I would try to keep it below 3.";
    public static int                                  maxLeafIDDist                      = 1;
    public final static String                         maxLeafBreakDistDesc               = "The maximum distance to instantly decay leaves from any log block that is removed by TreeCapitator.";
    public static int                                  maxLeafBreakDist                   = 4;
    public final static String                         minLeavesToIDDesc                  = "The minimum number of leaves within maxLeafIDDist of the top log block required to identify a tree.";
    public static int                                  minLeavesToID                      = 3;
    public final static String                         useStrictBlockPairingDesc          = "Set to true if you want only the leaf blocks listed with each log in blockIDList\n"
                                                                                                  + "to break when that log type is chopped.  When set to false it will break\n"
                                                                                                  + "any leaf type within range of the tree, not just the type for that tree.";
    public static boolean                              useStrictBlockPairing              = false;
    
    public final static String                         allowDebugOutputDesc               = "Set to true if you want TreeCapitator to tell you what kind of block you have clicked when sneaking, false to disable.";
    public static boolean                              allowDebugOutput                   = false;
    public final static String                         allowDebugLoggingDesc              = "Set to true if you want TreeCapitator to log info about what it's doing, false to disable";
    public static boolean                              allowDebugLogging                  = false;
    
    public static boolean                              isForge                            = false;
    public static Block                                wood;                                                                                                                                                                                                  ;
    
    public static ArrayList<BlockID>                   logIDList                          = new ArrayList<BlockID>();
    public static ArrayList<BlockID>                   leafIDList                         = new ArrayList<BlockID>();
    public static Map<BlockID, ArrayList<BlockID>>     logToLeafListMap                   = new HashMap<BlockID, ArrayList<BlockID>>();
    public static Map<BlockID, ArrayList<BlockID>>     logToLogListMap                    = new HashMap<BlockID, ArrayList<BlockID>>();
    public static Map<String, HashMap<String, String>> configBlockList                    = new HashMap<String, HashMap<String, String>>();
    public static Map<String, HashMap<String, String>> thirdPartyConfig                   = new HashMap<String, HashMap<String, String>>();
    public static Map<String, String>                  tagMap                             = new HashMap<String, String>();
    public static ArrayList<Coord>                     blocksBeingChopped                 = new ArrayList<Coord>();
    
    public static final String                         configBlockIDDesc                  = "Add the log and leaf block IDs for all trees you want to be able to chop down.\n" +
                                                                                                  "Each section below represents a type of tree.  Each list may contain block IDs\n" +
                                                                                                  "and/or third-party config replacement tags. You can change it to be more or\n" +
                                                                                                  "less granular as long as all sections follow the basic structure.  Do not use\n" +
                                                                                                  "spaces or periods in your section names.  Otherwise you can call them anything\n" +
                                                                                                  "you like.\n\n" +
                                                                                                  "EACH LOG ID MAY ONLY APPEAR IN EXACTLY ONE SECTION.\n\n" +
                                                                                                  "NOTE: Some mod trees use vanilla log blocks as well as custom blocks.  If a tree\n" +
                                                                                                  "contains more than 1 type of log, all Strings.LOGS must be included in the same section.\n" +
                                                                                                  "Examples of this are the default entries for vanilla_ebxl_oaks and vanilla_ebxl_spruces.\n\n" +
                                                                                                  "Simple Example (all Strings.LOGS and leaves are grouped in one section, no metadata is specified):\n" +
                                                                                                  "    trees {\n" +
                                                                                                  "        S:leaves=18; <Forestry.leaves>; <ExtrabiomesXL.autumnleaves.id>; <ExtrabiomesXL.greenleaves.id>\n" +
                                                                                                  "        S:Strings.LOGS=17; <Forestry.log1>; <Forestry.log2>; <Forestry.log3>; <Forestry.log4>; <ExtrabiomesXL.customlog.id>; <ExtrabiomesXL.quarterlog0.id>; <ExtrabiomesXL.quarterlog1.id>; <ExtrabiomesXL.quarterlog2.id>;<ExtrabiomesXL.quarterlog3.id>\n" +
                                                                                                  "    }\n\n" +
                                                                                                  "Advanced Example (each mod tree has its own section, metadata is included):\n" +
                                                                                                  "    vanilla_ebxl_oaks {\n" +
                                                                                                  "        S:leaves=18,0\n" +
                                                                                                  "        S:Strings.LOGS=17,0; 17,4; 17,8; <ExtrabiomesXL.quarterlog0.id>,2; <ExtrabiomesXL.quarterlog1.id>,2; <ExtrabiomesXL.quarterlog2.id>,2;<ExtrabiomesXL.quarterlog3.id>,2;\n" +
                                                                                                  "    }\n\n" +
                                                                                                  "    birches {\n" +
                                                                                                  "        S:leaves=18,2\n" +
                                                                                                  "        S:Strings.LOGS=17,2; 17,6; 17,10\n" +
                                                                                                  "    }\n\n" +
                                                                                                  "    vanilla_ebxl_spruces {\n" +
                                                                                                  "        S:leaves=18,1; <ExtrabiomesXL.autumnleaves.id>\n" +
                                                                                                  "        S:Strings.LOGS=17,1; 17,5; 17,9\n" +
                                                                                                  "    }\n\n" +
                                                                                                  "    jungle_trees {\n" +
                                                                                                  "        S:leaves=18,3\n" +
                                                                                                  "        S:Strings.LOGS=17,3; 17,7; 17,11\n" +
                                                                                                  "    }\n\n" +
                                                                                                  "    ic2_rubber {\n" +
                                                                                                  "        S:leaves=<IC2.blockRubLeaves>\n" +
                                                                                                  "        S:Strings.LOGS=<IC2.blockRubWood>\n" +
                                                                                                  "    }\n\n" +
                                                                                                  "    ebxl_acacia {\n" +
                                                                                                  "        S:leaves=<ExtrabiomesXL.greenleaves.id>,2\n" +
                                                                                                  "        S:Strings.LOGS=<ExtrabiomesXL.customlog.id>,1\n" +
                                                                                                  "    }\n\n" +
                                                                                                  "    ebxl_firs {\n" +
                                                                                                  "        S:leaves=<ExtrabiomesXL.greenleaves.id>,0\n" +
                                                                                                  "        S:Strings.LOGS=<ExtrabiomesXL.customlog.id>,0; <ExtrabiomesXL.quarterlog0.id>,1; <ExtrabiomesXL.quarterlog1.id>,1; <ExtrabiomesXL.quarterlog2.id>,1; <ExtrabiomesXL.quarterlog3.id>,1\n" +
                                                                                                  "    }\n\n" +
                                                                                                  "    ebxl_redwoods {\n" +
                                                                                                  "        S:leaves=<ExtrabiomesXL.greenleaves.id>,1\n" +
                                                                                                  "        S:Strings.LOGS=<ExtrabiomesXL.quarterlog0.id>,0; <ExtrabiomesXL.quarterlog1.id>,0; <ExtrabiomesXL.quarterlog2.id>,0; <ExtrabiomesXL.quarterlog3.id>,0\n" +
                                                                                                  "    }";
    
    public static final String                         thirdPartyConfigDesc               = "Third-Party config entries tell TreeCapitator how to find the block IDs from\n" +
                                                                                                  "other mods' config files.  These values are case-sensitive!\n\n" +
                                                                                                  "Format:\n" +
                                                                                                  "    <section_name> {\n" +
                                                                                                  "        S:modID=<modID from mcmod.info>\n" +
                                                                                                  "        S:configPath=<path to config file relative to .minecraft/config/>\n" +
                                                                                                  "        S:blockValues=<block config section>:<config property name>; <mod config section>:<config property name>\n" +
                                                                                                  "        S:itemValues=<item config section>:<property name>; <item config section>:<property name>\n" +
                                                                                                  "        B:useShiftedItemID=<whether or not to use the +256 shifted item ID> (true/false)\n" +
                                                                                                  "    }\n\n" +
                                                                                                  "Example:\n" +
                                                                                                  "    extrabiomesxl {\n" +
                                                                                                  "        S:modID=ExtrabiomesXL\n" +
                                                                                                  "        S:configPath=extrabiomes/extrabiomes.cfg\n" +
                                                                                                  "        S:blockValues=block:customlog.id; block:quarterlog0.id; block:quarterlog1.id; block:quarterlog2.id; block:quarterlog3.id; block:autumnleaves.id; block:greenleaves.id\n" +
                                                                                                  "        S:itemValues=items.world:axeRuby.id; items.world:axeGreenSapphire.id; items.world:axeSapphire.id\n" +
                                                                                                  "        B:useShiftedItemID=true\n" +
                                                                                                  "    }\n\n" +
                                                                                                  "Once your third-party config entries are setup, you can use replacement\n" +
                                                                                                  "tags in your tree, axe, and shears ID configs.  Replacement tags are structured like this:\n" +
                                                                                                  "<ModName.ConfigPropName>";
    
    static
    {
        /*
         * Third-Party config defaults
         */
        HashMap<String, String> biomesoplenty = new HashMap<String, String>();
        biomesoplenty.put(Strings.MOD_ID, "BiomesOPlenty");
        biomesoplenty.put(Strings.CONFIG_PATH, "BiomesOPlenty.cfg");
        biomesoplenty.put(Strings.BLOCK_VALUES, "block:Acacia Leaves ID; block:Acacia Log ID; block:Apple Leaves ID; " +
                "block:Fruitless Apple Leaves ID; block:Bamboo ID; block:Bamboo Leaves ID; block:Cherry Log ID; " +
                "block:Dark Leaves ID; block:Dark Log ID; block:Dying Leaves ID; block:Dead Log ID; block:Fir Leaves ID; " +
                "block:Fir Log ID; block:Magic Log ID; block:Magic Leaves ID; block:Mangrove Leaves ID; block:Mangrove Log ID; " +
                "block:Maple Leaves ID; block:Orange Autumn Leaves ID; block:Origin Leaves ID; block:Palm Leaves ID; " +
                "block:Palm Log ID; block:Pink Cherry Leaves ID; block:Redwood Leaves ID; block:Redwood Log ID; " +
                "block:White Cherry Leaves ID; block:Willow Leaves ID; block:Willow Log ID; block:Yellow Autumn Leaves ID");
        biomesoplenty.put(Strings.ITEM_VALUES, "item:Muddy Axe ID");
        biomesoplenty.put(Strings.SHIFT_INDEX, "true");
        thirdPartyConfig.put("biomesoplenty", biomesoplenty);
        
        HashMap<String, String> divinerpg = new HashMap<String, String>();
        divinerpg.put(Strings.MOD_ID, "DivineRPG");
        divinerpg.put(Strings.CONFIG_PATH, "DivineRPG.cfg");
        divinerpg.put(Strings.BLOCK_VALUES, "block:eucalyptus");
        divinerpg.put(Strings.ITEM_VALUES, "item:Bedrock Axe; item:Crystal Axe; item:Realmite Axe; item:azuriteaxe; item:corruptedaxe; " +
                "item:denseaxe; item:divineaxe; item:donatoraxe; item:energyaxe; item:mythrilaxe; item:plasmaaxe; item:serenityaxe; item:twilightaxe");
        divinerpg.put(Strings.SHIFT_INDEX, "true");
        thirdPartyConfig.put("divinerpg", divinerpg);
        
        HashMap<String, String> extrabiomesxl = new HashMap<String, String>();
        extrabiomesxl.put(Strings.MOD_ID, "ExtrabiomesXL");
        extrabiomesxl.put(Strings.CONFIG_PATH, "extrabiomes/extrabiomes.cfg");
        extrabiomesxl.put(Strings.BLOCK_VALUES, "block:customlog.id; block:quarterlog0.id; block:quarterlog1.id; block:quarterlog2.id; block:quarterlog3.id; " +
                "block:autumnleaves.id; block:greenleaves.id");
        thirdPartyConfig.put("extrabiomesxl", extrabiomesxl);
        
        HashMap<String, String> forestry = new HashMap<String, String>();
        forestry.put(Strings.MOD_ID, "Forestry");
        forestry.put(Strings.CONFIG_PATH, "forestry/base.conf");
        forestry.put(Strings.BLOCK_VALUES, "block:log1; block:log2; block:log3; block:log4; block:leaves");
        thirdPartyConfig.put("forestry", forestry);
        
        HashMap<String, String> ic2 = new HashMap<String, String>();
        ic2.put(Strings.MOD_ID, "IC2");
        ic2.put(Strings.CONFIG_PATH, "IC2.cfg");
        ic2.put(Strings.BLOCK_VALUES, "block:blockRubWood; block:blockRubLeaves");
        ic2.put(Strings.ITEM_VALUES, "item:itemToolBronzeAxe; item:itemToolChainsaw");
        ic2.put(Strings.SHIFT_INDEX, "true");
        thirdPartyConfig.put("ic2", ic2);
        
        HashMap<String, String> inficraft = new HashMap<String, String>();
        inficraft.put(Strings.MOD_ID, "Flora Trees");
        inficraft.put(Strings.CONFIG_PATH, "InfiCraft/FloraSoma.txt");
        inficraft.put(Strings.BLOCK_VALUES, "block:Bloodwood Block; block:Flora Leaves; block:Redwood Block; block:Sakura Leaves; block:Wood Block");
        thirdPartyConfig.put("inficraft", inficraft);
        
        HashMap<String, String> mfreloaded = new HashMap<String, String>();
        mfreloaded.put(Strings.MOD_ID, "MFReloaded");
        mfreloaded.put(Strings.CONFIG_PATH, "MFReloaded.cfg");
        mfreloaded.put(Strings.BLOCK_VALUES, "block:ID.RubberWood; block:ID.RubberLeaves; block:ID.RubberSapling");
        thirdPartyConfig.put("mfreloaded", mfreloaded);
        
        HashMap<String, String> redpower = new HashMap<String, String>();
        redpower.put(Strings.MOD_ID, "RedPowerWorld");
        redpower.put(Strings.CONFIG_PATH, "redpower/redpower.cfg");
        redpower.put(Strings.BLOCK_VALUES, "blocks.world:log.id; blocks.world:leaves.id");
        redpower.put(Strings.ITEM_VALUES, "items.world:axeRuby.id; items.world:axeGreenSapphire.id; items.world:axeSapphire.id");
        redpower.put(Strings.SHIFT_INDEX, "true");
        thirdPartyConfig.put("redpower", redpower);
        
        HashMap<String, String> thaumcraft = new HashMap<String, String>();
        thaumcraft.put(Strings.MOD_ID, "Thaumcraft");
        thaumcraft.put(Strings.CONFIG_PATH, "Thaumcraft.cfg");
        thaumcraft.put(Strings.BLOCK_VALUES, "block:BlockMagicalLog; block:BlockMagicalLeaves");
        thaumcraft.put(Strings.ITEM_VALUES, "item:Thaumaxe");
        thaumcraft.put(Strings.SHIFT_INDEX, "true");
        thirdPartyConfig.put("thaumcraft", thaumcraft);
        
        HashMap<String, String> twilightforest = new HashMap<String, String>();
        twilightforest.put(Strings.MOD_ID, "TwilightForest");
        twilightforest.put(Strings.CONFIG_PATH, "TwilightForest.cfg");
        twilightforest.put(Strings.BLOCK_VALUES, "block:Log; block:MagicLog; block:MagicStrings.LOGSpecial; block:Leaves; block:MagicLeaves; block:Hedge");
        twilightforest.put(Strings.ITEM_VALUES, "item:IronwoodAxe; item:SteeleafAxe; item:MinotaurAxe");
        twilightforest.put(Strings.SHIFT_INDEX, "true");
        thirdPartyConfig.put("twilightforest", twilightforest);
        
        HashMap<String, String> zapapples = new HashMap<String, String>();
        zapapples.put(Strings.MOD_ID, "ZapApples");
        zapapples.put(Strings.CONFIG_PATH, "ZapApples.cfg");
        zapapples.put(Strings.BLOCK_VALUES, "block:zapAppleLogID; block:zapAppleLeavesID; block:zapAppleFlowersID");
        thirdPartyConfig.put("zapapples", zapapples);
        
        /*
         * Default local config
         */
        HashMap<String, String> huge_brown_mushrooms = new HashMap<String, String>();
        huge_brown_mushrooms.put(Strings.LOGS, "99,10; 99,15");
        huge_brown_mushrooms.put(Strings.LEAVES, "99,1; 99,2; 99,3; 99,4; 99,5; 99,6; 99,7; 99,8; 99,9; 99,14");
        configBlockList.put("huge_brown_mushrooms", huge_brown_mushrooms);
        
        HashMap<String, String> huge_red_mushrooms = new HashMap<String, String>();
        huge_red_mushrooms.put(Strings.LOGS, "100,10; 100,15");
        huge_red_mushrooms.put(Strings.LEAVES, "100,1; 100,2; 100,3; 100,4; 100,5; 100,6; 100,7; 100,8; 100,9; 100,14");
        configBlockList.put("huge_red_mushrooms", huge_red_mushrooms);
        
        HashMap<String, String> vanilla_ebxl_oaks = new HashMap<String, String>();
        vanilla_ebxl_oaks.put(Strings.LOGS, "17,0; 17,4; 17,8; 17,12; <ExtrabiomesXL.quarterlog0.id>,2; <ExtrabiomesXL.quarterlog1.id>,2; " +
                "<ExtrabiomesXL.quarterlog2.id>,2; <ExtrabiomesXL.quarterlog3.id>,2;");
        vanilla_ebxl_oaks.put(Strings.LEAVES, "18,0; <ExtrabiomesXL.autumnleaves.id>; <BiomesOPlenty.Dying Leaves ID>; <BiomesOPlenty.Origin Leaves ID>; " +
                "<BiomesOPlenty.Apple Leaves ID>; <BiomesOPlenty.Fruitless Apple Leaves ID>; <BiomesOPlenty.Orange Autumn Leaves ID>; " +
                "<BiomesOPlenty.Maple Leaves ID>");
        configBlockList.put("vanilla_ebxl_bop_oaks", vanilla_ebxl_oaks);
        
        HashMap<String, String> vanilla_ebxl_spruces = new HashMap<String, String>();
        vanilla_ebxl_spruces.put(Strings.LOGS, "17,1; 17,5; 17,9; 17,13");
        vanilla_ebxl_spruces.put(Strings.LEAVES, "18,1; <ExtrabiomesXL.autumnleaves.id>");
        configBlockList.put("vanilla_ebxl_spruces", vanilla_ebxl_spruces);
        
        HashMap<String, String> birches = new HashMap<String, String>();
        birches.put(Strings.LOGS, "17,2; 17,6; 17,10; 17,14");
        birches.put(Strings.LEAVES, "18,2; <BiomesOPlenty.Yellow Autumn Leaves ID>; 18,0");
        configBlockList.put("vanilla_bop_birches", birches);
        
        HashMap<String, String> jungle_trees = new HashMap<String, String>();
        jungle_trees.put(Strings.LOGS, "17,3; 17,7; 17,11; 17,15");
        jungle_trees.put(Strings.LEAVES, "18,3");
        configBlockList.put("jungle_trees", jungle_trees);
        
        HashMap<String, String> biomesoplenty_dead = new HashMap<String, String>();
        biomesoplenty_dead.put(Strings.LOGS, "<BiomesOPlenty.Dead Log ID>");
        configBlockList.put("biomesoplenty_dead", biomesoplenty_dead);
        
        HashMap<String, String> biomesoplenty_acacia = new HashMap<String, String>();
        biomesoplenty_acacia.put(Strings.LOGS, "<BiomesOPlenty.Acacia Log ID>");
        biomesoplenty_acacia.put(Strings.LEAVES, "<BiomesOPlenty.Acacia Leaves ID>");
        configBlockList.put("biomesoplenty_acacia", biomesoplenty_acacia);
        
        HashMap<String, String> biomesoplenty_bamboo = new HashMap<String, String>();
        biomesoplenty_bamboo.put(Strings.LOGS, "<BiomesOPlenty.Bamboo ID>");
        biomesoplenty_bamboo.put(Strings.LEAVES, "<BiomesOPlenty.Bamboo Leaves ID>");
        configBlockList.put("biomesoplenty_bamboo", biomesoplenty_bamboo);
        
        HashMap<String, String> biomesoplenty_cherry = new HashMap<String, String>();
        biomesoplenty_cherry.put(Strings.LOGS, "<BiomesOPlenty.Cherry Log ID>");
        biomesoplenty_cherry.put(Strings.LEAVES, "<BiomesOPlenty.Pink Cherry Leaves ID>; <BiomesOPlenty.White Cherry Leaves ID>");
        configBlockList.put("biomesoplenty_cherry", biomesoplenty_cherry);
        
        HashMap<String, String> biomesoplenty_dark = new HashMap<String, String>();
        biomesoplenty_dark.put(Strings.LOGS, "<BiomesOPlenty.Dark Log ID>");
        biomesoplenty_dark.put(Strings.LEAVES, "<BiomesOPlenty.Dark Leaves ID>; <BiomesOPlenty.White Cherry Leaves ID>");
        configBlockList.put("biomesoplenty_darkwood", biomesoplenty_dark);
        
        HashMap<String, String> biomesoplenty_fir = new HashMap<String, String>();
        biomesoplenty_fir.put(Strings.LOGS, "<BiomesOPlenty.Fir Log ID>");
        biomesoplenty_fir.put(Strings.LEAVES, "<BiomesOPlenty.Fir Leaves ID>");
        configBlockList.put("biomesoplenty_fir", biomesoplenty_fir);
        
        HashMap<String, String> biomesoplenty_magic = new HashMap<String, String>();
        biomesoplenty_magic.put(Strings.LOGS, "<BiomesOPlenty.Magic Log ID>");
        biomesoplenty_magic.put(Strings.LEAVES, "<BiomesOPlenty.Magic Leaves ID>");
        configBlockList.put("biomesoplenty_magic", biomesoplenty_magic);
        
        HashMap<String, String> biomesoplenty_mangrove = new HashMap<String, String>();
        biomesoplenty_mangrove.put(Strings.LOGS, "<BiomesOPlenty.Mangrove Log ID>");
        biomesoplenty_mangrove.put(Strings.LEAVES, "<BiomesOPlenty.Mangrove Leaves ID>");
        configBlockList.put("biomesoplenty_mangrove", biomesoplenty_mangrove);
        
        HashMap<String, String> biomesoplenty_palm = new HashMap<String, String>();
        biomesoplenty_palm.put(Strings.LOGS, "<BiomesOPlenty.Palm Log ID>");
        biomesoplenty_palm.put(Strings.LEAVES, "<BiomesOPlenty.Palm Leaves ID>");
        configBlockList.put("biomesoplenty_palm", biomesoplenty_palm);
        
        HashMap<String, String> biomesoplenty_redwood = new HashMap<String, String>();
        biomesoplenty_redwood.put(Strings.LOGS, "<BiomesOPlenty.Redwood Log ID>");
        biomesoplenty_redwood.put(Strings.LEAVES, "<BiomesOPlenty.Redwood Leaves ID>");
        configBlockList.put("biomesoplenty_redwood", biomesoplenty_redwood);
        
        HashMap<String, String> biomesoplenty_willow = new HashMap<String, String>();
        biomesoplenty_willow.put(Strings.LOGS, "<BiomesOPlenty.Willow Log ID>");
        biomesoplenty_willow.put(Strings.LEAVES, "<BiomesOPlenty.Willow Leaves ID>");
        configBlockList.put("biomesoplenty_willow", biomesoplenty_willow);
        
        HashMap<String, String> divinerpg_eucalyptus = new HashMap<String, String>();
        divinerpg_eucalyptus.put(Strings.LOGS, "<DivineRPG.eucalyptus>");
        divinerpg_eucalyptus.put(Strings.LEAVES, "18"); // not sure on this? haven't found any of them yet and no sapling
        configBlockList.put("divinerpg_eucalyptus", divinerpg_eucalyptus);
        
        HashMap<String, String> ebxl_redwoods = new HashMap<String, String>();
        ebxl_redwoods.put(Strings.LOGS, "<ExtrabiomesXL.quarterlog0.id>,0; <ExtrabiomesXL.quarterlog1.id>,0; <ExtrabiomesXL.quarterlog2.id>,0; " +
                "<ExtrabiomesXL.quarterlog3.id>,0");
        ebxl_redwoods.put(Strings.LEAVES, "<ExtrabiomesXL.greenleaves.id>,1");
        configBlockList.put("ebxl_redwoods", ebxl_redwoods);
        
        HashMap<String, String> ebxl_firs = new HashMap<String, String>();
        ebxl_firs.put(Strings.LOGS, "<ExtrabiomesXL.customlog.id>,0; <ExtrabiomesXL.quarterlog0.id>,1; <ExtrabiomesXL.quarterlog1.id>,1; " +
                "<ExtrabiomesXL.quarterlog2.id>,1; <ExtrabiomesXL.quarterlog3.id>,1");
        ebxl_firs.put(Strings.LEAVES, "<ExtrabiomesXL.greenleaves.id>,0");
        configBlockList.put("ebxl_firs", ebxl_firs);
        
        HashMap<String, String> ebxl_acacia = new HashMap<String, String>();
        ebxl_acacia.put(Strings.LOGS, "<ExtrabiomesXL.customlog.id>,1");
        ebxl_acacia.put(Strings.LEAVES, "<ExtrabiomesXL.greenleaves.id>,2");
        configBlockList.put("ebxl_acacia", ebxl_acacia);
        
        HashMap<String, String> forestry_larch = new HashMap<String, String>();
        forestry_larch.put(Strings.LOGS, "<Forestry.log1>,0; <Forestry.log1>,4; <Forestry.log1>,8");
        forestry_larch.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_larch", forestry_larch);
        
        HashMap<String, String> forestry_teak = new HashMap<String, String>();
        forestry_teak.put(Strings.LOGS, "<Forestry.log1>,1; <Forestry.log1>,5; <Forestry.log1>,9");
        forestry_teak.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_teak", forestry_teak);
        
        HashMap<String, String> forestry_acacia = new HashMap<String, String>();
        forestry_acacia.put(Strings.LOGS, "<Forestry.log1>,2; <Forestry.log1>,6; <Forestry.log1>,10");
        forestry_acacia.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_acacia", forestry_acacia);
        
        HashMap<String, String> forestry_lime = new HashMap<String, String>();
        forestry_lime.put(Strings.LOGS, "<Forestry.log1>,3; <Forestry.log1>,7; <Forestry.log1>,11");
        forestry_lime.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_lime", forestry_lime);
        
        HashMap<String, String> forestry_chestnut = new HashMap<String, String>();
        forestry_chestnut.put(Strings.LOGS, "<Forestry.log2>,0; <Forestry.log2>,4; <Forestry.log2>,8");
        forestry_chestnut.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_chestnut", forestry_chestnut);
        
        HashMap<String, String> forestry_wenge = new HashMap<String, String>();
        forestry_wenge.put(Strings.LOGS, "<Forestry.log2>,1; <Forestry.log2>,5; <Forestry.log2>,9");
        forestry_wenge.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_wenge", forestry_wenge);
        
        HashMap<String, String> forestry_baobab = new HashMap<String, String>();
        forestry_baobab.put(Strings.LOGS, "<Forestry.log2>,2; <Forestry.log2>,6; <Forestry.log2>,10");
        forestry_baobab.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_baobab", forestry_baobab);
        
        HashMap<String, String> forestry_sequoia = new HashMap<String, String>();
        forestry_sequoia.put(Strings.LOGS, "<Forestry.log2>,3; <Forestry.log2>,7; <Forestry.log2>,11");
        forestry_sequoia.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_sequoia", forestry_sequoia);
        
        HashMap<String, String> forestry_kapok = new HashMap<String, String>();
        forestry_kapok.put(Strings.LOGS, "<Forestry.log3>,0; <Forestry.log3>,4; <Forestry.log3>,8");
        forestry_kapok.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_kapok", forestry_kapok);
        
        HashMap<String, String> forestry_ebony = new HashMap<String, String>();
        forestry_ebony.put(Strings.LOGS, "<Forestry.log3>,1; <Forestry.log3>,5; <Forestry.log3>,9");
        forestry_ebony.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_ebony", forestry_ebony);
        
        HashMap<String, String> forestry_mahogany = new HashMap<String, String>();
        forestry_mahogany.put(Strings.LOGS, "<Forestry.log3>,2; <Forestry.log3>,6; <Forestry.log3>,10");
        forestry_mahogany.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_mahogany", forestry_mahogany);
        
        HashMap<String, String> forestry_balsa = new HashMap<String, String>();
        forestry_balsa.put(Strings.LOGS, "<Forestry.log3>,3; <Forestry.log3>,7; <Forestry.log3>,11");
        forestry_balsa.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_balsa", forestry_balsa);
        
        HashMap<String, String> forestry_willow = new HashMap<String, String>();
        forestry_willow.put(Strings.LOGS, "<Forestry.log4>,0; <Forestry.log4>,4; <Forestry.log4>,8");
        forestry_willow.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_willow", forestry_willow);
        
        HashMap<String, String> forestry_walnut = new HashMap<String, String>();
        forestry_walnut.put(Strings.LOGS, "<Forestry.log4>,1; <Forestry.log4>,5; <Forestry.log4>,9");
        forestry_walnut.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_walnut", forestry_walnut);
        
        HashMap<String, String> forestry_boojum = new HashMap<String, String>();
        forestry_boojum.put(Strings.LOGS, "<Forestry.log4>,2; <Forestry.log4>,6; <Forestry.log4>,10");
        forestry_boojum.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_boojum", forestry_boojum);
        
        HashMap<String, String> forestry_cherry = new HashMap<String, String>();
        forestry_cherry.put(Strings.LOGS, "<Forestry.log4>,3; <Forestry.log4>,7; <Forestry.log4>,11");
        forestry_cherry.put(Strings.LEAVES, "<Forestry.leaves>,0; <Forestry.leaves>,8");
        configBlockList.put("forestry_cherry", forestry_cherry);
        
        HashMap<String, String> ic2_rubber = new HashMap<String, String>();
        ic2_rubber.put(Strings.LOGS, "<IC2.blockRubWood>");
        ic2_rubber.put(Strings.LEAVES, "<IC2.blockRubLeaves>");
        configBlockList.put("ic2_rubber", ic2_rubber);
        
        HashMap<String, String> inficraft_bloodwood = new HashMap<String, String>();
        inficraft_bloodwood.put(Strings.LOGS, "<Flora Trees.Bloodwood Block>");
        inficraft_bloodwood.put(Strings.LEAVES, "<Flora Trees.Sakura Leaves>,2");
        configBlockList.put("inficraft_bloodwood", inficraft_bloodwood);
        
        HashMap<String, String> inficraft_eucalyptus = new HashMap<String, String>();
        inficraft_eucalyptus.put(Strings.LOGS, "<Flora Trees.Wood Block>,0; <Flora Trees.Wood Block>,4; <Flora Trees.Wood Block>,8");
        inficraft_eucalyptus.put(Strings.LEAVES, "<Flora Trees.Flora Leaves>,1");
        configBlockList.put("inficraft_eucalyptus", inficraft_eucalyptus);
        
        HashMap<String, String> inficraft_ghostwood = new HashMap<String, String>();
        inficraft_ghostwood.put(Strings.LOGS, "<Flora Trees.Wood Block>,2; <Flora Trees.Wood Block>, 6; <Flora Trees.Wood Block>, 10");
        inficraft_ghostwood.put(Strings.LEAVES, "<Flora Trees.Sakura Leaves>,1");
        configBlockList.put("inficraft_ghostwood", inficraft_ghostwood);
        
        HashMap<String, String> inficraft_hopseed = new HashMap<String, String>();
        inficraft_hopseed.put(Strings.LOGS, "<Flora Trees.Wood Block>,3; <Flora Trees.Wood Block>, 7; <Flora Trees.Wood Block>, 11");
        inficraft_hopseed.put(Strings.LEAVES, "<Flora Trees.Flora Leaves>,2");
        configBlockList.put("inficraft_hopseed", inficraft_hopseed);
        
        HashMap<String, String> inficraft_redwood = new HashMap<String, String>();
        inficraft_redwood.put(Strings.LOGS, "<Flora Trees.Redwood Block>");
        inficraft_redwood.put(Strings.LEAVES, "<Flora Trees.Flora Leaves>,0");
        configBlockList.put("inficraft_redwood", inficraft_redwood);
        
        HashMap<String, String> inficraft_sakura = new HashMap<String, String>();
        inficraft_sakura.put(Strings.LOGS, "<Flora Trees.Wood Block>, 1; <Flora Trees.Wood Block>, 5; <Flora Trees.Wood Block>, 9");
        inficraft_sakura.put(Strings.LEAVES, "<Flora Trees.Sakura Leaves>,0");
        configBlockList.put("inficraft_sakura", inficraft_sakura);
        
        HashMap<String, String> mfr_rubber = new HashMap<String, String>();
        mfr_rubber.put(Strings.LOGS, "<MFReloaded.ID.RubberWood>");
        mfr_rubber.put(Strings.LEAVES, "<MFReloaded.ID.RubberLeaves>");
        configBlockList.put("mfr_rubber", mfr_rubber);
        
        HashMap<String, String> rp2_rubber = new HashMap<String, String>();
        rp2_rubber.put(Strings.LOGS, "<RedPowerWorld.log.id>");
        rp2_rubber.put(Strings.LEAVES, "<RedPowerWorld.leaves.id>");
        configBlockList.put("rp2_rubber", rp2_rubber);
        
        HashMap<String, String> thaum_greatwood = new HashMap<String, String>();
        thaum_greatwood.put(Strings.LOGS, "<Thaumcraft.BlockMagicalLog>,0; <Thaumcraft.BlockMagicalLog>,4; <Thaumcraft.BlockMagicalLog>,8");
        thaum_greatwood.put(Strings.LEAVES, "<Thaumcraft.BlockMagicalLeaves>,0; <Thaumcraft.BlockMagicalLeaves>,8");
        configBlockList.put("thaum_greatwood", thaum_greatwood);
        
        HashMap<String, String> thaum_silverwood = new HashMap<String, String>();
        thaum_silverwood.put(Strings.LOGS, "<Thaumcraft.BlockMagicalLog>,1; <Thaumcraft.BlockMagicalLog>,5; <Thaumcraft.BlockMagicalLog>,9");
        thaum_silverwood.put(Strings.LEAVES, "<Thaumcraft.BlockMagicalLeaves>,1");
        configBlockList.put("thaum_silverwood", thaum_silverwood);
        
        HashMap<String, String> twilight_oaks = new HashMap<String, String>();
        twilight_oaks.put(Strings.LOGS, "<TwilightForest.Log>,0; <TwilightForest.Log>,4; <TwilightForest.Log>,8; <TwilightForest.Log>,12");
        twilight_oaks.put(Strings.LEAVES, "<TwilightForest.Leaves>,0; <TwilightForest.Leaves>,3; <TwilightForest.Leaves>,8; <TwilightForest.Leaves>,11");
        configBlockList.put("twilight_oaks", twilight_oaks);
        
        HashMap<String, String> twilight_canopy = new HashMap<String, String>();
        twilight_canopy.put(Strings.LOGS, "<TwilightForest.Log>,1; <TwilightForest.Log>,5; <TwilightForest.Log>,9; <TwilightForest.Log>,13");
        twilight_canopy.put(Strings.LEAVES, "<TwilightForest.Leaves>, 1; <TwilightForest.Leaves>,9");
        configBlockList.put("twilight_canopy", twilight_canopy);
        
        HashMap<String, String> twilight_mangrove = new HashMap<String, String>();
        twilight_mangrove.put(Strings.LOGS, "<TwilightForest.Log>,2; <TwilightForest.Log>,6; <TwilightForest.Log>,10; <TwilightForest.Log>,14");
        twilight_mangrove.put(Strings.LEAVES, "<TwilightForest.Leaves>, 1; <TwilightForest.Leaves>,9");
        configBlockList.put("twilight_mangrove", twilight_mangrove);
        
        HashMap<String, String> twilight_darkwood = new HashMap<String, String>();
        twilight_darkwood.put(Strings.LOGS, "<TwilightForest.Log>,3; <TwilightForest.Log>,7; <TwilightForest.Log>,11;  <TwilightForest.Log>,15");
        twilight_darkwood.put(Strings.LEAVES, "<TwilightForest.Hedge>,1");
        configBlockList.put("twilight_darkwood", twilight_darkwood);
        
        HashMap<String, String> twilight_time = new HashMap<String, String>();
        twilight_time.put(Strings.LOGS, "<TwilightForest.MagicLog>,0; <TwilightForest.Log>,4; <TwilightForest.Log>,8; <TwilightForest.MagicLog>,12");
        twilight_time.put(Strings.LEAVES, "<TwilightForest.MagicLeaves>,0; <TwilightForest.MagicLeaves>,8");
        configBlockList.put("twilight_time", twilight_time);
        
        HashMap<String, String> zapapple = new HashMap<String, String>();
        zapapple.put(Strings.LOGS, "<ZapApples.zapAppleLogID>");
        zapapple.put(Strings.LEAVES, "<ZapApples.zapAppleLeavesID>; <ZapApples.zapAppleFlowersID>");
        configBlockList.put("zapapple", zapapple);
    }
    
    public static void debugString(String msg, Object... args)
    {
        if (allowDebugLogging)
            TCLog.info("[DEBUG] " + msg, args);
    }
    
    public static String replaceThirdPartyBlockTags(String input)
    {
        for (String tag : tagMap.keySet())
            input = input.replace(tag, tagMap.get(tag));
        
        return input;
    }
    
    public static String getRemoteConfig()
    {
        if (isForge && allowGetRemoteTreeConfig)
        {
            try
            {
                return CommonUtils.loadTextFromURL(new URL(remoteTreeConfigURL), TCLog.INSTANCE.getLogger(), TreeCapitator.remoteBlockIDConfig)[0];
            }
            catch (Throwable e)
            {
                TCLog.warning("Error retrieving remote tree config! Defaulting to cached copy if available or local config.");
            }
        }
        return TreeCapitator.remoteBlockIDConfig;
    }
    
    public static void init()
    {
        init(false);
    }
    
    public static void init(boolean isForgeVersion)
    {
        isForge = isForgeVersion;
        
        if (!isForge)
        {
            Block.blocksList[Block.wood.blockID] = null;
            wood = new BlockTree(Block.wood.blockID);
            Block.blocksList[wood.blockID] = wood;
            Item.itemsList[wood.blockID] = null;
            Item.itemsList[wood.blockID] = (new ItemMultiTextureTile(wood.blockID - 256, wood, BlockLog.woodType)).setUnlocalizedName("log");
            
            logIDList.add(new BlockID(wood.blockID));
        }
        else
        {
            axeIDList = axeIDList + "; " +
                    "<BiomesOPlenty.Muddy Axe ID>; " +
                    "<IC2.itemToolBronzeAxe>; <IC2.itemToolChainsaw>; " +
                    "<RedPowerWorld.axeRuby.id>; <RedPowerWorld.axeGreenSapphire.id>; <RedPowerWorld.axeSapphire.id>; " +
                    "<Thaumcraft.Thaumaxe>; " +
                    "<TwilightForest.IronwoodAxe>; <TwilightForest.SteeleafAxe>; <TwilightForest.MinotaurAxe>; " +
                    "<DivineRPG.Bedrock Axe>; <DivineRPG.Crystal Axe>; <DivineRPG.Realmite Axe>; <DivineRPG.azuriteaxe>; <DivineRPG.corruptedaxe>; " +
                    "<DivineRPG.denseaxe>; <DivineRPG.divineaxe>; <DivineRPG.donatoraxe>; <DivineRPG.energyaxe>; <DivineRPG.mythrilaxe>; " +
                    "<DivineRPG.plasmaaxe>; <DivineRPG.serenityaxe>; <DivineRPG.twilightaxe>";
        }
    }
    
    public static boolean isLogBlock(BlockID blockID)
    {
        return logIDList.contains(blockID);
    }
    
    public static boolean isAxeItem(ItemStack itemStack)
    {
        return itemStack != null && itemStack.stackSize > 0 && CommonUtils.isItemInList(itemStack.itemID, itemStack.getItemDamage(), TreeCapitator.axeIDList);
    }
    
    public static String getStringFromConfigBlockList()
    {
        String list = "";
        for (HashMap<String, String> group : configBlockList.values())
            list += " ! " + group.get(Strings.LOGS) + (group.containsKey(Strings.LEAVES) ? "|" + group.get(Strings.LEAVES) : "");
        return replaceThirdPartyBlockTags(list.replaceFirst(" ! ", ""));
    }
    
    public static String getStringFromParsedLists()
    {
        String list = "";
        List<ArrayList<BlockID>> processed = new ArrayList<ArrayList<BlockID>>();
        
        for (BlockID key : logIDList)
        {
            String logPart = "";
            
            if (!processed.contains(logToLogListMap.get(key)))
            {
                processed.add(logToLogListMap.get(key));
                
                for (BlockID logID : logToLogListMap.get(key))
                    logPart += "; " + logID.id + (logID.metadata != -1 ? "," + logID.metadata : "");
                logPart = logPart.replaceFirst("; ", "");
                
                if (logPart.trim().length() > 0)
                {
                    String leafPart = "";
                    
                    for (BlockID leafID : logToLeafListMap.get(key))
                        leafPart += "; " + leafID.id + (leafID.metadata != -1 ? "," + leafID.metadata : "");
                    leafPart = leafPart.replaceFirst("; ", "");
                    
                    list += " ! " + logPart + " | " + leafPart;
                }
            }
        }
        return list.replaceFirst(" ! ", "");
    }
    
    public static void parseConfigBlockList(String list)
    {
        logIDList = new ArrayList<BlockID>();
        leafIDList = new ArrayList<BlockID>();
        logToLogListMap = new HashMap<BlockID, ArrayList<BlockID>>();
        logToLeafListMap = new HashMap<BlockID, ArrayList<BlockID>>();
        
        debugString("Parsing Tree Block Config string: %s", list);
        
        if (list.trim().length() > 0)
        {
            String[] entries = list.trim().split("!");
            for (String entry : entries)
            {
                if (entry.trim().length() > 0)
                {
                    TreeCapitator.debugString("  Parsing Tree entry: %s", entry);
                    if (entry.trim().length() > 0)
                    {
                        String[] blockTypes = entry.trim().split("\\|");
                        
                        // parse log ids [0]
                        ArrayList<BlockID> logIDs = new ArrayList<BlockID>();
                        String[] logBlocks = blockTypes[0].trim().split(";");
                        
                        TreeCapitator.debugString("    Found log ID list: %s", blockTypes[0].trim());
                        
                        for (String logBlockStr : logBlocks)
                        {
                            String[] logBlock = logBlockStr.trim().split(",");
                            
                            TreeCapitator.debugString("    Found log ID: %s", logBlockStr);
                            int blockID = CommonUtils.parseInt(logBlock[0].trim(), -1);
                            
                            if (blockID != -1)
                            {
                                int metadata = -1;
                                
                                if (logBlock.length > 1)
                                    metadata = CommonUtils.parseInt(logBlock[1].trim(), -1);
                                TreeCapitator.debugString("    ++Configured log: %s, %s", blockID, metadata);
                                
                                BlockID logID = new BlockID(blockID, metadata);
                                if (!logIDList.contains(logID))
                                {
                                    logIDList.add(logID);
                                    logIDs.add(logID);
                                }
                            }
                            else
                                TreeCapitator.debugString("Block ID %s could not be parsed as an integer.  Ignoring entry.", logBlock[0].trim());
                        }
                        
                        for (BlockID logID : logIDs)
                            logToLogListMap.put(logID, logIDs);
                        
                        ArrayList<BlockID> pairedLeaves = new ArrayList<BlockID>();
                        
                        // parse leaf ids [1]
                        if (blockTypes.length > 1)
                        {
                            String[] leafBlocks = blockTypes[1].trim().split(";");
                            
                            TreeCapitator.debugString("    Found leaf ID list: %s", blockTypes[1].trim());
                            
                            for (String block : leafBlocks)
                            {
                                if (block.trim().length() > 0)
                                {
                                    TreeCapitator.debugString("    Found leaf ID: %s", block.trim());
                                    String[] leafBlock = block.trim().split(",");
                                    int blockID = CommonUtils.parseInt(leafBlock[0].trim(), -1);
                                    
                                    if (blockID != -1)
                                    {
                                        int metadata = -1;
                                        
                                        if (leafBlock.length > 1)
                                            metadata = CommonUtils.parseInt(leafBlock[1].trim(), -1);
                                        
                                        TreeCapitator.debugString("    ++Configured leaf: %s, %s", blockID, metadata);
                                        
                                        BlockID leafID = new BlockID(blockID, metadata);
                                        if (!leafIDList.contains(leafID))
                                            leafIDList.add(leafID);
                                        
                                        if (!pairedLeaves.contains(leafID))
                                            pairedLeaves.add(leafID);
                                    }
                                    else
                                        TreeCapitator.debugString("Block ID %s could not be parsed as an integer.  Ignoring entry.", leafBlock[0].trim());
                                }
                            }
                        }
                        
                        for (BlockID logID : logIDs)
                            if (!logToLeafListMap.containsKey(logID))
                                logToLeafListMap.put(logID, pairedLeaves);
                    }
                }
            }
        }
        TCLog.info("Block ID list parsing complete.");
    }
}
