package com.builtbroken.atomicscienceclassic.content.centrifuge;

import com.builtbroken.atomicscienceclassic.content.centrifuge.TileCentrifuge;
import com.builtbroken.mc.lib.render.RenderUtility;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import com.builtbroken.atomicscienceclassic.Atomic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCentrifuge extends TileEntitySpecialRenderer
{
    public static final IModelCustom MODEL = AdvancedModelLoader.loadModel(new ResourceLocation(Atomic.MODEL_DIRECTORY + "centrifuge.tcn"));
    public static final ResourceLocation TEXTURE = new ResourceLocation(Atomic.DOMAIN, Atomic.MODEL_PATH + "centrifuge.png");

    public void render(TileCentrifuge tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);

        if (tileEntity.world() != null)
        {
            RenderUtility.rotateBlockBasedOnDirection(tileEntity.getDirection());
        }

        bindTexture(TEXTURE);

        GL11.glPushMatrix();
        GL11.glRotated(Math.toDegrees(tileEntity.rotation), 0, 1, 0);
        MODEL.renderOnly("C", "JROT", "KROT", "LROT", "MROT");
        GL11.glPopMatrix();

        MODEL.renderAllExcept("C", "JROT", "KROT", "LROT", "MROT");
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double var2, double var4, double var6, float var8)
    {
        this.render((TileCentrifuge) tileEntity, var2, var4, var6, var8);
    }
}