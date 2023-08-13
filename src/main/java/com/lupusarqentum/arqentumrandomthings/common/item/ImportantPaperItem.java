package com.lupusarqentum.arqentumrandomthings.common.item;

import com.lupusarqentum.arqentumrandomthings.RandomThingsMod;
import com.lupusarqentum.arqentumrandomthings.client.DateLocalizationHelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import javax.annotation.Nullable;

public class ImportantPaperItem extends Item {
    public ImportantPaperItem(Properties p_41383_) { super(p_41383_); }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, TooltipFlag flagIn) {
        CompoundTag nbt = stack.getTag();
        if (nbt == null) {
            return;
        }
        if (!nbt.contains("player_received")) {
            return;
        }
        if (!nbt.contains("paper_created_date")) {
            return;
        }

        long initialDate = nbt.getLong("paper_created_date");
        int[] ia_receiptDate = calculateReceiptDate(initialDate);
        int[] ia_appearanceDate = calculateAppearanceDate(initialDate);
        String receiptDate = DateLocalizationHelper.localizeDate(ia_receiptDate[2], ia_receiptDate[1], ia_receiptDate[0]);
        String appearanceDate = DateLocalizationHelper.localizeDate(ia_appearanceDate[2], ia_appearanceDate[1], ia_appearanceDate[0]);
        String playerName = nbt.getString("player_received");

        String part1 = Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important").getString();
        tooltip.add(Component.literal(String.format(part1, appearanceDate)));
        tooltip.add(Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important2"));
        String toWhom = Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important3").getString();
        tooltip.add(Component.literal(String.format(toWhom, playerName)));
        String receiptDateTooltip = Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important4").getString();
        tooltip.add(Component.literal(String.format(receiptDateTooltip, receiptDate)));
    }

    private int[] calculateReceiptDate(long initialDate) {
        LocalDateTime datetime = LocalDateTime.ofEpochSecond(initialDate, 0, ZoneOffset.UTC);
        LocalDateTime receiptDatetime = datetime.minusDays(19);
        return new int[] {receiptDatetime.getYear(), receiptDatetime.getMonthValue(), receiptDatetime.getDayOfMonth()};
    }

    private int @NotNull [] calculateAppearanceDate(long initialDate) {
        LocalDateTime datetime = LocalDateTime.ofEpochSecond(initialDate, 0, ZoneOffset.UTC);
        LocalDateTime appearanceDatetime = datetime.plusDays(1);
        return new int[] {appearanceDatetime.getYear(), appearanceDatetime.getMonthValue(), appearanceDatetime.getDayOfMonth()};
    }
}
