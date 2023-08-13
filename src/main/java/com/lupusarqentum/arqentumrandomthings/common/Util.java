package com.lupusarqentum.arqentumrandomthings.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class Util {

    public static Item getItemByID(String id) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
    }
}
