package com.lupusarqentum.arqentumrandomthings.itemsregistration;

import com.lupusarqentum.arqentumrandomthings.RandomThingsMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ImportantPaperItem extends Item {
    public ImportantPaperItem(Properties p_41383_) {
        super(p_41383_);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        //tooltip.add(Component.translatable("item." + RandomThingsMod.MODID + ".important_paper"));
        tooltip.add(Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important"));
        tooltip.add(Component.translatable(RandomThingsMod.MODID + ".tooltip.paper.important2"));
    }

    //@Override
    //public Component getDescription() {
    //    return MutableComponent.create(new TranslatableContents("lore.important_paper", null, null));
    //}
}
