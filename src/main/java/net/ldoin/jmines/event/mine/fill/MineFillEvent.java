package net.ldoin.jmines.event.mine.fill;

import net.ldoin.jmines.event.mine.MineEvent;
import net.ldoin.jmines.mine.Mine;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MineFillEvent extends MineEvent {

    public MineFillEvent(Mine mine) {

        super(mine);

    }

    private static final HandlerList handlers = new HandlerList();

    @NotNull
    public HandlerList getHandlers() {

        return handlers;

    }
}