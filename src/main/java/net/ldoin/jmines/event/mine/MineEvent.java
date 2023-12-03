package net.ldoin.jmines.event.mine;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.mine.Mine;
import org.bukkit.event.Event;

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class MineEvent extends Event {

    Mine mine;

}