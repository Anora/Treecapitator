package bspkrs.treecapitator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import bspkrs.util.BlockID;
import bspkrs.util.HashCodeUtil;

public class ConfigTreeDefinition extends TreeDefinition
{
    protected List<String> logKeys;
    protected List<String> leafKeys;
    
    public ConfigTreeDefinition()
    {
        super();
        logKeys = new ArrayList<String>();
        leafKeys = new ArrayList<String>();
    }
    
    public ConfigTreeDefinition(List<BlockID> logs, List<BlockID> leaves)
    {
        super(logs, leaves);
    }
    
    public ConfigTreeDefinition(String configLogs, String configLeaves)
    {
        logKeys = Arrays.asList(configLogs.split(";"));
        leafKeys = Arrays.asList(configLeaves.split(";"));
    }
    
    public TreeDefinition getTagsReplacedTreeDef(Map<String, String> tagMap)
    {
        logBlocks = new ArrayList<BlockID>();
        leafBlocks = new ArrayList<BlockID>();
        for (Entry<String, String> e : tagMap.entrySet())
        {
            for (String logID : logKeys)
                super.addLogID(new BlockID(logID.replace(e.getKey(), e.getValue())));
            for (String leafID : leafKeys)
                super.addLeafID(new BlockID(leafID.replace(e.getKey(), e.getValue())));
        }
        
        return this;
    }
    
    @Override
    // TODO: fix this up
    public boolean equals(Object o)
    {
        if (!(o instanceof ConfigTreeDefinition))
            return false;
        
        if (o == this)
            return true;
        
        ConfigTreeDefinition td = (ConfigTreeDefinition) o;
        return td.logBlocks.equals(logBlocks) && td.leafBlocks.equals(leafBlocks);
        
    }
    
    @Override
    // TODO: fix this up
    public int hashCode()
    {
        int result = 23;
        result = HashCodeUtil.hash(result, logBlocks);
        result = HashCodeUtil.hash(result, leafBlocks);
        return result;
    }
    
    @Override
    public TreeDefinition readFromNBT(NBTTagCompound treeDefNBT)
    {
        super.readFromNBT(treeDefNBT);
        
        logKeys = new ArrayList<String>();
        leafKeys = new ArrayList<String>();
        
        if (treeDefNBT.hasKey(Strings.LOG_VALS))
        {
            String logValues = treeDefNBT.getString(Strings.LOG_VALS);
            
            for (String s : logValues.split(";"))
                logKeys.add(s.trim());
        }
        
        if (treeDefNBT.hasKey(Strings.LEAF_VALS))
        {
            String leafValues = treeDefNBT.getString(Strings.LEAF_VALS);
            
            for (String s : leafValues.split(";"))
                leafKeys.add(s.trim());
        }
        
        return this;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound treeDefNBT)
    {
        super.writeToNBT(treeDefNBT);
        
        String keyList = "";
        for (String logKey : logKeys)
        {
            keyList += "; " + logKey;
        }
        treeDefNBT.setString(Strings.LOG_VALS, keyList.replaceFirst("; ", ""));
        
        keyList = "";
        for (String leafKey : leafKeys)
        {
            keyList += "; " + leafKey;
        }
        treeDefNBT.setString(Strings.LEAF_VALS, keyList.replaceFirst("; ", ""));
    }
    
    /**
     * Retrieves a copy of the list of logs in this TreeDefinition.
     * 
     * @return
     */
    public List<String> getConfigLogList()
    {
        return new ArrayList<String>(logKeys);
    }
    
    /**
     * Retrieves a copy of the list of leaves in this TreeDefinition.
     * 
     * @return
     */
    public List<String> getConfigLeafList()
    {
        return new ArrayList<String>(leafKeys);
    }
}
