package com.builtbroken.atomic.client;

import com.builtbroken.atomic.AtomicScience;
import com.builtbroken.atomic.content.ASBlocks;
import com.builtbroken.atomic.content.ASItems;
import com.builtbroken.atomic.content.effects.client.RenderRadOverlay;
import com.builtbroken.atomic.content.machines.processing.boiler.TileEntityChemBoiler;
import com.builtbroken.atomic.content.machines.processing.boiler.render.TESRChemBoiler;
import com.builtbroken.atomic.content.machines.processing.centrifuge.TileEntityChemCentrifuge;
import com.builtbroken.atomic.content.machines.processing.centrifuge.render.TESRChemCentrifuge;
import com.builtbroken.atomic.content.machines.processing.extractor.TileEntityChemExtractor;
import com.builtbroken.atomic.content.machines.processing.extractor.render.TESRChemExtractor;
import com.builtbroken.atomic.content.machines.steam.generator.TESRSteamGenerator;
import com.builtbroken.atomic.content.machines.steam.generator.TileEntitySteamGenerator;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Handles registering renders and models
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/18/2018.
 */
@Mod.EventBusSubscriber(modid = AtomicScience.DOMAIN)
public class ASClientReg
{
    private static final String INVENTORY = "inventory";
    private static final String EMPTY = "";

    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ASClientReg());
        MinecraftForge.EVENT_BUS.register(RenderRadOverlay.INSTANCE);

        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReactorCell.class, new TESRReactorCell());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySteamGenerator.class, new TESRSteamGenerator());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChemExtractor.class, new TESRChemExtractor());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChemBoiler.class, new TESRChemBoiler());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChemCentrifuge.class, new TESRChemCentrifuge());

        //Armor
        newItemModel(ASItems.itemArmorHazmatHelm);
        newItemModel(ASItems.itemArmorHazmatChest);
        newItemModel(ASItems.itemArmorHazmatLegs);
        newItemModel(ASItems.itemArmorHazmatBoots);

        //Cells
        newItemModel(ASItems.itemFluidCell);
        newItemModel(ASItems.itemPoweredCell);

        newItemModel(ASItems.itemFissileFuelCell);
        newItemModel(ASItems.itemBreederFuelCell);

        newItemModel(ASItems.itemEmptyCell);

        //Crafting
        newItemModel(ASItems.itemYellowCake);
        newItemModel(ASItems.itemUranium235);
        newItemModel(ASItems.itemUranium238);

        newItemModel(ASItems.itemProcessingWaste);
        newItemModel(ASItems.itemToxicWaste);

        //Tools
        newItemModel(ASItems.itemHeatProbe);
        newItemModel(ASItems.itemWrench);

        //Ore
        newBlockModel(ASBlocks.blockUraniumOre);

        //Reactor
        newBlockModel(ASBlocks.blockReactorCell);
        newBlockModel(ASBlocks.blockReactorController);

        //Steam consumers
        newBlockModel(ASBlocks.blockSteamFunnel);
        newBlockModel(ASBlocks.blockSteamTurbine);

        //Processing machines
        newBlockModel(ASBlocks.blockChemExtractor);
        newBlockModel(ASBlocks.blockChemBoiler);
        newBlockModel(ASBlocks.blockChemCentrifuge);
    }

    protected static void newBlockModel(Block block)
    {
        newBlockModel(block, 0,  INVENTORY, EMPTY);
    }

    protected static void newBlockModel(Block block, int meta, String varient, String sub)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(block.getRegistryName() + sub, varient));
    }

    protected static void newItemModel(Item item)
    {
        newItemModel(item, 0,  INVENTORY, EMPTY);
    }

    protected static void newItemModel(Item item, int meta, String varient, String sub)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName() + sub, varient));
    }
}
