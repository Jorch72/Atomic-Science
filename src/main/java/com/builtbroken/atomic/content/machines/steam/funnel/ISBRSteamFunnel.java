package com.builtbroken.atomic.content.machines.steam.funnel;

import com.builtbroken.atomic.AtomicScience;
import com.builtbroken.atomic.content.ASFluids;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 6/16/2018.
 */
public class ISBRSteamFunnel implements ISimpleBlockRenderingHandler
{
    public final static double pixel = 1.0 / 16.0;
    public final static int ID = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        renderWorldBlock(null, 0, -1, metadata, block, modelId, renderer);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        //Top
        bounds(renderer,
                pixel * 3, pixel * 8, pixel * 3,
                1 - pixel * 6, pixel * 8, 1 - pixel * 6);
        renderBlock(renderer, block, 6, x, y, z, null);

        //Edges
        if (world != null)
        {
            //North
            if(hasBlock(world, x, y, z, ForgeDirection.NORTH))
            {
                bounds(renderer,
                        pixel * 4, pixel * 7, 0,
                        pixel * 8, pixel * 8, pixel * 3);
                renderBlock(renderer, block, 5, x, y, z, null);
            }

            //South
            if(hasBlock(world, x, y, z, ForgeDirection.SOUTH))
            {
                bounds(renderer,
                        pixel * 4, pixel * 7, pixel * 3,
                        pixel * 8, pixel * 8, 1 - pixel * 3);
                renderBlock(renderer, block, 5, x, y, z, null);
            }

            //East
            if(hasBlock(world, x, y, z, ForgeDirection.EAST))
            {
                bounds(renderer,
                        pixel * 3, pixel * 7, pixel * 4,
                        1 - pixel * 3, pixel * 8, pixel * 8);
                renderBlock(renderer, block, 5, x, y, z, null);
            }

            //West
            if(hasBlock(world, x, y, z, ForgeDirection.WEST))
            {
                bounds(renderer,
                        0, pixel * 7, pixel * 4,
                        pixel * 3, pixel * 8, pixel * 8);
                renderBlock(renderer, block, 5, x, y, z, null);
            }
        }

        //layer 2
        bounds(renderer,
                pixel, pixel * 2, pixel,
                1 - pixel * 2, pixel * 6, 1 - pixel * 2);
        renderBlock(renderer, block, 1, x, y, z, null);

        //layer 1
        bounds(renderer,
                0, 0, 0,
                1, pixel * 2, 1);
        renderBlock(renderer, block, 0, x, y, z, null);

        return true;
    }

    protected boolean hasBlock(IBlockAccess world, int x, int y, int z, ForgeDirection direction)
    {
        x += direction.offsetX;
        y += direction.offsetY;
        z += direction.offsetZ;

        if(world != null)
        {
            Block block = world.getBlock(x, y, z);
            if(block != null && !block.isAir(world, x, y, z))  //ChunkCache should return air as an empty value
            {
                if(block.isSideSolid(world, x, y, z, direction.getOpposite()))
                {
                    return true;
                }
                TileEntity tile = world.getTileEntity(x, y, z);
                if(tile instanceof IFluidHandler)
                {
                    return ((IFluidHandler) tile).canFill(direction.getOpposite(), ASFluids.STEAM.fluid);
                }
            }
        }
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return ID;
    }

    protected void bounds(RenderBlocks renderer, double x, double y, double z, double xx, double yy, double zz)
    {
        renderer.setRenderBounds(x, y, z, x + xx, y + yy, z + zz);
    }

    public void renderBlock(RenderBlocks renderer, Block block, int meta, int x, int y, int z, IIcon overrideTexture)
    {
        if (y == -1)
        {
            Tessellator t = Tessellator.instance;
            t.startDrawingQuads();

            IIcon useTexture = overrideTexture != null ? overrideTexture : getTextureSafe(block, 0, meta);
            t.setNormal(0.0F, -1.0F, 0.0F);
            renderer.renderFaceYNeg(block, 0, 0, 0, useTexture);

            useTexture = overrideTexture != null ? overrideTexture : getTextureSafe(block, 1, meta);
            t.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(block, 0, 0, 0, useTexture);

            useTexture = overrideTexture != null ? overrideTexture : getTextureSafe(block, 2, meta);
            t.setNormal(0.0F, 0.0F, -1.0F);
            renderer.renderFaceZNeg(block, 0, 0, 0, useTexture);

            useTexture = overrideTexture != null ? overrideTexture : getTextureSafe(block, 3, meta);
            t.setNormal(0.0F, 0.0F, 1.0F);
            renderer.renderFaceZPos(block, 0, 0, 0, useTexture);

            useTexture = overrideTexture != null ? overrideTexture : getTextureSafe(block, 4, meta);
            t.setNormal(-1.0F, 0.0F, 0.0F);
            renderer.renderFaceXNeg(block, 0, 0, 0, useTexture);

            useTexture = overrideTexture != null ? overrideTexture : getTextureSafe(block, 5, meta);
            t.setNormal(1.0F, 0.0F, 0.0F);
            renderer.renderFaceXPos(block, 0, 0, 0, useTexture);
            t.draw();
        }
        else
        {
            renderer.setOverrideBlockTexture(overrideTexture);
            renderer.renderStandardBlock(block, x, y, z);
            renderer.setOverrideBlockTexture(null);
        }
    }

    public static IIcon getTextureSafe(Block block, int side, int meta)
    {
        if (block != null)
        {
            IIcon icon;
            try
            {
                icon = block.getIcon(side, meta);
            }
            catch (Exception e)
            {
                if (AtomicScience.runningAsDev)
                {
                    AtomicScience.logger.error("Error getting icon for " + block + " M:" + meta + " S:" + side, e);
                }
                icon = Blocks.wool.getIcon(meta, side);
            }
            if (icon == null)
            {
                return Blocks.stone.getIcon(0, 0);
            }
            return icon;
        }
        return Blocks.stone.getIcon(0, 0);
    }
}
