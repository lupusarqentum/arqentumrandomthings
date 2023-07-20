package com.lupusarqentum.arqentumrandomthings.common;

import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.NotNull;

public class Logger {
    private static final org.slf4j.Logger LOGGER = LogUtils.getLogger();
    private static final char default_separator = ' ';

    public static void info(@NotNull Object obj) {
        LOGGER.info(obj.toString());
    }

    public static void info(char sep, Object @NotNull ... obj) {
        if (obj.length == 0) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < obj.length - 1; i++) {
            sb.append(obj[i].toString());
            sb.append(sep);
        }
        sb.append(obj[obj.length - 1].toString());
        info(sb.toString());
    }

    public static void info(Object @NotNull ... obj) {
        info(default_separator, obj);
    }
}
