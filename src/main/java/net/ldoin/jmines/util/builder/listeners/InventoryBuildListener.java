package net.ldoin.jmines.util.builder.listeners;

import net.ldoin.jmines.util.builder.InventoryBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Map;
import java.util.function.Consumer;

public class InventoryBuildListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        InventoryBuilder inventoryBuilder = InventoryBuilder.getBuildedInventories().get(event.getInventory());

        if (inventoryBuilder != null) {

            event.setCancelled(inventoryBuilder.isCancelClick());

            for (Consumer<InventoryClickEvent> consumer : inventoryBuilder.getClickEventConsumers())
                consumer.accept(event);

            for (Map.Entry<Integer, Consumer<InventoryClickEvent>> consumers : inventoryBuilder.getClickInSlotEventConsumers().entrySet())
                if (consumers.getKey() == event.getSlot()) {

                    consumers.getValue().accept(event);
                    break;

                }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        InventoryBuilder remove = null;
        InventoryBuilder inventoryBuilder = InventoryBuilder.getBuildedInventories().get(event.getInventory());

        if (inventoryBuilder != null) {

            if (inventoryBuilder.isUseCacheOnce())
                remove = inventoryBuilder;

            for (Consumer<InventoryCloseEvent> closeEventConsumer : inventoryBuilder.getCloseEventConsumers())
                closeEventConsumer.accept(event);

        }

        if (remove != null)
            InventoryBuilder.getBuildedInventories().remove(remove.getInventory());

    }
}