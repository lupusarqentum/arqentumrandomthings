package com.lupusarqentum.arqentumrandomthings;

import com.lupusarqentum.arqentumrandomthings.common.block.BlocksRegistration;
import com.lupusarqentum.arqentumrandomthings.common.item.ItemsRegistration;
import com.lupusarqentum.arqentumrandomthings.common.ModConfig;
import com.lupusarqentum.arqentumrandomthings.common.item.ImportantPaperFishingSpawner;
import com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn.EmeraldPickaxeSpawner;
import com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn.ImportantPaperSpawner;
import com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn.ItemInsideContainerSpawner;
import com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn.MathProbemStatementSpawner;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RandomThingsMod.MODID)
public class RandomThingsMod {
    public static final String MODID = "arqentumrandomthings";

    public RandomThingsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlocksRegistration.register(modEventBus);
        ItemsRegistration.register(modEventBus);

        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.SERVER_SPEC, "arqentumrandomthings.toml");

        ItemInsideContainerSpawner.instance.init(MinecraftForge.EVENT_BUS);
        ItemInsideContainerSpawner.instance.addItemToSpawn(new ImportantPaperSpawner());
        ItemInsideContainerSpawner.instance.addItemToSpawn(new MathProbemStatementSpawner());
        ItemInsideContainerSpawner.instance.addItemToSpawn(new EmeraldPickaxeSpawner());

        ImportantPaperFishingSpawner.init(MinecraftForge.EVENT_BUS);
    }
}
