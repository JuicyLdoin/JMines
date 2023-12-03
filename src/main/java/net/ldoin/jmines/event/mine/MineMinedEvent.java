package net.ldoin.jmines.event.mine;

import net.ldoin.jmines.mine.Mine;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MineMinedEvent extends MineEvent {

    public MineMinedEvent(Mine mine) {

        super(mine);

    }

    private static final HandlerList handlers = new HandlerList();

    @NotNull
    public HandlerList getHandlers() {

        return handlers;

    }
}