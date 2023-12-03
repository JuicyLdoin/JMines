package net.ldoin.jmines.mine.block;

import lombok.Value;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

@Value
public class MineBlock implements IPlaceable {

    String blockData;

    public MineBlock(Block block) {

        blockData = block.getBlockData().getAsString();

    }

    public BlockData getBlockData() {

        return Bukkit.createBlockData(blockData);

    }

    public boolean place(Location location) {

        location.getBlock().setBlockData(getBlockData());
        return true;

    }

    public boolean isOn(Location location) {

        return location.getBlock().getBlockData().equals(getBlockData());

    }
}