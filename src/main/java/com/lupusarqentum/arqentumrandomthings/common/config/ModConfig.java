package com.lupusarqentum.arqentumrandomthings.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

public class ModConfig {

    public static final ForgeConfigSpec SERVER_SPEC;

    public static ForgeConfigSpec.DoubleValue importantPaperChestSpawningProbability;
    public static ForgeConfigSpec.DoubleValue problemStatementSpawningProbability;
    public static ForgeConfigSpec.DoubleValue emeraldPickaxeSpawningProbability;

    public static ForgeConfigSpec.BooleanValue canMultipleItemsSpawnInsideContainerAtOnce;

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
        problemStatementSpawningProbability =
                builder.defineInRange("math_problem_statement_chest_spawning_chance", 0.003, 0, 1);
        emeraldPickaxeSpawningProbability =
                builder.defineInRange("emerald_pickaxe_chest_spawning_chance", 0.003, 0, 1);

        canMultipleItemsSpawnInsideContainerAtOnce = builder.comment("If true, important paper and problem statement " +
                        "can spawn at the same time, otherwise only one item can be spawned")
                .define("can_multiple_items_spawn_inside_chest_at_once", false);

        importantPaperFishingSpawningProbability =
                builder.defineInRange("important_paper_fishing_spawning_chance", 0.02, 0, 1);

        doesPizzaRemoveHunger = builder.comment("Whether or not should pizza remove hunger effect on eating")
                .define("pizza_removes_hunger", true);
    }
}
