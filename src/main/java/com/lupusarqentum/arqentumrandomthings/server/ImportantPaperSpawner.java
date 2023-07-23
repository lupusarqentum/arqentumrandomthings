package com.lupusarqentum.arqentumrandomthings.server;

import com.lupusarqentum.arqentumrandomthings.common.Logger;
import com.lupusarqentum.arqentumrandomthings.common.Random;
import com.lupusarqentum.arqentumrandomthings.common.itemsregistration.InventoryItemsRegistration;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ImportantPaperSpawner {

    private static ImportantPaperSpawner self;

    private Map<String, Long> lastTimePLayerFishedUpItem;

    public static void init(@NotNull IEventBus eventBus) {
        self = new ImportantPaperSpawner();
        self.lastTimePLayerFishedUpItem = new HashMap<String, Long>();
        eventBus.addListener(self::onChestOpened);
        eventBus.addListener(self::onItemFished);
        eventBus.addListener(self::onItemPickedUp);
    }

    private @NotNull Item getAirItem() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:air"));
    }

    private @NotNull Item getImportantPaperItem() {
        return InventoryItemsRegistration.IMPORTANT_PAPER.get();
    }

    private void onItemPickedUp(PlayerEvent.@NotNull ItemPickupEvent event) {
        if (event.getEntity().level.isClientSide == true) {
            return;
        }
        Player player = event.getEntity();
        String playerName = player.getDisplayName().getString();
        if (lastTimePLayerFishedUpItem.containsKey(playerName)) {
            Long currentTimeSinceEpoch = Instant.now().getEpochSecond();
            Long itemFishedTimeSinceEpoch = lastTimePLayerFishedUpItem.get(playerName);
            if (currentTimeSinceEpoch - itemFishedTimeSinceEpoch < 4) {
                if(player.getInventory().add(getImportantPaperItemStack(player)) == false) {
                    // drop important paper in the world
                    Level world = player.level;
                    Vec3 playerPos = player.position();
                    ItemStack itemStack = getImportantPaperItemStack(player);
                    ItemEntity droppedItem = new ItemEntity(world, playerPos.x, playerPos.y + 1, playerPos.z, itemStack);
                    world.addFreshEntity(droppedItem);
                }
            }
            lastTimePLayerFishedUpItem.remove(playerName);
        }
    }

    private void onItemFished(@NotNull ItemFishedEvent event) {
        if (event.getEntity().level.isClientSide == false && Random.rollProbability(getFishingSpawnProbability())) {
            Player player = event.getEntity();
            String playerName = player.getDisplayName().getString();
            Long timeSinceEpoch = Instant.now().getEpochSecond();
            lastTimePLayerFishedUpItem.put(playerName, timeSinceEpoch);
        }
    }

    private void onChestOpened(PlayerContainerEvent.@NotNull Open event) {
        if (event.getEntity().level.isClientSide
                || Random.rollProbability(getChestSpawnProbability()) == false) {
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

    private @NotNull ItemStack getImportantPaperItemStack(@NotNull Player entity) {
        ItemStack itemStack = new ItemStack(getImportantPaperItem());
        long creating_date = Instant.now().getEpochSecond();
        CompoundTag nbt = new CompoundTag();
        nbt.putString("player_received", entity.getDisplayName().getString());
        nbt.putLong("paper_created_date", creating_date);
        itemStack.setTag(nbt);
        return itemStack;
    }

    private float getChestSpawnProbability() {
        return 0.3f;
    }

    private float getFishingSpawnProbability() {
        return 1f;
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
