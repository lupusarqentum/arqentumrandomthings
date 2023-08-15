package com.lupusarqentum.arqentumrandomthings.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class Util {

    public static Item getItemByID(String id) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
    }

    public static boolean isModLoaded(String target) {
        return ModList.get().isLoaded(target);
    }

    public static boolean isDivineRPGLoaded() {
        return isModLoaded("divinerpg");
    }
}
