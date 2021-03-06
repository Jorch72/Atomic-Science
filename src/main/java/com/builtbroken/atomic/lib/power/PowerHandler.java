package com.builtbroken.atomic.lib.power;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Simple wrapper for power systems
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 5/17/2018.
 */
public abstract class PowerHandler
{
    public boolean canHandle(ForgeDirection side, TileEntity tile)
    {
        return false;
    }

    public boolean canHandle(ItemStack stack)
    {
        return false;
    }

    public int addPower(ForgeDirection side, TileEntity tileEntity, int power, boolean doAction)
    {
        return 0;
    }

    public int removePower(ForgeDirection side, TileEntity tileEntity, int power, boolean doAction)
    {
        return 0;
    }


    public int addPower(ItemStack stack, int power, boolean doAction)
    {
        return 0;
    }

    public int removePower(ItemStack stack, int power, boolean doAction)
    {
        return 0;
    }

    public int getPowerStored(ItemStack itemStack)
    {
        return 0;
    }
}
