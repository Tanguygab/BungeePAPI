package io.github.tanguygab.bungeepapi.spigot;

import com.google.common.io.ByteArrayDataInput;
import io.github.tanguygab.bungeepapi.common.PluginMessageHandler;

public class SpigotPluginMessageHandler extends PluginMessageHandler {

    private final BungeePAPISpigot plugin;

    public SpigotPluginMessageHandler(BungeePAPISpigot plugin) {
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin,PLUGIN_CHANNEL,((SpigotPlatform)super.plugin.getPlatform()).listener);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin,PLUGIN_CHANNEL);
        sendMessage("Load");
    }

    @Override
    public void unload() {
        plugin.getServer().getMessenger().unregisterIncomingPluginChannel(plugin,PLUGIN_CHANNEL);
        plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(plugin,PLUGIN_CHANNEL);
    }

    public void onMessageReceive(byte[] data) {
        ByteArrayDataInput in = readMessage(data);
        String subChannel = in.readUTF();
        switch (subChannel) {
            case "Load" -> {

            }
        }
    }

    public void sendMessage(String... args) {
        plugin.getServer().sendPluginMessage(plugin,PluginMessageHandler.PLUGIN_CHANNEL,createMessage(args));
    }
}