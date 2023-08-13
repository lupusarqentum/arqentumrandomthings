package com.lupusarqentum.arqentumrandomthings.common.item;

import com.lupusarqentum.arqentumrandomthings.RandomThingsMod;
import com.lupusarqentum.arqentumrandomthings.common.config.ModConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class PizzaItem extends Item {

    private static final int INITIAL_SLICES_COUNT = 16;

    public PizzaItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).food(Foods.PIZZA));
    }

    public ItemStack getItemStackForCreativeTab() {
        ItemStack creativeTabPizza = new ItemStack(ItemsRegistration.PIZZA.get());
        setSlicesCount(creativeTabPizza, INITIAL_SLICES_COUNT);
        return creativeTabPizza;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level worldIn, @NotNull List<Component> tooltip, TooltipFlag flagIn) {
        String tooltip0PatternPath = RandomThingsMod.MODID +  ".tooltip.pizza0";
        String tooltipPatternPath = RandomThingsMod.MODID + ".tooltip.pizza";
        String tooltipPattern = Component.translatable(tooltipPatternPath).getString();
        int slices_left = getSlicesCount(itemStack);
        String formattedString = String.format(tooltipPattern, "" + slices_left);
        tooltip.add(Component.translatable(tooltip0PatternPath));
        tooltip.add(Component.literal(formattedString));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        int slices_count = getSlicesCount(itemStack);
        super.finishUsingItem(itemStack, level, livingEntity);
        if (!level.isClientSide && ModConfig.doesPizzaRemoveHunger.get()) {
            livingEntity.removeEffect(MobEffects.HUNGER);
        }
        int slices_left = slices_count - 1;
        if (slices_left == 0
                || (livingEntity instanceof Player && ((Player)livingEntity).getAbilities().instabuild)) {
            return itemStack;
        }
        ItemStack newItemStack = new ItemStack(ItemsRegistration.PIZZA.get());
        setSlicesCount(newItemStack, slices_left);
        return newItemStack;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 5;
    }

    private void setSlicesCount(@NotNull ItemStack itemStack, int slices_count) {
        if (itemStack.getTag() == null) {
            itemStack.setTag(new CompoundTag());
        }
        CompoundTag nbt = itemStack.getTag();
        nbt.putInt("slices_left", slices_count);
        nbt.putInt("CustomModelData", slices_count);
    }

    private int getSlicesCount(@NotNull ItemStack itemStack) {
        if (itemStack.getTag() == null) {
            CompoundTag nbt = new CompoundTag();
            itemStack.setTag(nbt);
        }
        int slices_left = itemStack.getTag().getInt("slices_left");
        if (slices_left < 1 || slices_left > INITIAL_SLICES_COUNT) {
            setSlicesCount(itemStack, INITIAL_SLICES_COUNT);
        }
        return itemStack.getTag().getInt("slices_left");
    }
}
