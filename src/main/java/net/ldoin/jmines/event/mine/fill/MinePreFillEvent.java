package net.ldoin.jmines.event.mine.fill;

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
public class MinePreFillEvent extends MineEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    boolean cancelled;

    public MinePreFillEvent(Mine mine) {

        super(mine);

        cancelled = false;

    }

    @NotNull
    public HandlerList getHandlers() {

        return handlers;

    }
}