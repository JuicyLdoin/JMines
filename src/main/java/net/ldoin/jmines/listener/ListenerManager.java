package net.ldoin.jmines.listener;

import net.ldoin.jmines.JMinesPlugin;
import net.ldoin.jmines.listener.listeners.BlockBreakListener;
import net.ldoin.jmines.listener.listeners.BlockPlaceListener;
import net.ldoin.jmines.listener.listeners.chat.ChatConsumerListener;
import net.ldoin.jmines.listener.listeners.inventory.InventoryBranchListener;
import net.ldoin.jmines.util.builder.listeners.InventoryBuildListener;
import net.ldoin.jmines.util.builder.listeners.ItemBuildListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {

    public ListenerManager(JMinesPlugin plugin) {

        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new BlockBreakListener(), plugin);
        pm.registerEvents(new BlockPlaceListener(), plugin);

        pm.registerEvents(new ChatConsumerListener(), plugin);

        pm.registerEvents(new InventoryBranchListener(), plugin);

        pm.registerEvents(new InventoryBuildListener(), plugin);
        pm.registerEvents(new ItemBuildListener(), plugin);

    }
}