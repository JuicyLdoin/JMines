package net.ldoin.jmines.event.mine.fill;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.event.mine.MineEvent;
import net.ldoin.jmines.mine.Mine;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MineRegenerateBlockEvent extends MineEvent {

    private static final HandlerList handlers = new HandlerList();
    Block block;

    public MineRegenerateBlockEvent(Mine mine, Block block) {

        super(mine);

        this.block = block;

    }

    @NotNull
    public HandlerList getHandlers() {

        return handlers;

    }
}