package com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn;

import com.lupusarqentum.arqentumrandomthings.common.Util;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import com.lupusarqentum.arqentumrandomthings.common.Logger;
import com.lupusarqentum.arqentumrandomthings.common.Random;

public abstract class ItemInsideContainerSpawningParams {
    public abstract double getProbability(ItemInsideContainerSpawningContext context);
    public abstract ItemStack getItemStack(ItemInsideContainerSpawningContext context);
    public int chooseSlotIndex(@NotNull ItemInsideContainerSpawningContext context) {
        Container chest = context.container();
        int size = chest.getContainerSize();
        int freeSlotsCount = 0;
        Item air = Util.getItemByID("minecraft:air");
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
        Logger.error("Failed to find a free slot to spawn an item inside a chest");
        return -1;
    }
}
