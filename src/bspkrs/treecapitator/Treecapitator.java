package bspkrs.treecapitator;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import bspkrs.util.BlockID;
import bspkrs.util.CommonUtils;
import bspkrs.util.Coord;

public class Treecapitator
{
    // The player chopping
    public EntityPlayer          player;
    private Coord                startPos;
    // The axe of the player currently chopping
    private ItemStack            axe;
    private ItemStack            shears;
    private final TreeDefinition treeDef;
    private final List<BlockID>  masterLogList;
    private final BlockID        vineID;
    private float                currentAxeDamage, currentShearsDamage = 0.0F;
    private int                  numLogsBroken;
    private int                  numLeavesSheared;
    private float                logDamageMultiplier;
    private float                leafDamageMultiplier;
    
    public Treecapitator(EntityPlayer entityPlayer, TreeDefinition treeDef)
    {
        player = entityPlayer;
        this.treeDef = treeDef;
        masterLogList = TreeRegistry.instance().masterLogList();
        vineID = new BlockID(Block.vine.blockID);
        logDamageMultiplier = TCSettings.damageMultiplier;
        leafDamageMultiplier = TCSettings.damageMultiplier;
        numLogsBroken = 1;
        numLeavesSheared = 1;
    }
    
    public static boolean isBreakingPossible(World world, EntityPlayer entityPlayer, boolean shouldLog)
    {
        ItemStack axe = entityPlayer.getCurrentEquippedItem();
        if ((isAxeItemEquipped(entityPlayer) || !TCSettings.needItem))
        {
            if (!entityPlayer.capabilities.isCreativeMode && TCSettings.allowItemDamage && axe != null
                    && (axe.isItemStackDamageable() && (axe.getMaxDamage() - axe.getItemDamage() <= TCSettings.damageMultiplier))
                    && !TCSettings.allowMoreBlocksThanDamage)
            {
                if (shouldLog)
                    TCLog.debug("Chopping disabled due to axe durability.");
                return false;
            }
            
            return true;
        }
        else if (shouldLog)
            TCLog.debug("Player does not have an axe equipped.");
        
        return false;
    }
    
    public static boolean isBreakingEnabled(EntityPlayer player)
    {
        return (TCSettings.sneakAction.equalsIgnoreCase("none")
                || (TCSettings.sneakAction.equalsIgnoreCase("disable") && !player.isSneaking())
                || (TCSettings.sneakAction.equalsIgnoreCase("enable") && player.isSneaking()))
                && !(player.capabilities.isCreativeMode && TCSettings.disableInCreative);
    }
    
    public static int getTreeHeight(TreeDefinition tree, World world, int x, int y, int z, int md, EntityPlayer entityPlayer)
    {
        Coord startPos = new Coord(x, y, z);
        
        if (isBreakingPossible(world, entityPlayer, false))
        {
            if (!tree.onlyDestroyUpwards())
                startPos = getBottomLog(tree.logBlocks, world, startPos, false);
            
            Coord topLog = getTopLog(tree.logBlocks, world, new Coord(x, y, z), false);
            
            if (!tree.allowSmartTreeDetection() || tree.leafBlocks.size() == 0
                    || hasXLeavesInDist(tree.leafBlocks, world, topLog, tree.maxLeafIDDist(), tree.minLeavesToID(), false))
                return topLog.y - startPos.y + 1;
        }
        
        return 1;
    }
    
