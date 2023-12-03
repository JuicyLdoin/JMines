package net.ldoin.jmines.editor.menu.create;

import net.ldoin.jmines.editor.MineEditor;
import net.ldoin.jmines.editor.menu.MineEditorMenu;
import net.ldoin.jmines.listener.listeners.chat.ChatConsumerListener;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MineEditorCreate extends MineEditorMenu {

    public static final Set<UUID> creating = new HashSet<>();

    public MineEditorCreate(MineEditor mineEditor) {

        super(null, mineEditor, null);

    }

    public void open(Player player) {

        UUID uuid = player.getUniqueId();
        creating.add(uuid);

        player.sendMessage(get("editor_mine_create_message"));

        ChatConsumerListener.consumerMap.put(uuid, event -> {

            event.setCancelled(true);
            ChatConsumerListener.consumerMap.remove(uuid);

            String message = event.getMessage();

            if (message.equalsIgnoreCase("cancel"))
                return;

            if (getMineEditor().getMineManager().getMine(message) != null) {

                player.sendMessage(String.format(get("editor_mine_create_exists"), message));
                return;

            }

            getMineEditor().openPage(player, new MineEditorCreateType(getMineEditor(), message));

        });

    }
}