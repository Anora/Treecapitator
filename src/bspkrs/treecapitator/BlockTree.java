package bspkrs.treecapitator;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import bspkrs.util.BlockID;

public class BlockTree extends net.minecraft.block.BlockLog
{
    private TreeBlockBreaker breaker;
    
    public BlockTree(int i)
    {
        super(i);
        setHardness(TreeCapitator.logHardnessNormal);
        setStepSound(Block.soundWoodFootstep);
        setBlockName("log");
        setRequiresSelfNotify();
    }
    
    /**
     * Called when the block is attempted to be harvested
     */
    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int md, EntityPlayer entityPlayer)
    {
        ArrayList<BlockID> log = new ArrayList<BlockID>();
        log.add(new BlockID(Block.wood.blockID, (TreeCapitator.useStrictBlockPairing ? md : -1)));
        ArrayList<BlockID> leaf = new ArrayList<BlockID>();
        leaf.add(new BlockID(Block.leaves.blockID, (TreeCapitator.useStrictBlockPairing ? md : -1)));
        breaker = new TreeBlockBreaker(entityPlayer, log, leaf);
        breaker.onBlockHarvested(world, x, y, z, md, entityPlayer);
    }
    
    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    @Override
    public float getBlockHardness(World par1World, int par2, int par3, int par4)
    {
        return breaker != null ? breaker.getBlockHardness() : TreeCapitator.logHardnessNormal;
    }
}
