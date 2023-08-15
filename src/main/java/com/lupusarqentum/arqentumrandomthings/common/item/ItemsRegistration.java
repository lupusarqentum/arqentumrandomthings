package com.lupusarqentum.arqentumrandomthings.common.item;

import com.lupusarqentum.arqentumrandomthings.RandomThingsMod;

import com.lupusarqentum.arqentumrandomthings.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ItemsRegistration {
    public static class ItemsIDs {
        public static final String IMPORTANT_PAPER = "important_paper";
        public static final String PIZZA_DOUGH = "pizza_dough";
        public static final String VEGETABLE_MIX = "vegetable_mix";
        public static final String MEAT_MIX = "meat_mix";
        public static final String CHEESE = "cheese";
        public static final String RAW_PIZZA = "raw_pizza";
        public static final String PIZZA = "pizza";
        public static final String CHEESE_BUCKET = "cheese_bucket";
        public static final String MATH_PROBLEM = "math_problem";
        public static final String EMERALD_PICKAXE = "emerald_pickaxe";

    }

    public static final Item BUCKET = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:bucket"));
    public static final Item BOWL = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:bowl"));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RandomThingsMod.MODID);

    public static final RegistryObject<Item> IMPORTANT_PAPER = ITEMS.register(ItemsIDs.IMPORTANT_PAPER, () -> new ImportantPaperItem(
            new Item.Properties().fireResistant().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> PIZZA_DOUGH = ITEMS.register(ItemsIDs.PIZZA_DOUGH, () -> new Item(
            new Item.Properties().food(Foods.PIZZA_DOUGH)));
    public static final RegistryObject<Item> VEGETABLE_MIX = ITEMS.register(ItemsIDs.VEGETABLE_MIX, () -> new FoodWithRemainderItem(
            new Item.Properties().food(Foods.VEGETABLE_MIX), BOWL));
    public static final RegistryObject<Item> MEAT_MIX = ITEMS.register(ItemsIDs.MEAT_MIX, () -> new FoodWithRemainderItem(
            new Item.Properties().food(Foods.MEAT_MIX), BOWL));
    public static final RegistryObject<Item> CHEESE = ITEMS.register(ItemsIDs.CHEESE, () -> new Item(
            new Item.Properties().food(Foods.CHEESE)));
    public static final RegistryObject<Item> RAW_PIZZA = ITEMS.register(ItemsIDs.RAW_PIZZA, () -> new Item(
            new Item.Properties().food(Foods.RAW_PIZZA)));
    public static final RegistryObject<Item> PIZZA = ITEMS.register(ItemsIDs.PIZZA, PizzaItem::new);
    public static final RegistryObject<Item> CHEESE_BUCKET = ITEMS.register(ItemsIDs.CHEESE_BUCKET, () -> new FoodWithRemainderItem(
            new Item.Properties().food(Foods.CHEESE_BUCKET), BUCKET));
    public static final RegistryObject<Item> MATH_PROBLEM = ITEMS.register(ItemsIDs.MATH_PROBLEM, () -> new FoilingItem(
            new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> EMERALD_PICKAXE = ITEMS.register(ItemsIDs.EMERALD_PICKAXE, () ->
            new PickaxeItem(ToolTiers.EMERALD, 1, -2.8f, new Item.Properties()) {
                @Override
                @OnlyIn(Dist.CLIENT)
                public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
                    String lore1 = Component.translatable(RandomThingsMod.MODID + ".emerald_pickaxe.lore").getString();
                    String lore2 = Util.isDivineRPGLoaded() ?
                            Component.translatable(RandomThingsMod.MODID + ".emerald_pickaxe.loredrpg").getString() : "";
                    Component text = Component.literal(lore1 + lore2);
                    tooltip.add(text);
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }});

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        modEventBus.addListener(ItemsRegistration::addToCreativeTab);
    }

    private static void addToCreativeTab(CreativeModeTabEvent.@NotNull BuildContents event) {
        if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(PIZZA_DOUGH);
            event.accept(VEGETABLE_MIX);
            event.accept(MEAT_MIX);
            event.accept(CHEESE_BUCKET);
            event.accept(CHEESE);
            event.accept(RAW_PIZZA);
            event.accept(((PizzaItem)(PIZZA.get())).getItemStackForCreativeTab());
        } else if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(EMERALD_PICKAXE);
        }
    }
}
