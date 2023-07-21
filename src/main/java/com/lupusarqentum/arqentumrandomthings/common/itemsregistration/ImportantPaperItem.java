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
        tooltip.add(Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important"));
        tooltip.add(Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important2"));
        if (stack.getTag() != null) {
            tooltip.add(Component.literal(stack.getTag().getString("player_received")));
            int[] arr = stack.getTag().getIntArray("receipt_date");
            tooltip.add(Component.literal(DateLocalizationHelper.localizeDate(arr[2], arr[1], arr[0])));
        }
    }
}
