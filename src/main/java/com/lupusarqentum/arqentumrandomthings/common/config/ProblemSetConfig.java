package com.lupusarqentum.arqentumrandomthings.common.config;

import com.lupusarqentum.arqentumrandomthings.common.Random;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class ProblemSetConfig {

    public static final ForgeConfigSpec SPEC;

    private static ForgeConfigSpec.ConfigValue<List<? extends String>> problems;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.@NotNull Builder builder) {
        problems = builder.comment("list of problem statements, e.g. [\"2+2=?\", " +
                        "\"there were two goats. how many?\"]")
                   .defineList("problems", Collections.emptyList(), o -> o instanceof String);
    }

    public static String getProblemStatement() {
        int size = problems.get().size();
        if (size == 0) {
            return "";
        }
        int index = Random.nextInt(0, size - 1);
        return problems.get().get(index);
    }

    public static boolean hasProblem() {
        int size = problems.get().size();
        return size > 0;
    }

}
