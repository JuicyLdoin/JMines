package net.ldoin.jmines.listener.listeners.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ChatConsumerListener implements Listener {

    public static final Map<UUID, Consumer<PlayerChatEvent>> consumerMap = new HashMap<>();

    @EventHandler
    public void onChat(PlayerChatEvent event) {

        UUID uuid = event.getPlayer().getUniqueId();

        if (consumerMap.containsKey(uuid))
            consumerMap.get(uuid).accept(event);

    }
}