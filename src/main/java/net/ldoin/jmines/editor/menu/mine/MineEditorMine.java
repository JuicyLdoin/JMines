package net.ldoin.jmines.editor.menu.mine;

import net.ldoin.jmines.editor.MineEditor;
import net.ldoin.jmines.editor.menu.MineEditorMenu;
import net.ldoin.jmines.mine.Mine;
import net.ldoin.jmines.util.builder.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class MineEditorMine extends MineEditorMenu {

    public MineEditorMine(MineEditorMenu previous, MineEditor mineEditor, Mine mine) {

        super(previous, mineEditor, mine);

        setInventory(mineEditor.createBuilder(Bukkit.createInventory(null, 45, makeMineString(get("editor_mine_menu"), mine)))
                .fill(Material.GRAY_STAINED_GLASS_PANE)
                .setItem(36, new ItemBuilder()
                        .setMaterial(Material.ARROW)
                        .callItemMetaBuilder()
                        .setName(get("editor_item_arrow_back"))
                        .buildItemStack())
                .setCancelClick(true)
                .addOnClick(36, event -> {

                    if (event.getAction() != InventoryAction.PICKUP_ALL)
                        return;

                    mineEditor.openPage((Player) event.getWhoClicked(), previous);

                })
                .build());

    }
}