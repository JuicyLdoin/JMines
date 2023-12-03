package net.ldoin.jmines.listener.listeners;

import net.ldoin.jmines.JMinesPlugin;
import net.ldoin.jmines.mine.Mine;
import net.ldoin.jmines.mine.MineOptions;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BlockBreakListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {

        Block block = event.getBlock();
        Player player = event.getPlayer();

        Mine mine = JMinesPlugin.getPlugin().getMineManager().getMine(block);

        if (mine == null)
            return;

        boolean cancelled = mine.blockBreak(block, player);
        event.setCancelled(cancelled);

        if (cancelled)
            return;

        MineOptions mineOptions = mine.getMineOptions();

        if (mineOptions.isGiveBlocks()) {

            event.setDropItems(false);

            List<ItemStack> drop = new ArrayList<>();

            if (player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH))
                drop.add(new ItemStack(block.getType()));
            else
                block.getDrops(player.getInventory().getItemInMainHand()).forEach(items -> drop.add(new ItemStack(items)));

            // TODO: full inventory notify
            drop.forEach(itemStack -> player.getInventory().addItem(itemStack));

        }

        if (!mineOptions.isDropExp())
            event.setExpToDrop(0);

    }
}