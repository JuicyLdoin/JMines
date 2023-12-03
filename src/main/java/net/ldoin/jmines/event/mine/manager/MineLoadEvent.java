package net.ldoin.jmines.event.mine.manager;

import net.ldoin.jmines.event.mine.MineEvent;
import net.ldoin.jmines.mine.Mine;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MineLoadEvent extends MineEvent {

    public MineLoadEvent(Mine mine) {

        super(mine);

    }

    private static final HandlerList handlers = new HandlerList();

    @NotNull
    public HandlerList getHandlers() {

        return handlers;

    }
}