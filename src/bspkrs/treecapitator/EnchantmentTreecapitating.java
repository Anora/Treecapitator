package bspkrs.treecapitator;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EnchantmentTreecapitating extends Enchantment
{
    protected EnchantmentTreecapitating(int par1, int par2)
    {
        super(par1, par2, EnumEnchantmentType.digger);
    }
    
    @Override
    public boolean canApply(ItemStack itemStack)
    {
        return itemStack.isItemEnchantable() && ToolRegistry.instance().isAxe(itemStack);
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack itemStack)
    {
        return type.canEnchantItem(itemStack.getItem());
    }
    
    @Override
    public int getMinEnchantability(int par1)
    {
        return 10;
    }
    
    @Override
    public int getMaxEnchantability(int par1)
    {
        return 60;
    }
    
    @Override
    public int getMaxLevel()
    {
        return 1;
    }
    
    @Override
    public String getName()
    {
        return "Treecapitating";
    }
    
    @Override
    public boolean canApplyTogether(Enchantment enchantment)
    {
        return super.canApplyTogether(enchantment) && enchantment.effectId != fortune.effectId;
    }
    
}
