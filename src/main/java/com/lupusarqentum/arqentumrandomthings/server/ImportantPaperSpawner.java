package com.lupusarqentum.arqentumrandomthings.server;

import com.lupusarqentum.arqentumrandomthings.common.Logger;
import com.lupusarqentum.arqentumrandomthings.common.Random;
import com.lupusarqentum.arqentumrandomthings.common.itemsregistration.InventoryItemsRegistration;

import java.lang.reflect.Method;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ImportantPaperSpawner {

    private static ImportantPaperSpawner self;

    public static void init(@NotNull IEventBus eventBus) {
        self = new ImportantPaperSpawner();
        eventBus.addListener(self::onChestOpened);
    }

    private @NotNull Item getAirItem() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:air"));
    }

    private @NotNull Item getImportantPaperItem() {
        return InventoryItemsRegistration.IMPORTANT_PAPER.get();
    }

    private void onChestOpened(PlayerContainerEvent.@NotNull Open event) {
        if (event.getEntity().level.isClientSide
                || Random.rollProbability(getSpawnProbability()) == false) {
            return;
        }
        Container chest = getContainerFrom(event);
        if (chest == null) {
            return;
        }
        int free_slot_index = findFreeSlot(chest);
        if (free_slot_index == -1) {
            return;
        }
        chest.setItem(free_slot_index, getImportantPaperItemStack(event.getEntity()));
    }

    private int findFreeSlot(@NotNull Container chest) {
        int size = chest.getContainerSize();
        int freeSlotsCount = 0;
        Item air = getAirItem();
        for (int i = 0; i < size; i++) {
            if (chest.getItem(i).is(air)) {
                freeSlotsCount++;
            }
        }
        if (freeSlotsCount == 0) {
            return -1;
        }
        int freeSlotToReturnNumber = Random.nextInt(1, freeSlotsCount);
        for (int i = 0; i < size; i++) {
            if (chest.getItem(i).is(air)) {
                freeSlotToReturnNumber--;
                if (freeSlotToReturnNumber == 0) {
                    return i;
                }
            }
        }
        Logger.error("Important Paper Spawning failed #68305");
        return -1;
    }

    private @NotNull ItemStack getImportantPaperItemStack(Player entity) {
        ItemStack itemStack = new ItemStack(getImportantPaperItem());
        return itemStack;
    }

    private float getSpawnProbability() {
        return 0.3f;
    }

    private @Nullable Container getContainerFrom(@NotNull PlayerContainerEvent event) {
        if (event.getContainer() instanceof ChestMenu) {
            return ((ChestMenu)(event.getContainer())).getContainer();
        }
        if (event.getContainer() instanceof AbstractContainerMenu) {
            Method getContainerMethod;
            try {
                getContainerMethod = event.getContainer().getClass().getMethod("getContainer", new Class[0]);
            } catch (Exception e) {
                return null;
            }
            Object value;
            try {
                value = getContainerMethod.invoke(event.getContainer(), new Object[0]);
            } catch (Exception e) {
                return null;
            }
            if (value instanceof Container) {
                return (Container)value;
            }
        }
        return null;
    }
}
