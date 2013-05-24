package bspkrs.treecapitator.fml;

import net.minecraft.block.Block;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import bspkrs.treecapitator.TCSettings;
import bspkrs.treecapitator.TreeCapitator;
import bspkrs.treecapitator.TreeRegistry;
import bspkrs.util.BlockID;

public class PlayerHandler
{
    @ForgeSubscribe
    public void onBlockClicked(PlayerInteractEvent event)
    {
        if (event.action.equals(PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) && TreeCapitatorMod.proxy.isEnabled())
        {
            Block block = Block.blocksList[event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z)];
            
            if (block != null)
            {
                int metadata = event.entityPlayer.worldObj.getBlockMetadata(event.x, event.y, event.z);
                
                if (TCSettings.allowDebugOutput)
                    TreeCapitatorMod.proxy.debugOutputBlockID(block.blockID, metadata);
                
                BlockID blockID = new BlockID(block, metadata);
                
                if (TreeRegistry.instance().isRegistered(blockID))
                {
                    block.setHardness(TreeCapitator.getBlockHardness(event.entityPlayer));
                }
            }
        }
    }
    
    @ForgeSubscribe
    public void getPlayerBreakSpeed(BreakSpeed event)
    {
        BlockID blockID = new BlockID(event.block.blockID, event.metadata);
        
        if (TreeCapitatorMod.proxy.isEnabled() && TreeRegistry.instance().isRegistered(blockID) &&
                TreeCapitator.isAxeItemEquipped(event.entityPlayer))
        {
            event.newSpeed = event.originalSpeed * TreeRegistry.instance().get(blockID).breakSpeedModifier();
        }
    }
}
