package bspkrs.treecapitator;

import bspkrs.util.Configuration;
import bspkrs.util.Const;

public class Strings
{
    public static final String VERSION_NUMBER                     = Const.MCVERSION + ".r01";
    
    public static final String OAK                                = "vanilla_oak";
    public static final String SPRUCE                             = "vanilla_spruce";
    public static final String BIRCH                              = "vanilla_birch";
    public static final String JUNGLE                             = "vanilla_jungle";
    public static final String MUSH_BROWN                         = "vanilla_huge_brown_mushroom";
    public static final String MUSH_RED                           = "vanilla_huge_red_mushroom";
    
    public static final String LOGS                               = "logs";
    public static final String LOG_VALS                           = "logValues";
    public static final String LEAVES                             = "leaves";
    public static final String LEAF_VALS                          = "leafValues";
    public static final String MOD_ID                             = "modID";
    public static final String CONFIG_PATH                        = "configPath";
    public static final String BLOCK_VALUES                       = "blockValues";
    public static final String ITEM_VALUES                        = "itemValues";
    public static final String axeIDList                          = "axeIDList";
    public static final String shearIDList                        = "shearIDList";
    public static final String SHIFT_INDEX                        = "useShiftedItemID";
    public static final String IDR_MOD_ID                         = "idResolverModID";
    @Deprecated
    public static final String TREE_BLOCK_CTGY                    = "2_tree_definitions";
    @Deprecated
    public static final String THIRD_PARTY_CFG_CTGY               = "1_third_party_configs";
    public static final String THIRD_PARTY_TREE_CFG_CTGY          = "z_third_party_tree_configs";
    public static final String BLOCK_CTGY                         = "block_settings";
    public static final String ITEM_CTGY                          = "item_settings";
    public static final String LEAF_CTGY                          = "leaf_and_vine_settings";
    public static final String MISC_CTGY                          = "miscellaneous_settings";
    public static final String ID_RES_CTGY                        = "id_resolver_settings";
    public static final String GENERAL                            = Configuration.CATEGORY_GENERAL;
    public static final String onlyDestroyUpwards                 = "onlyDestroyUpwards";
    public static final String requireLeafDecayCheck              = "requireLeafDecayCheck";
    public static final String maxHorLogBreakDist                 = "maxHorLogBreakDist";
    public static final String maxVerLogBreakDist                 = "maxVerLogBreakDist";
    public static final String maxLeafIDDist                      = "maxLeafIDDist";
    public static final String maxLeafBreakDist                   = "maxLeafBreakDist";
    public static final String minLeavesToID                      = "minLeavesToID";
    public static final String breakSpeedModifier                 = "breakSpeedModifier";
    public static final String id                                 = "id";
    public static final String metadata                           = "metadata";
    
