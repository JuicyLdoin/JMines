package net.ldoin.jmines.util.builder;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.util.builder.item.ItemBuilder;
import net.ldoin.jmines.util.builder.item.ItemMetaBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@SuppressWarnings({"unused"})
public class InventoryBuilder implements IBuilder<Inventory> {

    @Getter
    private static final ConcurrentHashMap<Inventory, InventoryBuilder> buildedInventories = new ConcurrentHashMap<>();
    final Inventory inventory;
    final List<Consumer<InventoryClickEvent>> clickEventConsumers = new ArrayList<>();
    final List<Consumer<InventoryCloseEvent>> closeEventConsumers = new ArrayList<>();
    final Map<Integer, Consumer<InventoryClickEvent>> clickInSlotEventConsumers = new HashMap<>();
    boolean cancelClick = false;
    boolean useCacheOnce = true;

    public InventoryBuilder addItem(ItemStack itemStack) {

        inventory.addItem(itemStack);
        return this;

    }

    public InventoryBuilder addItems(List<ItemStack> itemStacks) {

        for (ItemStack itemStack : itemStacks)
            inventory.addItem(itemStack);

        return this;

    }

    public InventoryBuilder setItem(int slot, ItemStack itemStack) {

        inventory.setItem(slot, itemStack);
        return this;

    }

    public InventoryBuilder setItem(int slot, ItemBuilder itemBuilder) {

        return setItem(slot, itemBuilder.build());

    }

    public InventoryBuilder setItem(int slot, ItemMetaBuilder itemMetaBuilder) {

        return setItem(slot, itemMetaBuilder.buildItemStack());

    }

    public InventoryBuilder setItem(int slot, Material material) {

        return setItem(slot, new ItemStack(material));

    }

    public InventoryBuilder fill(ImmutableMap<Integer, ItemStack> items) {

        if (items.isEmpty())
            return this;

        items.keySet()
                .stream()
                .filter(slot -> inventory.getSize() >= slot)
                .forEach(slot -> inventory.setItem(slot, items.get(slot)));

        return this;

    }

    public InventoryBuilder fill(int from, int to, ItemStack itemStack) {

        for (int slot = from; slot < to; slot++)
            if (inventory.getItem(slot) == null)
                inventory.setItem(slot, itemStack);

        return this;

    }

    public InventoryBuilder fill(int from, int to, ItemBuilder itemBuilder) {

        return fill(from, to, itemBuilder.build());

    }

    public InventoryBuilder fill(int from, int to, Material material) {

        return fill(from, to, new ItemStack(material));

    }

    public InventoryBuilder fill(ItemStack itemStack) {

        return fill(0, inventory.getSize(), itemStack);

    }

    public InventoryBuilder fill(ItemBuilder itemBuilder) {

        return fill(itemBuilder.build());

    }

    public InventoryBuilder fill(Material material) {

        return fill(new ItemStack(material));

    }

    public InventoryBuilder setCancelClick(boolean cancelClick) {

        this.cancelClick = cancelClick;
        return this;

    }

    public InventoryBuilder setUseCacheOnce(boolean useCacheOnce) {

        this.useCacheOnce = useCacheOnce;
        return this;

    }

    public InventoryBuilder addOnClick(Consumer<InventoryClickEvent> clickEventConsumer) {

        clickEventConsumers.add(clickEventConsumer);
        return this;

    }

    public InventoryBuilder addOnClick(int slot, Consumer<InventoryClickEvent> clickEventConsumer) {

        clickInSlotEventConsumers.put(slot, clickEventConsumer);
        return this;

    }

    public InventoryBuilder addOnClose(Consumer<InventoryCloseEvent> closeEventConsumer) {

        closeEventConsumers.add(closeEventConsumer);
        return this;

    }

    public Inventory build() {

        if (!clickEventConsumers.isEmpty() || !clickInSlotEventConsumers.isEmpty() || useCacheOnce || cancelClick)
            buildedInventories.put(inventory, this);

        return inventory;

    }
}