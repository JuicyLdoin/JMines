package net.ldoin.jmines.editor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Value;
import net.ldoin.jmines.JMinesPlugin;
import net.ldoin.jmines.editor.menu.MineEditorMain;
import net.ldoin.jmines.editor.menu.MineEditorMenu;
import net.ldoin.jmines.mine.manager.MineManager;
import net.ldoin.jmines.util.builder.InventoryBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Value
public class MineEditor {

    JMinesPlugin plugin = JMinesPlugin.getPlugin();
    MineManager mineManager = plugin.getMineManager();

    Multimap<Player, MineEditorMenu> history = HashMultimap.create();

    @Nullable
    private MineEditorMenu[] getHistory(Player player) {

        if (!history.containsKey(player))
            return null;

        return history.get(player).toArray(new MineEditorMenu[0]);

    }

    public InventoryBuilder createBuilder(Inventory inventory) {

        return new InventoryBuilder(inventory)
                .setCancelClick(true)
                .setUseCacheOnce(false);

    }

    public void openPage(Player player, MineEditorMenu menu) {

        player.closeInventory();

        if (history.containsEntry(player, menu))
            history.remove(player, menu);

        history.put(player, menu);
        menu.open(player);

    }

    public void openMainPage(Player player) {

        clear(player);
        openPage(player, new MineEditorMain(this));

    }

    public void close(Player player) {

        player.closeInventory();
        clear(player);

    }

    public void clear(Player player) {

        MineEditorMenu[] array = getHistory(player);

        if (array == null)
            return;

        for (MineEditorMenu menu : array) {

            if (menu == null)
                continue;

            Inventory inventory = menu.getInventory();

            if (inventory != null)
                InventoryBuilder.getBuildedInventories().remove(inventory);

            history.remove(player, menu);

        }

        history.removeAll(player);

    }

    public void load() {

        Objects.requireNonNull(plugin.getCommand("jmines")).setExecutor(new MineEditorCommand(this, plugin.getLangManager()));

    }
}