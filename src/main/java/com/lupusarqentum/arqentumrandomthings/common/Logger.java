package com.lupusarqentum.arqentumrandomthings.common;

import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.NotNull;

public class Logger {
    private static final org.slf4j.Logger LOGGER = LogUtils.getLogger();
    private static final String default_separator = " ";

    public static void infoSeparated(String separator, Object @NotNull ... mes) {
        if (mes.length == 0) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mes.length - 1; i++) {
            sb.append(mes[i].toString());
            sb.append(separator);
        }
        sb.append(mes[mes.length - 1].toString());
        LOGGER.info(sb.toString());
    }

    public static void info(Object @NotNull ... mes) {
        infoSeparated(default_separator, mes);
    }
}
