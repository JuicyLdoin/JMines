package net.ldoin.jmines.editor.menu;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.JMinesPlugin;
import net.ldoin.jmines.editor.MineEditor;
import net.ldoin.jmines.mine.Mine;
import net.ldoin.jmines.mine.MineOptions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

@Getter
@Setter

@AllArgsConstructor
@RequiredArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class MineEditorMenu {

    final MineEditorMenu previous;
    final MineEditor mineEditor;
    final Mine mine;

    Inventory inventory;

    public String get(String id) {

        return JMinesPlugin.getPlugin().getLangManager().get(id);

    }

    public List<String> getList(String id) {

        return JMinesPlugin.getPlugin().getLangManager().getList(id);

    }

    public void open(Player player) {

        if (inventory != null)
            player.openInventory(inventory);

    }

    public String makeMineString(String str, Mine mine) {

        MineOptions mineOptions = mine.getMineOptions();

        return str.replace("%name%", mine.getName())
                .replace("%world%", mineOptions.getWorld())
                .replace("%min_location%", mine.getCuboid().getMin().toString())
                .replace("%max_location%", mine.getCuboid().getMax().toString())
                .replace("%give_blocks%", String.valueOf(mineOptions.isGiveBlocks()))
                .replace("%give_exp%", String.valueOf(mineOptions.isDropExp()))
                .replace("%reset_delay%", String.valueOf(mineOptions.getResetDelay()))
                .replace("%reset_percentage%", String.valueOf(mineOptions.getResetPercentage()));

    }
}