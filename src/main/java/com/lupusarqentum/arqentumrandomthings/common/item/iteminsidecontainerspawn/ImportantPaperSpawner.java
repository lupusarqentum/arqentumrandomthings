package com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn;

import com.lupusarqentum.arqentumrandomthings.common.ModConfig;
import com.lupusarqentum.arqentumrandomthings.common.Util;
import com.lupusarqentum.arqentumrandomthings.common.item.ItemsRegistration;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.time.Instant;

public class ImportantPaperSpawner extends ItemInsideContainerSpawningParams {

    @Override
    public double getProbability(ItemInsideContainerSpawningContext context) {
        return ModConfig.importantPaperChestSpawningProbability.get();
    }

    @Override
    public ItemStack getItemStack(ItemInsideContainerSpawningContext context) {
        ItemStack itemStack = new ItemStack(ItemsRegistration.IMPORTANT_PAPER.get());
        long creating_date = Instant.now().getEpochSecond();
        CompoundTag nbt = new CompoundTag();
        nbt.putString("player_received", context.player().getDisplayName().getString());
        nbt.putLong("paper_created_date", creating_date);
        itemStack.setTag(nbt);
        return itemStack;
    }
}
