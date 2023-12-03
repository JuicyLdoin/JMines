package net.ldoin.jmines.mine;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum MineType {

    CUBOID(Material.STONE),
    OVERLAY(Material.GRASS),
    REGENERATE(Material.COBBLESTONE);

    Material display;

}