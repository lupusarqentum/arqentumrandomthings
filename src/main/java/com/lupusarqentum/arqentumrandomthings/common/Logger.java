package com.lupusarqentum.arqentumrandomthings.common;

import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.NotNull;

public class Logger {
    private static final org.slf4j.Logger LOGGER = LogUtils.getLogger();
    private static final String default_separator = " ";

    private static @NotNull String constructMessageFromParts(String separator, Object @NotNull [] parts) {
        if (parts.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            sb.append(parts[i].toString());
            sb.append(separator);
        }
        sb.append(parts[parts.length - 1].toString());
        return sb.toString();
    }

    public static void infoWithSeparator(String separator, Object @NotNull ... mes) {
        LOGGER.info(constructMessageFromParts(separator, mes));
    }

    public static void info(Object @NotNull ... mes) {
        infoWithSeparator(default_separator, mes);
    }

    public static void errorWithSeparator(String separator, Object @NotNull ... mes) {
        LOGGER.error(constructMessageFromParts(separator, mes));
    }

    public static void error(Object @NotNull ... mes) {
        errorWithSeparator(default_separator, mes);
    }
}
