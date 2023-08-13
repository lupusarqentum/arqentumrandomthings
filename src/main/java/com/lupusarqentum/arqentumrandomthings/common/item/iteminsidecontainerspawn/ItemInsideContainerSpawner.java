package com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn;

import com.lupusarqentum.arqentumrandomthings.common.ModConfig;
import com.lupusarqentum.arqentumrandomthings.common.Random;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemInsideContainerSpawner {

    public static ItemInsideContainerSpawner instance = new ItemInsideContainerSpawner();

    private List<ItemInsideContainerSpawningParams> itemsSpawning;

    private ItemInsideContainerSpawner() {}

    public void init(@NotNull IEventBus eventBus) {
        itemsSpawning = new ArrayList<>();
        eventBus.addListener(this::onContainerOpened);
    }

    public void addItemToSpawn(ItemInsideContainerSpawningParams itemInsideContainerSpawningParams) {
        itemsSpawning.add(itemInsideContainerSpawningParams);
    }

    private boolean canMultipleItemsSpawnAtOnce() {
        return ModConfig.canMultipleItemsSpawnInsideContainerAtOnce.get();
    }

    private void onContainerOpened(PlayerContainerEvent.@NotNull Open event) {
        ItemInsideContainerSpawningContext context;
        try {
            context = new ItemInsideContainerSpawningContext(event);
        } catch (Exception e) {
            return;
        }

        if (canMultipleItemsSpawnAtOnce()) {
            for (var itemSpawning : itemsSpawning) {
                if (Random.rollProbability(itemSpawning.getProbability(context))) {
                    executeItemSpawning(itemSpawning, context);
                }
            }
        } else {
            double probabilitiesSum = 0.0;
            for (var itemSpawning : itemsSpawning) {
                probabilitiesSum += itemSpawning.getProbability(context);
            }
            if (probabilitiesSum > 1.0 || Random.rollProbability(probabilitiesSum)) {
                double randomNumber = Random.nextDouble(0.0, probabilitiesSum);
                double skippedProp = 0.0;
                for (var itemSpawning : itemsSpawning) {
                    if (randomNumber > skippedProp && randomNumber < skippedProp + itemSpawning.getProbability(context)) {
                        executeItemSpawning(itemSpawning, context);
                        return;
                    } else {
                        skippedProp += itemSpawning.getProbability(context);
                    }
                }
            }
        }
    }

    private void executeItemSpawning(@NotNull ItemInsideContainerSpawningParams itemSpawning, ItemInsideContainerSpawningContext context) {
        int slotIndex = itemSpawning.chooseSlotIndex(context);
        ItemStack itemStack = itemSpawning.getItemStack(context);
        if (slotIndex == -1 || itemStack == null) {
            return;
        }
        context.container().setItem(slotIndex, itemStack);
    }
}
