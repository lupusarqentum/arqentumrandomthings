package com.lupusarqentum.arqentumrandomthings;

import com.lupusarqentum.arqentumrandomthings.common.block.BlocksRegistration;
import com.lupusarqentum.arqentumrandomthings.common.itemsregistration.InventoryItemsRegistration;
import com.lupusarqentum.arqentumrandomthings.server.ServerConfig;
import com.lupusarqentum.arqentumrandomthings.server.ServerSideFeaturesInitializer;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RandomThingsMod.MODID)
public class RandomThingsMod {
    public static final String MODID = "arqentumrandomthings";

    public RandomThingsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlocksRegistration.register(modEventBus);
        InventoryItemsRegistration.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ServerConfig.SERVER_SPEC, "arqentumrandomthings.toml");

        ServerSideFeaturesInitializer.init();
    }
}