    public static final String maxHorBreakDistDesc                = "The maximum horizontal distance that the log breaking effect will travel (use -1 for no limit).";
    public static final String maxVerLogBreakDistDesc             = "The maximum vertical distance that the log breaking effect will travel (use -1 for no limit).";
    public static final String idResolverModIDDesc                = "The mod ID value for ID Resolver.";
    public static final String multiMineIDDesc                    = "The mod ID value for Multi-Mine.";
    public static final String remoteTreeConfigURLDesc            = "Incomplete - do not use";
    // "Leave this URL as is to get the latest tree definitions from my master list.\nFeel free to start your own remote list to share with your friends or send your suggestions to me for the master list!";
    @Deprecated
    public static String       remoteTreeConfigURL                = "http://bspk.rs/Minecraft/" + Const.MCVERSION + "/treeCapitatorTreeConfig.txt";
    public static final String remoteBlockIDConfigDesc            = "Incomplete - do not use";
    public static final String useRemoteTreeConfigDesc            = "Incomplete - do not use";
    public static final String enableEnchantmentModeDesc          = "Toggle for whether or not to use the Treecapitating enchantment as opposed to requiring an item to be in the axeIDList to chop a tree.";
    public static final String requireItemInAxeListForEnchantDesc = "Whether or not to check axeIDList for an item when determining if a given item can be imbued with the Treecapitating enchantment.\n" +
                                                                          "NOTE: when set to false, any ItemTool type item (pickaxes, shovels, etc) with a high enough enchantability level can get the enchantment, not just axes.";
    public static final String axeIDListDesc                      = "IDs of items that can chop down trees. Use ',' to split item id from metadata and ';' to split items.";
    public static final String needItemDesc                       = "Whether you need an item from the axeIDList to chop down a tree. Disabling will let you chop trees with any item.";
    public static final String onlyDestroyUpwardsDesc             = "Setting this to false will allow the chopping to move downward as well as upward (and blocks below the one you break will be chopped)";
    public static final String destroyLeavesDesc                  = "Enabling this will make leaves be destroyed when trees are chopped.";
    public static final String requireLeafDecayCheckDesc          = "When true TreeCapitator will only instantly decay leaves that have actually been marked for decay.\n" +
                                                                          "Set to false if you want leaves to be destroyed regardless of their decay status (hint: or for \"leaf\" blocks that are not really leaves).";
    public static final String shearLeavesDesc                    = "Enabling this will cause destroyed leaves to be sheared when a shearing item is in the hotbar (ignored if destroyLeaves is false).";
    public static final String shearVinesDesc                     = "Enabling this will shear /some/ of the vines on a tree when a shearing item is in the hotbar (ignored if destroyLeaves is false).";
    public static final String shearIDListDesc                    = "IDs of items that when placed in the hotbar will allow leaves to be sheared when shearLeaves is true.\n" +
                                                                          "Use ',' to split item id from metadata and ';' to split items.";
    public static final String logHardnessNormalDesc              = "The hardness of logs for when you are using items that won't chop down the trees.";
    public static final String logHardnessModifiedDesc            = "The hardness of logs for when you are using items that can chop down trees.";
    public static final String breakSpeedModifierDesc             = "When using an item that can chop trees, the break speed will by multiplied by this value.";
    public static final String disableInCreativeDesc              = "Flag to disable tree chopping in Creative mode";
    public static final String disableCreativeDropsDesc           = "Flag to disable drops in Creative mode";
    public static final String allowItemDamageDesc                = "Enable to cause item damage based on number of blocks destroyed";
    public static final String allowMoreBlocksThanDamageDesc      = "Enable to allow chopping down the entire tree even if your item does not have enough damage remaining to cover the number of blocks.";
    public static final String damageMultiplierDesc               = "Axes and shears will take damage this many times for each log broken.\n" +
                                                                          "Remaining damage is rounded and applied to tools when a tree is finished.";
    public static final String useIncreasingItemDamageDesc        = "Set to true to have the per-block item damage amount increase after every increaseDamageEveryXBlocks blocks are broken.";
    public static final String increaseDamageEveryXBlocksDesc     = "When useIncreasingItemDamage=true the damage applied per block broken will increase each time this many blocks are broken in a tree.";
    public static final String damageIncreaseAmountDesc           = "When useIncreasingItemDamage=true the damage applied per block broken will increase by this amount every increaseDamageEveryXBlocks blocks broken in a tree.";
    public static final String sneakActionDesc                    = "Set sneakAction = \"disable\" to disable tree chopping while sneaking,\n" +
                                                                          "set sneakAction = \"enable\" to only enable tree chopping while sneaking,\n" +
                                                                          "set sneakAction = \"none\" to have tree chopping enabled regardless of sneaking.";
    public static final String allowSmartTreeDetectionDesc        = "Set to false to disable TreeCapitator Smart Tree Detection.\n" +
                                                                          "Smart Tree Detection counts the number of leaf blocks that are adjacent to the\n" +
                                                                          "top-most connected log block at the x, z location of a log you've broken. If\n" +
                                                                          "there are at least minLeavesToID leaf blocks within maxLeafIDDist blocks then\n" +
                                                                          "TreeCapitator considers it a tree and allows chopping.\n" +
                                                                          "WARNING: Disabling Smart Tree Detection will remove the only safeguard against\n" +
                                                                          "accidentally destroying a log structure.  Make sure you know what you're doing!";
    public static final String maxLeafIDDistDesc                  = "If a tree's top log is not close enough to leaf blocks, the tree will not be chopped.\n" +
                                                                          "Increasing this value will search further.  I would try to keep it below 3.";
    public static final String maxLeafBreakDistDesc               = "The maximum distance to instantly decay leaves from any log block that is removed by TreeCapitator.";
    public static final String minLeavesToIDDesc                  = "The minimum number of leaves within maxLeafIDDist of the top log block required to identify a tree.";
    public static final String useStrictBlockPairingDesc          = "Set to true if you want only the leaf blocks listed with each log in blockIDList\n"
                                                                          + "to break when that log type is chopped.  When set to false it will break\n"
                                                                          + "any leaf type within range of the tree, not just the type for that tree.";
    public static final String allowDebugOutputDesc               = "Set to true if you want TreeCapitator to tell you what kind of block you have clicked when sneaking, false to disable.";
    public static final String allowDebugLoggingDesc              = "Set to true if you want TreeCapitator to log info about what it's doing, false to disable";
    // TODO: write a new config category description
    public static final String configBlockIDDesc                  = "Add the log and leaf block IDs for all trees you want to be able to chop down.\n" +
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
    // TODO: write a new config category description
    public static final String thirdPartyConfigDesc               = "Third-Party config entries tell TreeCapitator how to find the block IDs from\n" +
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
    
}
