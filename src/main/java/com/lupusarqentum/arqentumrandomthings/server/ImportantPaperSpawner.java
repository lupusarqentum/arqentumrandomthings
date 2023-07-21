package com.lupusarqentum.arqentumrandomthings.server;

import com.lupusarqentum.arqentumrandomthings.common.Random;
import com.lupusarqentum.arqentumrandomthings.common.itemsregistration.InventoryItemsRegistration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.lang.reflect.Method;

public class ImportantPaperSpawner {

    private static ImportantPaperSpawner self;

    public static void init(@NotNull IEventBus eventBus) {
        self = new ImportantPaperSpawner();
        eventBus.addListener(self::onChestOpened);
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

    private void onChestOpened(PlayerContainerEvent.@NotNull Open event) {
        if (event.getEntity().level.isClientSide) {
            return;
        }
        //if (!(Random.nextFloat() < 0.02)) {
        //    return;
        //}

        Item air = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:air"));
        Item impp = InventoryItemsRegistration.IMPORTANT_PAPER.get();

        Container chest = getContainerFrom(event);
        if (chest == null) {
            return;
        }

        int size = chest.getContainerSize();
        for (int i = 0; i < size; i++) {
            if (chest.getItem(i).is(air)) {
                ItemStack itemStack = new ItemStack(impp);
                chest.setItem(i, itemStack);
                break;
            }
        }
    }

}
