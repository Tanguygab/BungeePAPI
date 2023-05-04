package io.github.tanguygab.bungeepapi.spigot;

import com.google.common.io.ByteArrayDataInput;
import io.github.tanguygab.bungeepapi.common.PluginMessageHandler;

public class SpigotPluginMessageHandler extends PluginMessageHandler {

    private final BungeePAPISpigot plugin;

    public SpigotPluginMessageHandler(BungeePAPISpigot plugin) {
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin,PLUGIN_CHANNEL,((SpigotPlatform)super.plugin.getPlatform()).listener);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin,PLUGIN_CHANNEL);
    }

    @Override
    public void unload() {
        plugin.getServer().getMessenger().unregisterIncomingPluginChannel(plugin,PLUGIN_CHANNEL);
        plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(plugin,PLUGIN_CHANNEL);
    }

    @Override
    public void onMessageReceive(byte[] data) {
        ByteArrayDataInput in = readMsg(data);
        String subChannel = in.readUTF();
        switch (subChannel) {
            case "Load" -> {}
        }
    }

    @Override
    public void sendMessage() {

    }
}
