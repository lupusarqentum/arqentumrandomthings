package com.lupusarqentum.arqentumrandomthings.common.block;

import com.lupusarqentum.arqentumrandomthings.RandomThingsMod;
import com.lupusarqentum.arqentumrandomthings.common.item.Foods;
import com.lupusarqentum.arqentumrandomthings.common.item.ItemsRegistration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;

public class BlocksRegistration {
    public class BlocksIDs {
        public static final String CHEESE_BLOCK = "cheese_block";
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RandomThingsMod.MODID);

    public static final RegistryObject<Block> CHEESE_BLOCK = BLOCKS.register(BlocksIDs.CHEESE_BLOCK, () ->
            new Block(BlockBehaviour.Properties.of(Material.CAKE).strength(0.2f)));
    public static final RegistryObject<Item> CHEESE_BLOCK_ITEM = ItemsRegistration.ITEMS.register(BlocksIDs.CHEESE_BLOCK, () ->
            new BlockItem(CHEESE_BLOCK.get(), new Item.Properties().food(Foods.CHEESE_BLOCK)));

    private static final HashMap<CreativeModeTab, ArrayList<String>> blockItemsToAddToCreativeTab = new HashMap<>();

    static {
        blockItemsToAddToCreativeTab.put(CreativeModeTabs.BUILDING_BLOCKS, new ArrayList<>());
        blockItemsToAddToCreativeTab.put(CreativeModeTabs.COLORED_BLOCKS, new ArrayList<>());
        blockItemsToAddToCreativeTab.put(CreativeModeTabs.FUNCTIONAL_BLOCKS, new ArrayList<>());
        blockItemsToAddToCreativeTab.put(CreativeModeTabs.REDSTONE_BLOCKS, new ArrayList<>());
        blockItemsToAddToCreativeTab.put(CreativeModeTabs.NATURAL_BLOCKS, new ArrayList<>());
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        modEventBus.addListener(BlocksRegistration::addToCreativeTab);
    }

    private static void registerBasicBlock(String blockID, Supplier<? extends Block> sup, CreativeModeTab tab) {
        BLOCKS.register(blockID, sup);
        ItemsRegistration.ITEMS.register(blockID, () ->
                new BlockItem(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(
                        new ResourceLocation(RandomThingsMod.MODID + ":" + blockID))), new Item.Properties()));
        if (blockItemsToAddToCreativeTab.containsKey(tab)) {
            blockItemsToAddToCreativeTab.get(tab).add(blockID);
        }
    }

    private static void addToCreativeTab(CreativeModeTabEvent.@NotNull BuildContents event) {
        if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(new ItemStack(ForgeRegistries.ITEMS.getValue(
                    new ResourceLocation(RandomThingsMod.MODID + ":" + BlocksIDs.CHEESE_BLOCK))));
        }
        if (blockItemsToAddToCreativeTab.containsKey(event.getTab())) {
            for (String blockId : blockItemsToAddToCreativeTab.get(event.getTab())) {
                event.accept(new ItemStack(ForgeRegistries.ITEMS.getValue(
                        new ResourceLocation(RandomThingsMod.MODID + ":" + blockId))));
            }
        }
    }
}
