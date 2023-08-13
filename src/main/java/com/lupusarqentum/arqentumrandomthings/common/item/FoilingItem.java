package com.lupusarqentum.arqentumrandomthings.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FoilingItem extends Item {
    public FoilingItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }
}
