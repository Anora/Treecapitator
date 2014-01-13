package bspkrs.treecapitator.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import bspkrs.treecapitator.util.TCConst;
import bspkrs.util.ItemID;
import bspkrs.util.ListUtils;

public class ToolRegistry
{
    private static ToolRegistry instance;
    
    public static ToolRegistry instance()
    {
        if (instance == null)
            new ToolRegistry();
        
        return instance;
    }
    
    // Registry tool lists
    private List<ItemID> axeList;
    private List<ItemID> shearsList;
    
    // Vanilla tool lists
    private List<ItemID> vanAxeList;
    private List<ItemID> vanShearsList;
    
    private List<ItemID> blacklist;
    
    protected ToolRegistry()
    {
        instance = this;
        
        initLists();
        initVanillaItemLists();
    }
    
    protected void initLists()
    {
        axeList = new ArrayList<ItemID>();
        shearsList = new ArrayList<ItemID>();
        blacklist = new ArrayList<ItemID>();
    }
    
    protected void initVanillaLists()
    {
        vanAxeList = new ArrayList<ItemID>();
        vanShearsList = new ArrayList<ItemID>();
    }
    
    protected void initVanillaItemLists()
    {
        initVanillaLists();
        vanAxeList.add(new ItemID(Items.wooden_axe));
        vanAxeList.add(new ItemID(Items.stone_axe));
        vanAxeList.add(new ItemID(Items.iron_axe));
        vanAxeList.add(new ItemID(Items.golden_axe));
        vanAxeList.add(new ItemID(Items.diamond_axe));
        
        vanShearsList.add(new ItemID(Items.shears));
    }
    
    public List<ItemID> blacklist()
    {
        return new ArrayList<ItemID>(blacklist);
    }
    
    // This must be done after all trees are registered to avoid screwing up the registration process
    public void readBlacklistFromDelimitedString(String dList)
    {
        blacklist = ListUtils.getDelimitedStringAsItemIDList(dList, ";");
    }
    
    protected void readFromNBT(NBTTagCompound ntc)
    {
        axeList = ListUtils.getDelimitedStringAsItemIDList(ntc.getString(TCConst.AXE_ID_LIST), ";");
        shearsList = ListUtils.getDelimitedStringAsItemIDList(ntc.getString(TCConst.SHEARS_ID_LIST), ";");
        blacklist = ListUtils.getDelimitedStringAsItemIDList(ntc.getString(TCConst.BLACKLIST), ";");
    }
    
    public void writeToNBT(NBTTagCompound ntc)
    {
        ntc.setString(TCConst.AXE_ID_LIST, ListUtils.getListAsDelimitedString(axeList, ";"));
        ntc.setString(TCConst.SHEARS_ID_LIST, ListUtils.getListAsDelimitedString(shearsList, ";"));
        ntc.setString(TCConst.BLACKLIST, ListUtils.getListAsDelimitedString(blacklist, ";"));
    }
    
    public void registerAxe(ItemID axe)
    {
        if (axe != null && !blacklist.contains(axe) && !axeList.contains(axe))
            axeList.add(axe);
    }
    
    public void registerShears(ItemID shears)
    {
        if (shears != null && !blacklist.contains(shears) && !shearsList.contains(shears))
            shearsList.add(shears);
    }
    
    public List<ItemID> axeList()
    {
        return new ArrayList<ItemID>(axeList);
    }
    
    public List<ItemID> shearsList()
    {
        return new ArrayList<ItemID>(shearsList);
    }
    
    public List<ItemID> vanillaAxeList()
    {
        return new ArrayList<ItemID>(vanAxeList);
    }
    
    public List<ItemID> vanillaShearsList()
    {
        return new ArrayList<ItemID>(vanShearsList);
    }
    
    public boolean isAxe(ItemID itemID)
    {
        return !blacklist.contains(itemID) && axeList.contains(itemID);
    }
    
    public boolean isAxe(Item item)
    {
        if (item != null)
        {
            ItemID itemID = new ItemID(item);
            return !blacklist.contains(itemID) && axeList.contains(itemID);
        }
        
        return false;
    }
    
    public boolean isAxe(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            ItemID itemID = new ItemID(itemStack);
            return !blacklist.contains(itemID) && axeList.contains(itemID);
        }
        else
            return false;
    }
    
    public boolean isShears(ItemID itemID)
    {
        return !blacklist.contains(itemID) && shearsList.contains(itemID);
    }
    
    public boolean isShears(Item item)
    {
        if (item != null)
        {
            ItemID itemID = new ItemID(item);
            return !blacklist.contains(itemID) && shearsList.contains(itemID);
        }
        else
            return false;
    }
    
    public boolean isShears(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            ItemID itemID = new ItemID(itemStack);
            return !blacklist.contains(itemID) && shearsList.contains(new ItemID(itemStack));
        }
        else
            return false;
    }
}