    public void onBlockHarvested(World world, int x, int y, int z, int md, EntityPlayer entityPlayer)
    {
        if (!world.isRemote)
        {
            TCLog.debug("In TreeCapitator.onBlockHarvested() " + x + ", " + y + ", " + z);
            player = entityPlayer;
            startPos = new Coord(x, y, z);
            
            if (isBreakingEnabled(entityPlayer))
            {
                Coord topLog = getTopLog(world, new Coord(x, y, z));
                if (!treeDef.allowSmartTreeDetection() || treeDef.leafBlocks.size() == 0
                        || hasXLeavesInDist(world, topLog, treeDef.maxLeafIDDist(), treeDef.minLeavesToID()))
                {
                    if (isAxeItemEquipped() || !TCSettings.needItem)
                    {
                        TCLog.debug("Proceeding to chop tree...");
                        List<Coord> listFinal = new ArrayList<Coord>();
                        TCLog.debug("Finding log blocks...");
                        List<Coord> logs = addLogs(world, new Coord(x, y, z));
                        addLogsAbove(world, new Coord(x, y, z), listFinal);
                        
                        TCLog.debug("Destroying %d log blocks...", logs.size());
                        destroyBlocks(world, logs);
                        if (numLogsBroken > 1)
                            TCLog.debug("Number of logs broken: %d", numLogsBroken);
                        
                        if (TCSettings.destroyLeaves && treeDef.leafBlocks.size() != 0)
                        {
                            TCLog.debug("Finding leaf blocks...");
                            List<Coord> leaves = new ArrayList<Coord>();
                            for (Coord pos : listFinal)
                            {
                                addLeaves(world, pos, leaves);
                                
                                // Deprecated in favor of simply not adding the "has log close" leaves in the first place
                                // removeLeavesWithLogsAround(world, leaves);
                            }
                            TCLog.debug("Destroying %d leaf blocks...", leaves.size());
                            destroyBlocksWithChance(world, leaves, 0.5F, hasShearsInHotbar(player));
                            
                            if (numLeavesSheared > 1)
                                TCLog.debug("Number of leaves sheared: %d", numLeavesSheared);
                        }
                        
                        /*
                         * Apply remaining damage if it rounds to a non-zero value
                        q   */
                        if (currentAxeDamage > 0.0F && axe != null)
                        {
                            currentAxeDamage = Math.round(currentAxeDamage);
                            
                            for (int i = 0; i < MathHelper.floor_double(currentAxeDamage); i++)
                                axe.getItem().onBlockDestroyed(axe, world, 17, x, y, z, player);
                        }
                        
                        if (currentShearsDamage > 0.0F && shears != null)
                        {
                            currentShearsDamage = Math.round(currentShearsDamage);
                            
                            for (int i = 0; i < Math.floor(currentShearsDamage); i++)
                                if (TCSettings.isForge && shears.itemID == Item.shears.itemID)
                                    shears.damageItem(1, player);
                                else
                                    shears.getItem().onBlockDestroyed(shears, world, 18, x, y, z, player);
                        }
                    }
                    else
                        TCLog.debug("Axe item is not equipped.");
                }
                else
                    TCLog.debug("Could not identify tree.");
            }
            else
                TCLog.debug("Tree Chopping is disabled due to player state or gamemode.");
        }
        else
            TCLog.debug("World is remote, skipping TreeCapitator.onBlockHarvested().");
    }
    
    /**
     * Returns the block hardness based on whether the player is holding an axe-type item or not
     */
    public float getBlockHardness()
    {
        return this.isAxeItemEquipped() ? TCSettings.logHardnessModified : TCSettings.logHardnessNormal;
    }
    
    /**
     * Returns the block hardness based on whether the player is holding an axe-type item or not
     */
    public static float getBlockHardness(EntityPlayer entityPlayer)
    {
        return isAxeItemEquipped(entityPlayer) ? TCSettings.logHardnessModified : TCSettings.logHardnessNormal;
    }
    
    private Coord getTopLog(World world, Coord pos)
    {
        return getTopLog(treeDef.logBlocks, world, pos, true);
    }
    
    private static Coord getTopLog(List<BlockID> logs, World world, Coord pos, boolean shouldLog)
    {
        while (logs.contains(new BlockID(world, pos.x, pos.y + 1, pos.z)))
            pos.y++;
        
        if (shouldLog)
            TCLog.debug("Top Log: " + pos.x + ", " + pos.y + ", " + pos.z);
        
        return pos;
    }
    
