package net.ldoin.jmines.util.builder.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.util.builder.IBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)

@SuppressWarnings({"unused"})
public class ItemBuilder implements IBuilder<ItemStack> {

    @Getter
    private static final ConcurrentHashMap<ItemStack, ItemBuilder> buildedItems = new ConcurrentHashMap<>();
    final List<Consumer<PlayerInteractEvent>> interactEventConsumers = new ArrayList<>();
    final List<Consumer<InventoryClickEvent>> inventoryClickEventConsumers = new ArrayList<>();
    @Setter
    ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {

        this.itemStack = itemStack;

    }

    public ItemBuilder() {

        itemStack = new ItemStack(Material.AIR, 1);

    }

    public static ItemBuilder getBuildedItem(ItemStack itemStack) {

        return buildedItems.get(itemStack);

    }

    public ItemBuilder setMaterial(Material material) {

        itemStack.setType(material);
        return this;

    }

    public ItemBuilder setAmount(int amount) {

        itemStack.setAmount(Math.min(itemStack.getMaxStackSize(), amount));
        return this;

    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {

        Objects.requireNonNull(itemStack.getItemMeta()).addItemFlags(itemFlag);
        return this;

    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean unsafe) {

        if (unsafe)
            itemStack.addUnsafeEnchantment(enchantment, level);
        else
            itemStack.addEnchantment(enchantment, level);

        return this;

    }

    public ItemBuilder setItemMeta(ItemMeta itemMeta) {

        itemStack.setItemMeta(itemMeta);
        return this;

    }

    public ItemBuilder setItemMeta(ItemMetaBuilder itemMetaBuilder) {

        itemMetaBuilder.build(itemStack);
        return this;

    }

    public ItemBuilder addOnInteract(Consumer<PlayerInteractEvent> interactConsumer) {

        interactEventConsumers.add(interactConsumer);
        return this;

    }

    public ItemBuilder addOnInventoryClick(Consumer<InventoryClickEvent> inventoryClickConsumer) {

        inventoryClickEventConsumers.add(inventoryClickConsumer);
        return this;

    }

    public ItemMetaBuilder callItemMetaBuilder() {

        return new ItemMetaBuilder(this);

    }

    public ItemStack build() {

        if (!interactEventConsumers.isEmpty() || !inventoryClickEventConsumers.isEmpty())
            buildedItems.put(itemStack, this);

        return itemStack;

    }
}