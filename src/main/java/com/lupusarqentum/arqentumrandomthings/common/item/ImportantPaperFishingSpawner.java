package com.lupusarqentum.arqentumrandomthings.common.item;

import com.lupusarqentum.arqentumrandomthings.common.config.ModConfig;
import com.lupusarqentum.arqentumrandomthings.common.Random;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

public class ImportantPaperFishingSpawner {

    private Map<String, Long> lastTimePLayerFishedUpItem;

    public static void init(@NotNull IEventBus eventBus) {
        ImportantPaperFishingSpawner self = new ImportantPaperFishingSpawner();
        self.lastTimePLayerFishedUpItem = new HashMap<>();
        eventBus.addListener(self::onItemFished);
        eventBus.addListener(self::onItemPickedUp);
    }

    private @NotNull Item getImportantPaperItem() {
        return ItemsRegistration.IMPORTANT_PAPER.get();
    }

    private void onItemPickedUp(PlayerEvent.@NotNull ItemPickupEvent event) {
        if (event.getEntity().level.isClientSide) {
            return;
        }
        Player player = event.getEntity();
        String playerName = player.getDisplayName().getString();
        if (lastTimePLayerFishedUpItem.containsKey(playerName)) {
            Long currentTimeSinceEpoch = Instant.now().getEpochSecond();
            Long itemFishedTimeSinceEpoch = lastTimePLayerFishedUpItem.get(playerName);
            if (currentTimeSinceEpoch - itemFishedTimeSinceEpoch < 4) {
                if(!player.getInventory().add(getImportantPaperItemStack(player))) {
                    // drop important paper in the world
                    Level world = player.level;
                    Vec3 playerPos = player.position();
                    ItemStack itemStack = getImportantPaperItemStack(player);
                    ItemEntity droppedItem = new ItemEntity(world, playerPos.x, playerPos.y + 1, playerPos.z, itemStack);
                    world.addFreshEntity(droppedItem);
                }
            }
            lastTimePLayerFishedUpItem.remove(playerName);
        }
    }

    private void onItemFished(@NotNull ItemFishedEvent event) {
        if (!event.getEntity().level.isClientSide && Random.rollProbability(getFishingSpawnProbability())) {
            Player player = event.getEntity();
            String playerName = player.getDisplayName().getString();
            Long timeSinceEpoch = Instant.now().getEpochSecond();
            lastTimePLayerFishedUpItem.put(playerName, timeSinceEpoch);
        }
    }

    private @NotNull ItemStack getImportantPaperItemStack(@NotNull Player entity) {
        ItemStack itemStack = new ItemStack(getImportantPaperItem());
        long creating_date = Instant.now().getEpochSecond();
        CompoundTag nbt = new CompoundTag();
        nbt.putString("player_received", entity.getDisplayName().getString());
        nbt.putLong("paper_created_date", creating_date);
        itemStack.setTag(nbt);
        return itemStack;
    }

    private double getFishingSpawnProbability() {

        return ModConfig.importantPaperFishingSpawningProbability.get();
    }
}
