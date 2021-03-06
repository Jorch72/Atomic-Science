package com.builtbroken.atomic.content.machines.processing.recipes;

import com.builtbroken.atomic.content.machines.processing.TileEntityProcessingMachine;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

/**
 * Prefab for recipes used in the processing machine
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 5/25/2018.
 */
public abstract class ProcessingRecipe<H extends TileEntityProcessingMachine> //TODO convert to interface for other mods to add recipes
{
    /**
     * Checks if the current state of the machine matches
     * a given recipe.
     * <p>
     * Use the machine to access inventories, fluid tanks,
     * and power as needed.
     *
     * @param machine - machine to apply recipe to
     * @return true if the state matches the recipe
     */
    public abstract boolean matches(H machine);

    /**
     * Called to process the recipe and apply
     * the results to the machine
     * <p>
     * Use the machine to access inventories, fluid tanks,
     * and power as needed.
     *
     * @param machine - machine to apply recipe to
     * @return true when recipe was applied
     */
    public abstract boolean applyRecipe(H machine);

    /**
     * Checks if the fluid is a component in the recipe.
     * <p>
     * This is mainly used to handle fluid input into the machine
     *
     * @param fluid
     * @param machine - machine to apply recipe to
     * @return
     */
    public abstract boolean isComponent(H machine, Fluid fluid);

    /**
     * Checks if the item is a component in the recipe.
     * <p>
     * This is mainly used to handle inventory input into the machine
     *
     * @param stack
     * @param machine - machine to apply recipe to
     * @return
     */
    public abstract boolean isComponent(H machine, ItemStack stack);
}
