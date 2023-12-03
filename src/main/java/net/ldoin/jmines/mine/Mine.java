package net.ldoin.jmines.mine;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.event.mine.MineBlockBreakEvent;
import net.ldoin.jmines.event.mine.fill.MineFillEvent;
import net.ldoin.jmines.util.Cuboid;
import net.ldoin.jmines.util.vec.Vec3i;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Mine {

    String name;
    MineType type;

    Cuboid cuboid;

    MineOptions mineOptions;

    public Mine(String name, MineType type, String world) {

        this.name = name;
        this.type = type;

        cuboid = new Cuboid(new Vec3i(0, 0, 0), new Vec3i(0, 0, 0));

        mineOptions = new MineOptions(world);

    }

    protected void callEvent(Event event) {

        Bukkit.getPluginManager().callEvent(event);

    }

    public World getWorld() {

        return Bukkit.getWorld(mineOptions.getWorld());

    }

    public abstract int getMinedPercentage();

    public void fill() {

        callEvent(new MineFillEvent(this));

    }

    public boolean blockBreak(Block block, Player player) {

        MineBlockBreakEvent event = new MineBlockBreakEvent(block, player, this);
        callEvent(event);

        if (event.isCancelled())
            return false;

        if (!mineOptions.getPlacedBlocks().contains(block))
            mineOptions.increaseMinedBlocks();

        mineOptions.getPlacedBlocks().remove(block);

        return true;

    }

    public BukkitRunnable loadTask() {

        return null;

    }
}