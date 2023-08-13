package com.lupusarqentum.arqentumrandomthings.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class Foods {
    public static final FoodProperties PIZZA_DOUGH = new FoodProperties.Builder().
            nutrition(1).saturationMod(0.5f).effect(() -> new MobEffectInstance(MobEffects.POISON, 300), 0.45f).build();
    public static final FoodProperties VEGETABLE_MIX = new FoodProperties.Builder().
            nutrition(4).saturationMod(0.125f).build();
    public static final FoodProperties MEAT_MIX = new FoodProperties.Builder().
            nutrition(7).saturationMod(0.15f).meat().build();
    public static final FoodProperties CHEESE = new FoodProperties.Builder().
            nutrition(3).saturationMod(0.1f).build();
    public static final FoodProperties RAW_PIZZA = new FoodProperties.Builder().
            nutrition(2).saturationMod(0.5f).meat().effect(() -> new MobEffectInstance(MobEffects.POISON, 300), 0.35f).build();
    public static final FoodProperties PIZZA = new FoodProperties.Builder()
            .nutrition(20).saturationMod(0.5f).meat().build();
    public static final FoodProperties CHEESE_BUCKET = new FoodProperties.Builder().
            nutrition(3).saturationMod(0.1f).build();
    public static final FoodProperties CHEESE_BLOCK = new FoodProperties.Builder().
            nutrition(3).saturationMod(0.1f).build();
}
