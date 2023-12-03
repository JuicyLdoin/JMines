package net.ldoin.jmines.mine.block.type;

import lombok.EqualsAndHashCode;
import lombok.Value;
import net.ldoin.jmines.mine.block.MineBlock;
import org.bukkit.Location;

@Value
@EqualsAndHashCode(callSuper = true)
public class OverlayBlock extends net.ldoin.jmines.mine.block.type.MineBlock {

    MineBlock ground;

    public OverlayBlock(MineBlock block, MineBlock ground) {

        super(block);

        this.ground = ground;

    }

    public boolean place(Location location) {

        if (ground.isOn(location.clone().subtract(0, 1, 0)))
            return super.place(location);

        return false;

    }
}