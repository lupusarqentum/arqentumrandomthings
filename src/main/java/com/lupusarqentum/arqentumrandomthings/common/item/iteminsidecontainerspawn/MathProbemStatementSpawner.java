package com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn;

import com.lupusarqentum.arqentumrandomthings.common.ModConfig;
import com.lupusarqentum.arqentumrandomthings.common.Util;

import net.minecraft.world.item.ItemStack;

public class MathProbemStatementSpawner extends ItemInsideContainerSpawningParams {
    @Override
    public double getProbability(ItemInsideContainerSpawningContext context) {
        return ModConfig.problemStatementSpawningProbability.get();
    }

    @Override
    public ItemStack getItemStack(ItemInsideContainerSpawningContext context) {
        return new ItemStack(Util.getItemByID("paper"));
    }
}
