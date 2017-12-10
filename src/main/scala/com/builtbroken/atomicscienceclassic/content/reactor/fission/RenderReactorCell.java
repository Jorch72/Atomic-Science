package com.builtbroken.atomicscienceclassic.content.reactor.fission;

import com.builtbroken.atomicscienceclassic.Atomic;
import com.builtbroken.mc.lib.render.RenderUtility;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.client.renderers.models.ModelCube;

@SideOnly(Side.CLIENT)
public class RenderReactorCell extends TileEntitySpecialRenderer
{
    public static final IModelCustom MODEL_TOP = AdvancedModelLoader.loadModel(new ResourceLocation(Atomic.MODEL_DIRECTORY + "reactorCellTop.tcn"));
    public static final IModelCustom MODEL_MIDDLE = AdvancedModelLoader.loadModel(new ResourceLocation(Atomic.MODEL_DIRECTORY + "reactorCellMiddle.tcn"));
    public static final IModelCustom MODEL_BOTTOM = AdvancedModelLoader.loadModel(new ResourceLocation(Atomic.MODEL_DIRECTORY + "reactorCellBottom.tcn"));

    public static final ResourceLocation TEXTURE_TOP = new ResourceLocation(Atomic.DOMAIN, Atomic.MODEL_PATH + "reactorCellTop.png");
    public static final ResourceLocation TEXTURE_MIDDLE = new ResourceLocation(Atomic.DOMAIN, Atomic.MODEL_PATH + "reactorCellMiddle.png");
    public static final ResourceLocation TEXTURE_BOTTOM = new ResourceLocation(Atomic.DOMAIN, Atomic.MODEL_PATH + "reactorCellBottom.png");
    public static final ResourceLocation TEXTURE_FISSILE = new ResourceLocation(Atomic.DOMAIN, Atomic.MODEL_PATH + "fissileMaterial.png");

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f)
    {
        TileReactorCell tileEntity = (TileReactorCell) t;

        // Render main body
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);

        int meta = 2;

        if (tileEntity.world() != null)
        {
            meta = tileEntity.getBlockMetadata();
        }

        boolean hasBelow = tileEntity.world() != null && ((TileReactorCell) t).world().getTile(t.xCoord, t.yCoord - 1, t.zCoord) instanceof TileReactorCell;

        switch (meta)
        {
        case 0:
            bindTexture(TEXTURE_BOTTOM);
            MODEL_BOTTOM.renderAll();
            break;
        case 1:
            bindTexture(TEXTURE_MIDDLE);
            GL11.glTranslatef(0, 0.075f, 0);
            GL11.glScalef(1f, 1.15f, 1f);
            MODEL_MIDDLE.renderAll();
            break;
        case 2:
            bindTexture(TEXTURE_TOP);

            if (hasBelow)
            {
                GL11.glScalef(1f, 1.32f, 1f);
            }
            else
            {
                GL11.glTranslatef(0, 0.1f, 0);
                GL11.glScalef(1f, 1.2f, 1f);
            }

            if (hasBelow)
            {
                MODEL_TOP.renderAllExcept("BottomPad", "BaseDepth", "BaseWidth", "Base");
            }
            else
            {
                MODEL_TOP.renderAll();
            }

            break;
        }

        GL11.glPopMatrix();

        // Render the fuel within the reactor
        if (tileEntity.getStackInSlot(0) != null)
        {
            float height = tileEntity.getHeight() * ((float) (tileEntity.getStackInSlot(0).getMaxDamage() - tileEntity.getStackInSlot(0).getItemDamage()) / (float) tileEntity.getStackInSlot(0).getMaxDamage());

            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F * height, (float) z + 0.5F);
            GL11.glScalef(0.4f, 0.9f * height, 0.4f);
            bindTexture(TEXTURE_FISSILE);
            RenderUtility.disableLighting();
            ModelCube.INSTNACE.render();
            RenderUtility.enableLighting();
            GL11.glPopMatrix();
        }
    }
}