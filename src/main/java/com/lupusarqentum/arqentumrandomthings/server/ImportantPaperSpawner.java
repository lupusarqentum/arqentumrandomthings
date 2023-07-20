package com.lupusarqentum.arqentumrandomthings.server;

import com.lupusarqentum.arqentumrandomthings.common.Random;
import com.lupusarqentum.arqentumrandomthings.itemsregistration.InventoryItemsRegistration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

public class ImportantPaperSpawner {

    private static ImportantPaperSpawner self;

    public static void init(IEventBus eventBus) {
        self = new ImportantPaperSpawner();
        eventBus.addListener(self::onChestOpened);
    }

    private void onChestOpened(PlayerContainerEvent.Open event) {
        if (event.getEntity().level.isClientSide) {
            return;
        }
        if (!(event.getContainer() instanceof ChestMenu)) {
            return;
        }
        if (!(Random.nextFloat() < 0.02)) {
            return;
        }

        Item air = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:air"));
        Item impp = InventoryItemsRegistration.IMPORTANT_PAPER.get();
        ChestMenu chest = (ChestMenu) (event.getContainer());
        int size = chest.getContainer().getContainerSize();
        for (int i = 0; i < size; i++) {
            if (chest.getContainer().getItem(i).is(air)) {
                ItemStack itemStack = new ItemStack(impp);
                chest.getContainer().setItem(i, itemStack);
                break;
            }
        }
    }

}
