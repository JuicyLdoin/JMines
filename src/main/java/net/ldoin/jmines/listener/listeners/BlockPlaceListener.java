package net.ldoin.jmines.listener.listeners;

import net.ldoin.jmines.JMinesPlugin;
import net.ldoin.jmines.mine.Mine;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {

        Block block = event.getBlock();
        Mine mine = JMinesPlugin.getPlugin().getMineManager().getMine(block);

        if (mine == null)
            return;

        mine.getMineOptions().getPlacedBlocks().add(block);

    }
}