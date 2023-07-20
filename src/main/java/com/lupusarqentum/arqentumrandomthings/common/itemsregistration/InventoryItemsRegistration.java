package com.lupusarqentum.arqentumrandomthings.common.itemsregistration;

import com.lupusarqentum.arqentumrandomthings.RandomThingsMod;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InventoryItemsRegistration {
    public class ItemsIDs {
        public final static String important_paper = "important_paper";
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RandomThingsMod.MODID);

    public static final RegistryObject<Item> IMPORTANT_PAPER = ITEMS.register(ItemsIDs.important_paper, () -> new ImportantPaperItem(
            new Item.Properties().fireResistant().rarity(Rarity.EPIC).stacksTo(1)));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
