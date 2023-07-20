package com.lupusarqentum.arqentumrandomthings;

import com.lupusarqentum.arqentumrandomthings.common.itemsregistration.InventoryItemsRegistration;
import com.lupusarqentum.arqentumrandomthings.server.ServerSideFeaturesInitializer;

import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RandomThingsMod.MODID)
public class RandomThingsMod {
    public static final String MODID = "arqentumrandomthings";

    public RandomThingsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        InventoryItemsRegistration.register(modEventBus);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // logical server code only
        ServerSideFeaturesInitializer.init();
    }
}
