package net.ldoin.jmines.util.builder.item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.util.builder.IBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@SuppressWarnings("unused")
public class ItemMetaBuilder implements IBuilder<ItemMeta> {

    @Getter
    ItemMeta itemMeta;
    ItemStack itemStack;

    ItemBuilder itemBuilder;

    public ItemMetaBuilder(ItemMeta itemMeta) {

        this.itemMeta = itemMeta;

    }

    public ItemMetaBuilder(ItemStack itemStack) {

        itemMeta = itemStack.getItemMeta();
        this.itemStack = itemStack;

    }

    public ItemMetaBuilder(ItemBuilder itemBuilder) {

        itemMeta = itemBuilder.getItemStack().getItemMeta();
        itemStack = itemBuilder.getItemStack();

        this.itemBuilder = itemBuilder;

    }

    public ItemMetaBuilder(ItemMetaBuilder itemMetaBuilder) {

        itemMeta = itemMetaBuilder.getItemMeta();
        itemStack = itemMetaBuilder.itemStack;

        itemBuilder = itemMetaBuilder.itemBuilder;

    }

    public ItemMetaBuilder setName(String name) {

        itemMeta.setDisplayName(name.replace("&", "§"));
        return this;

    }

    public ItemMetaBuilder setLore(List<String> lore) {

        itemMeta.setLore(lore.stream().map(string -> string.replace("&", "§")).collect(Collectors.toList()));
        return this;

    }

    public ItemMetaBuilder setLore(String... lore) {

        return setLore(Arrays.asList(lore));

    }

    public ItemMetaBuilder appendLore(String string) {

        List<String> lore = itemMeta.getLore();

        if (lore == null)
            lore = new ArrayList<>();

        lore.add(string.replace("&", "§"));
        return setLore(lore);

    }

    public ItemMetaBuilder appendLore(String... strings) {

        Arrays.stream(strings).forEach(this::appendLore);
        return this;

    }

    public ItemMetaBuilder appendLore(List<String> strings) {

        strings.forEach(this::appendLore);
        return this;

    }

    public ItemMetaBuilder setCustomModelData(int customModelData) {

        itemMeta.setCustomModelData(customModelData);
        return this;

    }

    public ItemMetaBuilder setUnbreakable(boolean unbreakable) {

        itemMeta.setUnbreakable(unbreakable);
        return this;

    }

    public ItemMeta build() {

        return itemMeta;

    }

    public ItemStack buildItemStack() {

        if (itemBuilder != null)
            return build(itemBuilder.build());
        else
            return build(itemStack);

    }

    public ItemStack build(ItemStack itemStack) {

        itemStack.setItemMeta(itemMeta);
        return itemStack;

    }
}