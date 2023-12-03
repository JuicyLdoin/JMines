package net.ldoin.jmines.event.mine.manager;

import net.ldoin.jmines.event.mine.MineEvent;
import net.ldoin.jmines.mine.Mine;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MineLoadEvent extends MineEvent {

    private static final HandlerList handlers = new HandlerList();

    public MineLoadEvent(Mine mine) {

        super(mine);

    }

    @NotNull
    public HandlerList getHandlers() {

        return handlers;

    }
}