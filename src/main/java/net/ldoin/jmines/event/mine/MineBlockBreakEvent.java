package net.ldoin.jmines.event.mine;

import net.ldoin.jmines.mine.Mine;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class MineBlockBreakEvent extends BlockBreakEvent {

    final Mine mine;

    public MineBlockBreakEvent(@NotNull Block theBlock, @NotNull Player player, Mine mine) {

        super(theBlock, player);

        this.mine = mine;

    }

    private static final HandlerList handlers = new HandlerList();

    @NotNull
    public HandlerList getHandlers() {

        return handlers;

    }
}