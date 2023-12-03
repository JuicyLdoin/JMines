package net.ldoin.jmines.mine.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.JMinesPlugin;
import net.ldoin.jmines.event.mine.fill.MinePreFillEvent;
import net.ldoin.jmines.event.mine.fill.MineRegenerateBlockEvent;
import net.ldoin.jmines.mine.Mine;
import net.ldoin.jmines.mine.MineOptions;
import net.ldoin.jmines.mine.MineType;
import net.ldoin.jmines.mine.block.MineBlock;
import net.ldoin.jmines.mine.block.type.RegenerateBlock;
import net.ldoin.jmines.util.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegenerateMine extends Mine {

    final List<Location> locations;
    final Map<Location, RegenerateBlock> blocks;

    public RegenerateMine(String name, MineType type, String world) {

        super(name, type, world);

        locations = new ArrayList<>();
        blocks = new HashMap<>();

    }

    public RegenerateMine(String name, MineType type, Cuboid cuboid, MineOptions mineOptions, List<Location> locations, Map<Location, RegenerateBlock> blocks) {

        super(name, type, cuboid, mineOptions);

        this.locations = locations;
        this.blocks = blocks;

    }

    public int getMinedPercentage() {

        return -1;

    }

    public void fill() {

        MinePreFillEvent event = new MinePreFillEvent(this);
        callEvent(event);

        if (event.isCancelled())
            return;

        blocks.forEach(((location, regenerateBlock) -> regenerateBlock.place(location)));

        super.fill();

    }

    public boolean blockBreak(Block block, Player player) {

        Location location = block.getLocation();

        if (!blocks.containsKey(location))
            return false;

        RegenerateBlock regenerateBlock = blocks.get(location);
        MineBlock replaceBlock = regenerateBlock.getReplace();

        if (replaceBlock.isOn(location))
            return false;

        replaceBlock.place(location);
        Bukkit.getScheduler().runTaskLater(JMinesPlugin.getPlugin(), () -> {

            if (regenerateBlock.place(location))
                callEvent(new MineRegenerateBlockEvent(this, location.getBlock()));

        }, regenerateBlock.getTicks());

        return super.blockBreak(block, player);

    }
}