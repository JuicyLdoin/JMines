package net.ldoin.jmines.mine;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MineOptions {

    String world;

    boolean giveBlocks;
    boolean dropExp;

    int resetDelay;
    int resetPercentage;

    int minedBlocks;
    List<Block> placedBlocks;

    Material menuIcon;

    public MineOptions(String world) {

        this.world = world;

        giveBlocks = false;
        dropExp = false;

        resetDelay = 0;
        resetPercentage = 0;

        minedBlocks = 0;
        placedBlocks = new ArrayList<>();

        menuIcon = Material.STONE;

    }

    public void increaseMinedBlocks() {

        minedBlocks++;

    }

    public void resetMinedBlocks() {

        minedBlocks = 0;

    }
}