package net.ldoin.jmines.event.mine.fill;

import net.ldoin.jmines.event.mine.MineEvent;
import net.ldoin.jmines.mine.Mine;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MineFillEvent extends MineEvent {

    private static final HandlerList handlers = new HandlerList();

    public MineFillEvent(Mine mine) {

        super(mine);

    }

    @NotNull
    public HandlerList getHandlers() {

        return handlers;

    }
}