    private static Coord getBottomLog(List<BlockID> logs, World world, Coord pos, boolean shouldLog)
    {
        while (logs.contains(new BlockID(world, pos.x, pos.y - 1, pos.z)))
            pos.y--;
        
        if (shouldLog)
            TCLog.debug("Bottom Log: " + pos.x + ", " + pos.y + ", " + pos.z);
        
        return pos;
    }
    
    private static boolean hasXLeavesInDist(List<BlockID> leaves, World world, Coord pos, int range, int limit, boolean shouldLog)
    {
        if (shouldLog)
            TCLog.debug("Attempting to identify tree...");
        
        int i = 0;
        for (int x = -range; x <= range; x++)
            // lower bound kept at -1 
            for (int y = -1; y <= range; y++)
                for (int z = -range; z <= range; z++)
                    if (x != 0 || y != 0 || z != 0)
                    {
                        BlockID blockID = new BlockID(world, pos.x + x, pos.y + y, pos.z + z);
                        if (blockID.id > 0)
                            if (isLeafBlock(leaves, blockID))
                            {
                                if (shouldLog)
                                    TCLog.debug("Found leaf block: %s", blockID);
                                
                                i++;
                                if (i >= limit)
                                    return true;
                            }
                            else if (shouldLog)
                                TCLog.debug("Not a leaf block: %s", blockID);
                    }
        
        if (shouldLog)
            TCLog.debug("Number of leaf blocks is less than the limit. Found: %s", i);
        
        return false;
    }
    
    private boolean hasXLeavesInDist(World world, Coord pos, int range, int limit)
    {
        return hasXLeavesInDist(treeDef.leafBlocks, world, pos, range, limit, true);
    }
    
    /**
     * Defines whether or not a player can break the block with current tool and sets the local axe object if possible
     */
    private boolean isAxeItemEquipped()
    {
        ItemStack item = player.getCurrentEquippedItem();
        
        if (TCSettings.enableEnchantmentMode)
        {
            if (item != null && item.isItemEnchanted())
                for (int i = 0; i < item.getEnchantmentTagList().tagCount(); i++)
                {
                    NBTTagCompound tag = (NBTTagCompound) item.getEnchantmentTagList().tagAt(i);
                    if (tag.getShort("id") == TCSettings.treecapitating.effectId)
                    {
                        axe = item;
                        return true;
                    }
                }
            
            axe = null;
            return false;
        }
        else if (ToolRegistry.instance().isAxe(item))
        {
            axe = item;
            return true;
        }
        else
        {
            axe = null;
            return false;
        }
    }
    
