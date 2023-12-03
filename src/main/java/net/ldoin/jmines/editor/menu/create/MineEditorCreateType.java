package net.ldoin.jmines.editor.menu.create;

import lombok.EqualsAndHashCode;
import lombok.Value;
import net.ldoin.jmines.editor.MineEditor;
import net.ldoin.jmines.editor.menu.MineEditorMenu;
import net.ldoin.jmines.mine.MineType;
import net.ldoin.jmines.util.builder.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.stream.Collectors;

@Value
@EqualsAndHashCode(callSuper = true)
public class MineEditorCreateType extends MineEditorMenu {

    String name;

    public MineEditorCreateType(MineEditor mineEditor, String name) {

        super(null, mineEditor, null);

        this.name = name;

        setInventory(mineEditor.createBuilder(Bukkit.createInventory(null, 9))
                .addItems(Arrays.stream(MineType.values())
                        .map(type -> new ItemBuilder()
                                .setMaterial(type.getDisplay())
                                .callItemMetaBuilder()
                                .setName("&e" + type.name())
                                .buildItemStack())
                        .collect(Collectors.toList()))
                .addOnClick(event -> {

                    ItemStack itemStack = event.getCurrentItem();

                    if (itemStack == null)
                        return;

                    Player player = (Player) event.getWhoClicked();

                    MineType type = MineType.valueOf(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()));
                    mineEditor.getMineManager().createMine(name, type, player.getWorld().getName());

                    mineEditor.close(player);
                    player.sendMessage(String.format(get("editor_mine_created_message"), name, type.name()));

                    MineEditorCreate.creating.remove(player.getUniqueId());

                })
                .build());

    }
}