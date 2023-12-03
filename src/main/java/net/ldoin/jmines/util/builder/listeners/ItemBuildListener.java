package net.ldoin.jmines.util.builder.listeners;

import net.ldoin.jmines.util.builder.item.ItemBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.function.Consumer;

public class ItemBuildListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getItem() == null)
            return;

        ItemBuilder itemBuilder = ItemBuilder.getBuildedItem(event.getItem());

        if (itemBuilder != null)
            for (Consumer<PlayerInteractEvent> consumer : itemBuilder.getInteractEventConsumers())
                consumer.accept(event);

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getCurrentItem() == null)
            return;

        ItemBuilder itemBuilder = ItemBuilder.getBuildedItem(event.getCurrentItem());

        if (itemBuilder != null)
            for (Consumer<InventoryClickEvent> consumer : itemBuilder.getInventoryClickEventConsumers())
                consumer.accept(event);

    }
}