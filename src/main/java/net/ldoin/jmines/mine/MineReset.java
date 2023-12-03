package net.ldoin.jmines.mine;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.JMinesPlugin;
import net.ldoin.jmines.event.mine.MineMinedEvent;
import net.ldoin.jmines.util.Cuboid;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class MineReset extends Mine {

    int toReset;
    int resetBlocks;

    public MineReset(String name, MineType type, String world) {

        super(name, type, world);

    }

    public MineReset(String name, MineType type, Cuboid cuboid, MineOptions mineOptions) {

        super(name, type, cuboid, mineOptions);

        toReset = mineOptions.getResetDelay();
        resetBlocks = 0;

    }

    public int getMinedPercentage() {

        return getResetBlocks() / getCuboid().getArea() * getMineOptions().getMinedBlocks();

    }

    public void fill() {

        resetTimer();

        super.fill();

    }

    public void resetTimer() {

        setToReset(getMineOptions().getResetDelay());

    }

    public boolean blockBreak(Block block, Player player) {

        if (getMineOptions().getMinedBlocks() - 1 == resetBlocks)
            callEvent(new MineMinedEvent(this));

        return super.blockBreak(block, player);

    }

    public BukkitRunnable loadTask() {

        BukkitRunnable runnable = new BukkitRunnable() {

            public void run() {

                toReset--;

                if (toReset == 0) {

                    fill();
                    return;

                }

                int resetPercentage = getMineOptions().getResetPercentage();

                if (resetPercentage > 0 && 100 - getMinedPercentage() >= resetPercentage)
                    fill();

            }
        };
        runnable.runTaskTimer(JMinesPlugin.getPlugin(), 0, 20);

        return runnable;

    }
}