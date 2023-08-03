package com.lupusarqentum.arqentumrandomthings.server;

import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

public class ServerConfig {

    public static final ForgeConfigSpec SERVER_SPEC;

    public static ForgeConfigSpec.DoubleValue importantPaperChestSpawningProbability;
    public static ForgeConfigSpec.DoubleValue importantPaperFishingSpawningProbability;

    public static ForgeConfigSpec.BooleanValue doesPizzaRemoveHunger;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        SERVER_SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.@NotNull Builder builder) {
        importantPaperChestSpawningProbability =
                builder.defineInRange("important_paper_chest_spawning_chance", 0.003, 0, 1);
        importantPaperFishingSpawningProbability =
                builder.defineInRange("important_paper_fishing_spawning_chance", 0.02, 0, 1);
        doesPizzaRemoveHunger = builder.comment("Whether or not should pizza remove hunger effect on eating")
                .define("pizza_removes_hunger", true);
    }
}
