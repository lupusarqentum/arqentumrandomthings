package com.lupusarqentum.arqentumrandomthings;

import com.lupusarqentum.arqentumrandomthings.itemsregistration.InventoryItemsRegistration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import java.util.Random;

@Mod(RandomThingsMod.MODID)
public class RandomThingsMod {
    public static final String MODID = "arqentumrandomthings";

    public RandomThingsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        InventoryItemsRegistration.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(this::onChestOpened);
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    public void onChestOpened(PlayerContainerEvent.Open event) {
        if (event.getEntity().level.isClientSide) {
            return;
        }
        if (!(event.getContainer() instanceof ChestMenu)) {
            return;
        }
        if (!(new Random().nextFloat() < 0.02)) {
            return;
        }

        Item air = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:air"));
        Item impp = InventoryItemsRegistration.IMPORTANT_PAPER.get();
        ChestMenu chest = (ChestMenu) (event.getContainer());
        int size = chest.getContainer().getContainerSize();
        for (int i = 0; i < size; i++) {
            if (chest.getContainer().getItem(i).is(air)) {
                chest.getContainer().setItem(i, new ItemStack(impp));
                break;
            }
        }
    }
}
