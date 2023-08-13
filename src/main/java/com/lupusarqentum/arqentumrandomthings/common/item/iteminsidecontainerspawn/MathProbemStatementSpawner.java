package com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn;

import com.lupusarqentum.arqentumrandomthings.RandomThingsMod;
import com.lupusarqentum.arqentumrandomthings.common.config.ModConfig;
import com.lupusarqentum.arqentumrandomthings.common.config.ProblemSetConfig;
import com.lupusarqentum.arqentumrandomthings.common.item.ItemsRegistration;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.throwables.SyntheticBridgeException;

public class MathProbemStatementSpawner extends ItemInsideContainerSpawningParams {
    @Override
    public double getProbability(ItemInsideContainerSpawningContext context) {
        if (ProblemSetConfig.hasProblem()) {
            return ModConfig.problemStatementSpawningProbability.get();
        }
        return 0.0;
    }

    @Override
    public ItemStack getItemStack(ItemInsideContainerSpawningContext context) {
        ItemStack itemStack = new ItemStack(ItemsRegistration.MATH_PROBLEM.get());
        itemStack.setTag(getNbt());
        return itemStack;
    }

    private @NotNull CompoundTag getNbt() {
        CompoundTag nbt = new CompoundTag();
        CompoundTag displayTag = new CompoundTag();
        ListTag loreTag = new ListTag();
        StringTag nameTag = StringTag.valueOf(getNameComponentAsString());
        StringTag stringTag = StringTag.valueOf(getLoreComponentAsString());
        loreTag.add(0, stringTag);
        displayTag.put("Lore", loreTag);
        displayTag.put("Name", nameTag);
        nbt.put("display", displayTag);
        return nbt;
    }

    private @NotNull String getNameComponentAsString() {
        String nameLocalizationKey = "item." + RandomThingsMod.MODID + "." + ItemsRegistration.ItemsIDs.MATH_PROBLEM;
        return "{\"translate\":\"" + nameLocalizationKey + "\",\"color\":\"dark_purple\",\"italic\":false}";
    }

    private @NotNull String getLoreComponentAsString() {
        return "{\"text\":\"" + getStatement() + "\",\"color\":\"white\",\"italic\":false}";
    }

    private @NotNull String getStatement() {
        return ProblemSetConfig.getProblemStatement();
    }
}
