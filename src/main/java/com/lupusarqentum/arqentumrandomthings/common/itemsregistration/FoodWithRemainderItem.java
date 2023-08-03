package com.lupusarqentum.arqentumrandomthings.common.itemsregistration;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public final class FoodWithRemainderItem extends Item {

    private Item remainder;

    private FoodWithRemainderItem(Item.Properties ip) {
        super(ip);
    }

    public FoodWithRemainderItem(Item.@NotNull Properties ip, Item remainder) {
        this(ip.craftRemainder(remainder).stacksTo(1));
        this.remainder = remainder;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player && ((Player)livingEntity).getAbilities().instabuild) {
            return super.finishUsingItem(itemStack, level, livingEntity);
        }
        super.finishUsingItem(itemStack, level, livingEntity);
        return new ItemStack(this.remainder);
    }
}
