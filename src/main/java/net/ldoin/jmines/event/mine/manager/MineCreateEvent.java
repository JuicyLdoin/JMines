package net.ldoin.jmines.event.mine.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.event.mine.MineEvent;
import net.ldoin.jmines.mine.Mine;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MineCreateEvent extends MineEvent implements Cancellable {

    boolean cancelled;

    public MineCreateEvent(Mine mine) {

        super(mine);

        cancelled = false;

    }

    private static final HandlerList handlers = new HandlerList();

    @NotNull
    public HandlerList getHandlers() {

        return handlers;

    }
}