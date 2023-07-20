package com.lupusarqentum.arqentumrandomthings.server;

import net.minecraftforge.common.MinecraftForge;

public class ServerSideFeaturesInitializer {

    public static void init() {
        ImportantPaperSpawner.init(MinecraftForge.EVENT_BUS);
    }
}
