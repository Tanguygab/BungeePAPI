package io.github.tanguygab.bungeepapi.spigot;

import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.PluginMessageHandler;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class SpigotListener implements Listener, PluginMessageListener {

    @Override
    public void onPluginMessageReceived(@NonNull String channel, @NonNull Player player, byte@NonNull[] message) {
        if (channel.equalsIgnoreCase(PluginMessageHandler.PLUGIN_CHANNEL))
            ((SpigotPluginMessageHandler)BungeePAPI.getInstance().getPluginMessageHandler()).onMessageReceive(message);
    }
}