package net.ldoin.jmines.mine.block.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.mine.block.IPlaceable;
import org.bukkit.Location;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MineBlock implements IPlaceable {

    net.ldoin.jmines.mine.block.MineBlock block;

    public MineBlock(net.ldoin.jmines.mine.block.MineBlock block) {

        this.block = block;

    }

    public boolean place(Location location) {

        block.place(location);
        return true;

    }
}