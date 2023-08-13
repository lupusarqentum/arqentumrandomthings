package com.lupusarqentum.arqentumrandomthings.common.item.iteminsidecontainerspawn;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;

public record ItemInsideContainerSpawningContext(Level level, Player player, Container container) {
    public ItemInsideContainerSpawningContext(PlayerContainerEvent.@NotNull Open event) {
        this(Objects.requireNonNull(event.getEntity().level),
             Objects.requireNonNull(event.getEntity()),
             Objects.requireNonNull(getContainerFromEvent(event)));
    }

    private static @Nullable Container getContainerFromEvent(@NotNull PlayerContainerEvent event) {
        if (event.getContainer() instanceof ChestMenu) {
            return ((ChestMenu)(event.getContainer())).getContainer();
        }
        if (event.getContainer() != null) {
            Method getContainerMethod;
            try {
                getContainerMethod = event.getContainer().getClass().getMethod("getContainer", new Class[0]);
            } catch (Exception e) {
                return null;
            }
            Object value;
            try {
                value = getContainerMethod.invoke(event.getContainer(), new Object[0]);
            } catch (Exception e) {
                return null;
            }
            if (value instanceof Container) {
                return (Container)value;
            }
        }
        return null;
    }
}
