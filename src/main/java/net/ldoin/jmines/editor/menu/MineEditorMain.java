package net.ldoin.jmines.editor.menu;

import net.ldoin.jmines.editor.MineEditor;
import net.ldoin.jmines.editor.menu.create.MineEditorCreate;
import net.ldoin.jmines.editor.menu.mine.MineEditorMine;
import net.ldoin.jmines.mine.Mine;
import net.ldoin.jmines.mine.MineOptions;
import net.ldoin.jmines.mine.manager.MineManager;
import net.ldoin.jmines.util.builder.InventoryBranchBuilder;
import net.ldoin.jmines.util.builder.InventoryBuilder;
import net.ldoin.jmines.util.builder.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

public class MineEditorMain extends MineEditorMenu {

    public MineEditorMain(MineEditor mineEditor) {

        super(null, mineEditor, null);

        InventoryBranchBuilder inventoryBranchBuilder = new InventoryBranchBuilder()
                .setNextArrow(new ItemBuilder()
                        .setMaterial(Material.ARROW)
                        .callItemMetaBuilder()
                        .setName(get("editor_item_arrow_next"))
                        .buildItemStack())
                .setBackArrow(new ItemBuilder()
                        .setMaterial(Material.ARROW)
                        .callItemMetaBuilder()
                        .setName(get("editor_item_arrow_back"))
                        .buildItemStack());
        MineManager mineManager = mineEditor.getMineManager();

        int displayed = 0;
        int page = 1;

        InventoryBuilder inventoryBuilder = createPage(page);

        for (Mine mine : mineManager.getMines().values()) {

            inventoryBuilder.setItem(displayed, getMineItem(mine))
                    .addOnClick(displayed, event -> {

                        if (event.getAction() != InventoryAction.PICKUP_ALL)
                            return;

                        mineEditor.openPage((Player) event.getWhoClicked(), new MineEditorMine(this, mineEditor, mine));

                    });

            displayed++;

            if (displayed == 27) {

                displayed = 0;
                page++;

                inventoryBranchBuilder.addPage(inventoryBuilder.build());

                if (mineManager.getMines().size() > page * 27)
                    inventoryBuilder = createPage(page);

            }
        }

        inventoryBranchBuilder.addPage(inventoryBuilder
                .setItem(27, new ItemBuilder()
                        .setMaterial(Material.STONE)
                        .callItemMetaBuilder()
                        .setName(get("editor_mine_create_item")))
                .addOnClick(27, event -> mineEditor.openPage((Player) event.getWhoClicked(), new MineEditorCreate(mineEditor)))
                .build());

        setInventory(inventoryBranchBuilder.build().getPages().get(0));

    }

    private InventoryBuilder createPage(int page) {

        return getMineEditor().createBuilder(Bukkit.createInventory(null, 36, String.format(get("editor_main_page"), page)))
                .fill(0, 27, Material.GRAY_STAINED_GLASS_PANE)
                .fill(27, 36, Material.YELLOW_STAINED_GLASS_PANE);

    }

    private ItemStack getMineItem(Mine mine) {

        MineOptions mineOptions = mine.getMineOptions();

        return new ItemBuilder()
                .setMaterial(mineOptions.getMenuIcon())
                .callItemMetaBuilder()
                .setName(makeMineString(get("editor_mine_name"), mine))
                .setLore(getList("editor_mine_lore").stream()
                        .map(str -> makeMineString(str, mine))
                        .collect(Collectors.toList()))
                .buildItemStack();

    }
}