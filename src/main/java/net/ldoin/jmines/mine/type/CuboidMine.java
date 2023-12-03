package net.ldoin.jmines.mine.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.event.mine.fill.MinePreFillEvent;
import net.ldoin.jmines.mine.MineOptions;
import net.ldoin.jmines.mine.MineReset;
import net.ldoin.jmines.mine.MineType;
import net.ldoin.jmines.mine.block.IPlaceable;
import net.ldoin.jmines.util.Cuboid;
import net.ldoin.jmines.util.vec.Vec3i;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CuboidMine extends MineReset {

    final Map<IPlaceable, Double> blocks;
    @Setter
    IPlaceable surface;

    public CuboidMine(String name, MineType type, String world) {

        super(name, type, world);

        blocks = new HashMap<>();

    }

    public CuboidMine(String name, MineType type, Cuboid cuboid, MineOptions mineOptions, Map<IPlaceable, Double> blocks, IPlaceable surface) {

        super(name, type, cuboid, mineOptions);

        this.blocks = blocks;
        this.surface = surface;

    }

    public void fill() {

        MinePreFillEvent event = new MinePreFillEvent(this);
        callEvent(event);

        if (event.isCancelled())
            return;

        int resetBlocks = 0;

        setResetBlocks(resetBlocks);

        World world = getWorld();
        Random random = new Random();

        for (Vec3i vec : getCuboid()) {

            Location location = vec.toLocation(world);

            if (surface != null && vec.y == getCuboid().getMax().y) {

                surface.place(location);
                resetBlocks++;

                continue;

            }

            double r = random.nextDouble();
            boolean placed = false;

            for (Map.Entry<IPlaceable, Double> map : blocks.entrySet())
                if (r <= map.getValue() && map.getKey().place(location))
                    placed = true;

            if (placed)
                resetBlocks++;

        }

        setResetBlocks(resetBlocks);

        super.fill();

    }
}