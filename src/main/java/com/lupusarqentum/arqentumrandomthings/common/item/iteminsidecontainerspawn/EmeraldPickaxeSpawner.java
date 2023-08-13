package com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn;

import com.lupusarqentum.arqentumrandomthings.common.ModConfig;
import com.lupusarqentum.arqentumrandomthings.common.Util;

import net.minecraft.world.item.ItemStack;

public class EmeraldPickaxeSpawner extends ItemInsideContainerSpawningParams {
    @Override
    public double getProbability(ItemInsideContainerSpawningContext context) {
        return ModConfig.emeraldPickaxeSpawningProbability.get();
    }

    @Override
    public ItemStack getItemStack(ItemInsideContainerSpawningContext context) {
        return new ItemStack(Util.getItemByID("diamond_pickaxe"));
    }
}
