package com.lupusarqentum.arqentumrandomthings.common.itemsregistration;

import com.lupusarqentum.arqentumrandomthings.RandomThingsMod;

import com.lupusarqentum.arqentumrandomthings.client.DateLocalizationHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ImportantPaperItem extends Item {
    public ImportantPaperItem(Properties p_41383_) {
        super(p_41383_);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, TooltipFlag flagIn) {
        if (stack.getTag() == null) {
            return;
        }
        if (stack.getTag().getString("player_received") == null) {
            return;
        }
        if (stack.getTag().getIntArray("receipt_date") == null) {
            return;
        }
        if (stack.getTag().getIntArray("receipt_date").length != 3) {
            return;
        }

        int[] initialDate = stack.getTag().getIntArray("receipt_date");
        int[] ia_receiptDate = calculateReceiptDate(initialDate);
        int[] ia_appearanceDate = calculateAppearanceDate(initialDate);
        String receiptDate = DateLocalizationHelper.localizeDate(ia_receiptDate[2], ia_receiptDate[1], ia_receiptDate[0]);
        String appearanceDate = DateLocalizationHelper.localizeDate(ia_appearanceDate[2], ia_appearanceDate[1], ia_appearanceDate[0]);
        String playerName = stack.getTag().getString("player_received");

        String part1 = Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important").getString();
        tooltip.add(Component.literal(String.format(part1, appearanceDate)));
        tooltip.add(Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important2"));
        String toWhom = Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important3").getString();
        tooltip.add(Component.literal(String.format(toWhom, playerName)));
        String receiptDateTooltip = Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important4").getString();
        tooltip.add(Component.literal(String.format(receiptDateTooltip, receiptDate)));
    }

    private int[] calculateReceiptDate(int @NotNull [] initialDate) {
        int day = initialDate[2];
        int month = initialDate[1];
        int year = initialDate[0];
        day -= 20;
        if (day <= 0) {
            day = 28 - day;
            month--;
            if (month == 0) {
                month = 12;
                year--;
            }
        }
        return new int[] {year, month, day};
    }

    private int @NotNull [] calculateAppearanceDate(int @NotNull [] initialDate) {
        int day = initialDate[2];
        int month = initialDate[1];
        int year = initialDate[0];
        day++;
        if (day >= 31 || (day >= 29 && month == 2)) {
            day = 1;
            month++;
            if (month == 13) {
                month = 1;
                year++;
            }
        }
        return new int[] {year, month, day};
    }
}
