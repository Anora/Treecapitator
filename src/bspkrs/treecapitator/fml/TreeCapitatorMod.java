package bspkrs.treecapitator.fml;

import java.util.HashMap;
import java.util.logging.Level;

import net.minecraft.src.Block;
import net.minecraft.src.BlockLeavesBase;
import net.minecraft.src.BlockVine;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import bspkrs.fml.util.Config;
import bspkrs.treecapitator.TreeBlockBreaker;
import bspkrs.treecapitator.TreeCapitator;
import bspkrs.util.CommonUtils;
import bspkrs.util.ModVersionChecker;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(name = "TreeCapitator", modid = "TreeCapitator", version = "Forge 1.4.5.r03", useMetadata = true)
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class TreeCapitatorMod
{
    public static ModVersionChecker versionChecker;
    private final String            versionURL = "https://dl.dropbox.com/u/20748481/Minecraft/1.4.5/treeCapitatorForge.version";
    private final String            mcfTopic   = "http://www.minecraftforum.net/topic/1009577-";
    
    private HashMap                 leafClasses;
    private String                  idList     = "17;";
    private final static String     idListDesc = "Add the ID of log blocks (and optionally leaf blocks) that you want to be able to TreeCapitate. Format is \"<logID>[|<leafID>];\" ([] indicates optional elements). Example: 17|18; 209; 210; 211; 212; 213; 243|242;";
    
    public ModMetadata              metadata;
    
    @SidedProxy(clientSide = "bspkrs.treecapitator.fml.ClientProxy", serverSide = "bspkrs.treecapitator.fml.CommonProxy")
    public static CommonProxy       proxy;
    
    @Instance(value = "TreeCapitator")
    public static TreeCapitatorMod  instance;
    
    public TreeCapitatorMod()
    {}
    
    @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
        metadata = event.getModMetadata();
        metadata.version = "Forge " + TreeCapitator.versionNumber;
        versionChecker = new ModVersionChecker(metadata.name, metadata.version, versionURL, mcfTopic, FMLLog.getLogger());
        versionChecker.checkVersionWithLogging();
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        String ctgyGen = Configuration.CATEGORY_GENERAL;
        
        config.load();
        TreeCapitator.allowUpdateCheck = Config.getBoolean(config, "allowUpdateCheck", ctgyGen, TreeCapitator.allowUpdateCheck, TreeCapitator.allowUpdateCheckDesc);
        TreeCapitator.axeIDList = Config.getString(config, "axeIDList", ctgyGen, TreeCapitator.axeIDList, TreeCapitator.axeIDListDesc);
        TreeCapitator.needItem = Config.getBoolean(config, "needItem", ctgyGen, TreeCapitator.needItem, TreeCapitator.needItemDesc);
        TreeCapitator.onlyDestroyUpwards = Config.getBoolean(config, "onlyDestroyUpwards", ctgyGen, TreeCapitator.onlyDestroyUpwards, TreeCapitator.onlyDestroyUpwardsDesc);
        TreeCapitator.destroyLeaves = Config.getBoolean(config, "destroyLeaves", ctgyGen, TreeCapitator.destroyLeaves, TreeCapitator.destroyLeavesDesc);
        TreeCapitator.shearLeaves = Config.getBoolean(config, "shearLeaves", ctgyGen, TreeCapitator.shearLeaves, TreeCapitator.shearLeavesDesc);
        TreeCapitator.shearVines = Config.getBoolean(config, "shearVines", ctgyGen, TreeCapitator.shearVines, TreeCapitator.shearVinesDesc);
        TreeCapitator.shearIDList = Config.getString(config, "shearIDList", ctgyGen, TreeCapitator.shearIDList, TreeCapitator.shearIDListDesc);;
        TreeCapitator.logHardnessNormal = Config.getFloat(config, "logHardnessNormal", ctgyGen, TreeCapitator.logHardnessNormal, 0F, 100F, TreeCapitator.logHardnessNormalDesc);
        TreeCapitator.logHardnessModified = Config.getFloat(config, "logHardnessModified", ctgyGen, TreeCapitator.logHardnessModified, 0F, 100F, TreeCapitator.logHardnessModifiedDesc);
        TreeCapitator.disableInCreative = Config.getBoolean(config, "disableInCreative", ctgyGen, TreeCapitator.disableInCreative, TreeCapitator.disableInCreativeDesc);
        TreeCapitator.disableCreativeDrops = Config.getBoolean(config, "disableCreativeDrops", ctgyGen, TreeCapitator.disableCreativeDrops, TreeCapitator.disableCreativeDropsDesc);
        TreeCapitator.allowItemDamage = Config.getBoolean(config, "allowItemDamage", ctgyGen, TreeCapitator.allowItemDamage, TreeCapitator.allowItemDamageDesc);
        TreeCapitator.allowMoreBlocksThanDamage = Config.getBoolean(config, "allowMoreBlocksThanDamage", ctgyGen, TreeCapitator.allowMoreBlocksThanDamage, TreeCapitator.allowMoreBlocksThanDamageDesc);
        TreeCapitator.sneakAction = Config.getString(config, "sneakAction", ctgyGen, TreeCapitator.sneakAction, TreeCapitator.sneakActionDesc);
        TreeCapitator.maxBreakDistance = Config.getInt(config, "maxBreakDistance", ctgyGen, TreeCapitator.maxBreakDistance, -1, 100, TreeCapitator.maxBreakDistanceDesc);
        idList = Config.getString(config, "logIDList", ctgyGen, idList, idListDesc);
        config.save();
    }
    
    @Init
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
        TreeCapitator.init(true);
        proxy.registerTickHandler();
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent event)
    {
        parseBlockIDList(idList);
    }
    
    private void parseBlockIDList(String list)
    {
        leafClasses = new HashMap();
        
        FMLLog.log(Level.INFO, "Parsing log ID list: %s", list);
        if (list.trim().length() > 0)
        {
            String[] groups = list.trim().split(";");
            for (String group : groups)
            {
                if (group.trim().length() > 0)
                {
                    String[] ids = group.trim().split("\\|");
                    
                    FMLLog.log(Level.INFO, "Found Log Block ID: %s", ids[0].trim());
                    int logID = CommonUtils.parseInt(ids[0].trim());
                    FMLLog.log(Level.INFO, "Interpretted: %s", logID);
                    int leafID = 18;
                    
                    if (ids.length > 1)
                    {
                        FMLLog.log(Level.INFO, "Found Leaf Block ID: %s", list);
                        leafID = CommonUtils.parseInt(ids[1].trim());
                        FMLLog.log(Level.INFO, "Interpretted: %s", leafID);
                    }
                    else
                        FMLLog.log(Level.INFO, "Leaf Block ID not provided; using %s", leafID);
                    
                    if (logID > 0)
                    {
                        Block log = Block.blocksList[logID];
                        if (log != null)
                        {
                            if (!TreeCapitator.logClasses.contains(log.getClass()))
                            {
                                TreeCapitator.logClasses.add(log.getClass());
                                FMLLog.log(Level.INFO, "Configured Log Block class: %s", log.getClass().getName());
                                
                                Block leaf = Block.blocksList[leafID];
                                if (leaf != null)
                                {
                                    if (leaf instanceof BlockLeavesBase)
                                        leafClasses.put(log.getClass(), BlockLeavesBase.class);
                                    else
                                        leafClasses.put(log.getClass(), leaf.getClass());
                                }
                                else
                                    leafClasses.put(log.getClass(), BlockLeavesBase.class);
                                
                                FMLLog.log(Level.INFO, "Pairing Leaf Block class: %s", leafClasses.get(log.getClass()));
                            }
                            else
                                FMLLog.log(Level.INFO, "Block for ID %s is already configured", logID);
                        }
                        else
                            FMLLog.log(Level.WARNING, "Block ID %s not found", logID);
                    }
                }
            }
        }
    }
    
    public void onBlockHarvested(World world, int x, int y, int z, Block block, int metadata, EntityPlayer entityPlayer)
    {
        if (TreeCapitator.logClasses.contains(block.getClass()))
        {
            TreeBlockBreaker breaker = new TreeBlockBreaker(entityPlayer, block.blockID, block.getClass(), (Class<?>) leafClasses.get(block.getClass()), BlockVine.class);
            breaker.onBlockHarvested(world, x, y, z, metadata, entityPlayer);
        }
    }
}
