package net.ldoin.jmines.event.mine;

import net.ldoin.jmines.mine.Mine;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MineMinedEvent extends MineEvent {

    private static final HandlerList handlers = new HandlerList();

    public MineMinedEvent(Mine mine) {

        super(mine);

    }

    @NotNull
    public HandlerList getHandlers() {

        return handlers;

    }
}