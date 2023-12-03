package net.ldoin.jmines.mine.manager;

import lombok.Value;
import net.ldoin.jmines.JMinesPlugin;
import net.ldoin.jmines.event.mine.manager.MineCreateEvent;
import net.ldoin.jmines.event.mine.manager.MineDeleteEvent;
import net.ldoin.jmines.event.mine.manager.MineLoadEvent;
import net.ldoin.jmines.mine.Mine;
import net.ldoin.jmines.mine.MineType;
import net.ldoin.jmines.mine.type.CuboidMine;
import net.ldoin.jmines.mine.type.OverlayMine;
import net.ldoin.jmines.mine.type.RegenerateMine;
import net.ldoin.jmines.util.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Value
public class MineManager {

    JMinesPlugin plugin = JMinesPlugin.getPlugin();

    File folder = new File(plugin.getDataFolder(), "mines");
    FileUtil fileUtil = new FileUtil();

    Map<String, Mine> mines = new HashMap<>();
    Map<Mine, BukkitRunnable> tasks = new HashMap<>();

    @Nullable
    public Mine getMine(String name) {

        return mines.get(name);

    }

    @Nullable
    public Mine getMine(Block block) {

        return mines.values()
                .stream()
                .filter(mine -> mine.getCuboid().contains(block))
                .findAny()
                .orElse(null);

    }

    private File getMineFile(Mine mine) {

        return new File(folder, mine.getName() + ".json");

    }

    private void callEvent(Event event) {

        Bukkit.getPluginManager().callEvent(event);

    }

    public void loadMine(Mine mine) {

        BukkitRunnable task = mine.loadTask();

        if (task != null)
            tasks.put(mine, task);

        callEvent(new MineLoadEvent(mine));

    }

    public void createMine(String name, MineType type, String world) {

        Mine mine;

        switch (type) {

            case CUBOID:
                mine = new CuboidMine(name, type, world);
                break;
            case OVERLAY:
                mine = new OverlayMine(name, type, world);
                break;
            case REGENERATE:
                mine = new RegenerateMine(name, type, world);
                break;
            default:
                mine = null;

        }

        if (mine == null)
            throw new IllegalArgumentException(String.format("Mine type %s not registered", type.name()));

        MineCreateEvent event = new MineCreateEvent(mine);
        callEvent(event);

        if (!event.isCancelled()) {

            loadMine(mine);
            saveMine(mine);

            mines.put(mine.getName(), mine);

        }
    }

    public void createMine(Mine mine) {

        MineCreateEvent event = new MineCreateEvent(mine);
        callEvent(event);

        if (!event.isCancelled()) {

            loadMine(mine);
            saveMine(mine);

            mines.put(mine.getName(), mine);

        }
    }

    public void deleteMine(Mine mine) {

        MineDeleteEvent event = new MineDeleteEvent(mine);
        callEvent(event);

        if (!event.isCancelled())
            if (getMineFile(mine).delete()) {

                BukkitRunnable task = tasks.get(mine);

                if (task != null)
                    task.cancel();

                mines.remove(mine.getName());

            }
    }

    public void saveMine(Mine mine) {

        fileUtil.writeJson(getMineFile(mine), mine);

    }

    public void load() {

        if (!folder.exists()) {

            folder.mkdirs();
            return;

        }

        for (File file : Objects.requireNonNull(folder.listFiles())) {

            MineType mineType = MineType.valueOf(fileUtil.readStringInJson(file, "type"));
            Mine mine;

            switch (mineType) {

                case CUBOID:
                    mine = fileUtil.readJson(file, CuboidMine.class);
                    break;
                case OVERLAY:
                    mine = fileUtil.readJson(file, OverlayMine.class);
                    break;
                case REGENERATE:
                    mine = fileUtil.readJson(file, RegenerateMine.class);
                    break;
                default:
                    mine = null;

            }

            if (mine == null)
                continue;

            createMine(mine);

        }
    }

    public void unload() {

        if (!folder.exists())
            folder.mkdirs();

        mines.values().forEach(this::saveMine);

    }
}