    private int getFortuneLevel(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            NBTTagList list = itemStack.getEnchantmentTagList();
            if (list != null)
                for (int i = 0; i < list.tagCount(); i++)
                {
                    NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
                    if (tag.getShort("id") == Enchantment.fortune.effectId)
                        return tag.getShort("lvl");
                }
        }
        return 0;
    }
    
    /**
     * Defines whether or not a player can break the block with current tool
     */
    public static boolean isAxeItemEquipped(EntityPlayer entityPlayer)
    {
        ItemStack item = entityPlayer.getCurrentEquippedItem();
        
        if (TCSettings.enableEnchantmentMode)
        {
            if (item != null && item.isItemEnchanted())
                for (int i = 0; i < item.getEnchantmentTagList().tagCount(); i++)
                {
                    NBTTagCompound tag = (NBTTagCompound) item.getEnchantmentTagList().tagAt(i);
                    if (tag.getShort("id") == TCSettings.treecapitating.effectId)
                        return true;
                }
            
            return false;
        }
        else
            return ToolRegistry.instance().isAxe(item);
    }
    
    /**
     * Defines whether or not a player has a shear-type item in the hotbar
     */
    private boolean hasShearsInHotbar(EntityPlayer entityplayer)
    {
        return shearsHotbarIndex(entityplayer) != -1;
    }
    
    /**
     * Returns the index of the left-most shear-type item in the hotbar and sets the local shears object if possible
     */
    private int shearsHotbarIndex(EntityPlayer entityPlayer)
    {
        for (int i = 0; i < 9; i++)
        {
            ItemStack item = entityPlayer.inventory.mainInventory[i];
            
            if (item != null && item.stackSize > 0 && ToolRegistry.instance().isShears(item))
            {
                shears = item;
                return i;
            }
        }
        shears = null;
        return -1;
    }
    
    public static boolean isLeafBlock(List<BlockID> leaves, BlockID blockID)
    {
        return leaves.contains(blockID) || leaves.contains(new BlockID(blockID.id, blockID.metadata & 7));
    }
    
    public boolean isLeafBlock(BlockID blockID)
    {
        return isLeafBlock(treeDef.leafBlocks, blockID);
    }
    
    private void destroyBlocks(World world, List<Coord> list)
    {
        destroyBlocksWithChance(world, list, 1.0F, false);
    }
    
    private void destroyBlocksWithChance(World world, List<Coord> list, float f, boolean canShear)
    {
        while (list.size() > 0)
        {
            Coord pos = list.remove(0);
            int id = world.getBlockId(pos.x, pos.y, pos.z);
            if (id != 0)
            {
                Block block = Block.blocksList[id];
                int metadata = world.getBlockMetadata(pos.x, pos.y, pos.z);
                
                if ((((vineID.equals(new BlockID(block, metadata)) && TCSettings.shearVines)
                        || (isLeafBlock(new BlockID(block, metadata)) && TCSettings.shearLeaves)) && canShear)
                        && !(player.capabilities.isCreativeMode && TCSettings.disableCreativeDrops))
                {
                    world.spawnEntityInWorld(new EntityItem(world, pos.x, pos.y, pos.z, new ItemStack(id, 1, block.damageDropped(metadata))));
                    
                    if (TCSettings.allowItemDamage && !player.capabilities.isCreativeMode && shears != null && shears.stackSize > 0)
                    {
                        canShear = damageShearsAndContinue(world, id, pos.x, pos.y, pos.z);
                        numLeavesSheared++;
                        
                        if (canShear && TCSettings.useIncreasingItemDamage && numLeavesSheared % TCSettings.increaseDamageEveryXBlocks == 0)
                            leafDamageMultiplier += TCSettings.damageIncreaseAmount;
                    }
                }
                else if (!(player.capabilities.isCreativeMode && TCSettings.disableCreativeDrops))
                {
                    block.dropBlockAsItem(world, pos.x, pos.y, pos.z, metadata, getFortuneLevel(axe));
                    
                    if (TCSettings.allowItemDamage && !player.capabilities.isCreativeMode && axe != null && axe.stackSize > 0
                            && !vineID.equals(new BlockID(block, metadata)) && !isLeafBlock(new BlockID(block, metadata)) && !pos.equals(startPos))
                    {
                        if (!damageAxeAndContinue(world, id, startPos.x, startPos.y, startPos.z))
                            list.clear();
                        
                        numLogsBroken++;
                        
                        if (TCSettings.useIncreasingItemDamage && numLogsBroken % TCSettings.increaseDamageEveryXBlocks == 0)
                            logDamageMultiplier += TCSettings.damageIncreaseAmount;
                    }
                }
                
                if (world.blockHasTileEntity(pos.x, pos.y, pos.z))
                    world.removeBlockTileEntity(pos.x, pos.y, pos.z);
                
                world.setBlock(pos.x, pos.y, pos.z, 0, 0, 3);
            }
        }
    }
    
    /**
     * Damages the axe-type item and returns true if we should continue destroying logs
     */
    private boolean damageAxeAndContinue(World world, int id, int x, int y, int z)
    {
        if (axe != null)
        {
            currentAxeDamage += logDamageMultiplier;
            
            for (int i = 0; i < (int) Math.floor(currentAxeDamage); i++)
                axe.getItem().onBlockDestroyed(axe, world, id, x, y, z, player);
            
            currentAxeDamage -= Math.floor(currentAxeDamage);
            
            if (axe != null && axe.stackSize < 1)
                player.destroyCurrentEquippedItem();
        }
        return !TCSettings.needItem || TCSettings.allowMoreBlocksThanDamage || isAxeItemEquipped();
    }
    
    /**
     * Damages the shear-type item and returns true if we should continue shearing leaves/vines
     */
    private boolean damageShearsAndContinue(World world, int id, int x, int y, int z)
    {
        if (shears != null)
        {
            int shearsIndex = shearsHotbarIndex(player);
            currentShearsDamage += leafDamageMultiplier;
            
            for (int i = 0; i < Math.floor(currentShearsDamage); i++)
                // Shakes fist at Forge!
                if (TCSettings.isForge && shears.itemID == Item.shears.itemID)
                    shears.damageItem(1, player);
                else
                    shears.getItem().onBlockDestroyed(shears, world, id, x, y, z, player);
            
            currentShearsDamage -= Math.floor(currentShearsDamage);
            
            if (shears != null && shears.stackSize < 1 && shearsIndex != -1)
                player.inventory.setInventorySlotContents(shearsIndex, (ItemStack) null);
        }
        return TCSettings.allowMoreBlocksThanDamage || hasShearsInHotbar(player);
    }
    
    private List<Coord> addLogs(World world, Coord pos)
    {
        int index = 0, lowY = pos.y, x, y, z;
        List<Coord> list = new ArrayList<Coord>();
        Coord newPos;
        
        list.add(pos);
        
        do
        {
            Coord currentLog = list.get(index);
            
            for (x = -1; x <= 1; x++)
                for (y = (treeDef.onlyDestroyUpwards() ? 0 : -1); y <= 1; y++)
                    for (z = -1; z <= 1; z++)
                        if (treeDef.logBlocks.contains(new BlockID(world, currentLog.x + x, currentLog.y + y, currentLog.z + z)))
                        {
                            newPos = new Coord(currentLog.x + x, currentLog.y + y, currentLog.z + z);
                            
                            if (treeDef.maxHorLogBreakDist() == -1 || (Math.abs(newPos.x - startPos.x) <= treeDef.maxHorLogBreakDist()
                                    && Math.abs(newPos.z - startPos.z) <= treeDef.maxHorLogBreakDist())
                                    && (treeDef.maxVerLogBreakDist() == -1 || (Math.abs(newPos.y - startPos.y) <= treeDef.maxVerLogBreakDist()))
                                    && !list.contains(newPos) && (newPos.y >= lowY || !treeDef.onlyDestroyUpwards()))
                                list.add(newPos);
                        }
        }
        while (++index < list.size());
        
        if (list.contains(pos))
            list.remove(pos);
        
        return list;
    }
    
    private void addLogsAbove(World world, Coord position, List<Coord> listFinal)
    {
        List<Coord> listAbove = new ArrayList<Coord>();
        List<Coord> list;
        Coord newPosition;
        int counter, index, x, z;
        
        listAbove.add(position);
        
        do
        {
            list = listAbove;
            listAbove = new ArrayList<Coord>();
            
            for (Coord pos : list)
            {
                counter = 0;
                for (x = -1; x <= 1; x++)
                    for (z = -1; z <= 1; z++)
                        if (treeDef.logBlocks.contains(new BlockID(world, pos.x + x, pos.y + 1, pos.z + z)))
                        {
                            if (!listAbove.contains(newPosition = new Coord(pos.x + x, pos.y + 1, pos.z + z)))
                                listAbove.add(newPosition);
                            
                            counter++;
                        }
                
                if (counter == 0)
                    listFinal.add(pos.clone());
            }
            
            index = -1;
            while (++index < listAbove.size())
            {
                Coord pos = listAbove.get(index);
                for (x = -1; x <= 1; x++)
                    for (z = -1; z <= 1; z++)
                        if (treeDef.logBlocks.contains(new BlockID(world, pos.x + x, pos.y, pos.z + z)))
                            if (!listAbove.contains(newPosition = new Coord(pos.x + x, pos.y, pos.z + z)))
                                listAbove.add(newPosition);
            }
        }
        while (listAbove.size() > 0);
    }
    
    public List<Coord> addLeaves(World world, Coord pos, List<Coord> list)
    {
        int index = -1;
        
        if (list == null)
            list = new ArrayList<Coord>();
        
        addLeavesInDistance(world, pos, treeDef.maxHorLeafBreakDist(), list);
        
        while (++index < list.size())
        {
            Coord pos2 = list.get(index);
            if (CommonUtils.getHorSquaredDistance(pos, pos2) < treeDef.maxHorLeafBreakDist())
                addLeavesInDistance(world, pos2, 1, list);
        }
        
        return list;
    }
    
    public void addLeavesInDistance(World world, Coord pos, int range, List<Coord> list)
    {
        for (int x = -range; x <= range; x++)
            for (int y = -range; y <= range; y++)
                for (int z = -range; z <= range; z++)
                {
                    int blockID = world.getBlockId(x + pos.x, y + pos.y, z + pos.z);
                    int md = world.getBlockMetadata(x + pos.x, y + pos.y, z + pos.z);
                    if (isLeafBlock(new BlockID(blockID, md)) || vineID.equals(new BlockID(blockID)))
                    {
                        int metadata = world.getBlockMetadata(x + pos.x, y + pos.y, z + pos.z);
                        if (!treeDef.requireLeafDecayCheck() || ((metadata & 8) != 0 && (metadata & 4) == 0))
                        {
                            Coord newPos = new Coord(x + pos.x, y + pos.y, z + pos.z);
                            // check hasLogClose() here so we don't have to reiterate the list to remove other trees' leaves
                            // @Deprecates removeLeavesWithLogsAround()
                            if (!list.contains(newPos) && !hasLogClose(world, newPos, 1))
                                list.add(newPos);
                        }
                    }
                }
    }
    
    /*
     * So far this method is utter shit and doesn't work right
     */
    protected void addConnectedLeavesInRange(World world, Coord pos, int range, int distance, List<Coord> list)
    {
        
        if (!list.contains(pos))
        {
            int blockID = world.getBlockId(pos.x, pos.y, pos.z);
            int md = world.getBlockMetadata(pos.x, pos.y, pos.z);
            if (Math.abs(distance) <= range)
            {
                if (isLeafBlock(new BlockID(blockID, md)) || vineID.equals(new BlockID(blockID)) || distance == 0)
                {
                    if ((!treeDef.requireLeafDecayCheck() || ((md & 8) != 0 && (md & 4) == 0)) && distance != 0)
                    {
                        list.add(pos);
                    }
                    
                    for (int x = -1; x <= 1; x++)
                        for (int y = -1; y <= 1; y++)
                            for (int z = -1; z <= 1; z++)
                            {
                                addConnectedLeavesInRange(world, new Coord(pos.x + x, pos.y + y, pos.z + z), range, distance + 1, list);
                            }
                }
            }
        }
    }
    
    /**
     * Returns true if a log block is within i blocks of pos
     */
    public boolean hasLogClose(World world, Coord pos, int i)
    {
        for (int x = -i; x <= i; x++)
            for (int y = -i; y <= i; y++)
                for (int z = -i; z <= i; z++)
                {
                    Coord neighbor = new Coord(x + pos.x, y + pos.y, z + pos.z);
                    int neighborID = world.getBlockId(neighbor.x, neighbor.y, neighbor.z);
                    /*
                     * Use TreeCapitator.logIDList here so that we find ANY type of log block, not just the type for this tree
                     */
                    if ((x != 0 || y != 0 || z != 0) && neighborID != 0 &&
                            masterLogList.contains(new BlockID(world, neighbor.x, neighbor.y, neighbor.z))
                            && !neighbor.equals(startPos))
                        return true;
                }
        
        return false;
    }
}
