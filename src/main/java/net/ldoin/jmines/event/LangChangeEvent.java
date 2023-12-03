package net.ldoin.jmines.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LangChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    final String from;
    String to;

    public LangChangeEvent(String from, String to) {

        this.from = from;
        this.to = to;

    }

    @NotNull
    public HandlerList getHandlers() {

        return handlers;

    }
}