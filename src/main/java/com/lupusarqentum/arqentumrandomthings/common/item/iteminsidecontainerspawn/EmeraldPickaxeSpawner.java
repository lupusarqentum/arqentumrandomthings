package com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn;

import com.lupusarqentum.arqentumrandomthings.Constant;
import com.lupusarqentum.arqentumrandomthings.common.config.ModConfig;
import com.lupusarqentum.arqentumrandomthings.common.item.ItemsRegistration;

import net.minecraft.world.item.ItemStack;

public class EmeraldPickaxeSpawner extends ItemInsideContainerSpawningParams {
    @Override
    public double getProbability(ItemInsideContainerSpawningContext context) {
        if (context.player().position().y < Constant.UNDERGROUND_LEVEL) {
            return ModConfig.emeraldPickaxeSpawningProbability.get();
        }
        return 0.0;
    }

    @Override
    public ItemStack getItemStack(ItemInsideContainerSpawningContext context) {
        return new ItemStack(ItemsRegistration.EMERALD_PICKAXE.get());
    }
}
