package com.lupusarqentum.arqentumrandomthings.client;

import com.lupusarqentum.arqentumrandomthings.RandomThingsMod;
import net.minecraft.network.chat.Component;

public class DateLocalizationHelper {
    public static String localizeDate(int day, int month, int year) {
        String date_pattern = Component.translatable(RandomThingsMod.MODID + ".date_support.date").getString();
        String month_name = Component.translatable(RandomThingsMod.MODID + ".date_support.month" + month).getString();
        return String.format(date_pattern, "" + day, month_name, "" + year);
    }
}
