package net.ldoin.jmines.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.util.builder.InventoryBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryBranch {

    ItemStack backArrow;
    ItemStack nextArrow;

    List<Inventory> pages;

    public InventoryBranch() {

        pages = new ArrayList<>();

    }

    public InventoryBranch(ItemStack backArrow, ItemStack nextArrow, List<Inventory> pages) {

        this.backArrow = backArrow;
        this.nextArrow = nextArrow;

        this.pages = pages;

        initArrows();

    }

    public void addPage(Inventory inventory) {

        pages.add(inventory);

    }

    public void removePage(int index) {

        pages.remove(index - 1);

    }

    public void initArrows() {

        for (int i = 0; i < pages.size(); i++) {

            boolean back = i > 0;
            boolean next = pages.size() - 1 > i;

            InventoryBuilder inventory = new InventoryBuilder(pages.get(i));

            if (back) {

                int slot = inventory.getInventory().getSize() - 6;
                int finalI = i;

                inventory.setItem(slot, backArrow)
                        .addOnClick(slot, event -> open((Player) event.getWhoClicked(), finalI - 1));

            }

            if (next) {

                int slot = inventory.getInventory().getSize() - 2;
                int finalI = i;

                inventory.setItem(slot, nextArrow)
                        .addOnClick(slot, event -> open((Player) event.getWhoClicked(), finalI + 1));

            }

            pages.set(i, inventory.getInventory());

        }
    }

    public void open(Player player, int index) {

        player.openInventory(pages.get(index - 1));

    }
}