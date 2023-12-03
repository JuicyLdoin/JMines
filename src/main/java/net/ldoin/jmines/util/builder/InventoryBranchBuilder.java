package net.ldoin.jmines.util.builder;

import net.ldoin.jmines.util.InventoryBranch;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBranchBuilder implements IBuilder<InventoryBranch> {

    private final InventoryBranch inventoryBranch = new InventoryBranch();

    public InventoryBranchBuilder setBackArrow(ItemStack itemStack) {

        inventoryBranch.setNextArrow(itemStack);
        return this;

    }

    public InventoryBranchBuilder setNextArrow(ItemStack itemStack) {

        inventoryBranch.setNextArrow(itemStack);
        return this;

    }

    public InventoryBranchBuilder addPage(Inventory inventory) {

        inventoryBranch.addPage(inventory);
        return this;

    }

    public InventoryBranch build() {

        inventoryBranch.initArrows();
        return inventoryBranch;

    }
}