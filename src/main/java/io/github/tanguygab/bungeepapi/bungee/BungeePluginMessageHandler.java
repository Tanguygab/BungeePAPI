package io.github.tanguygab.bungeepapi.bungee;

import com.google.common.io.ByteArrayDataInput;
import io.github.tanguygab.bungeepapi.common.PluginMessageHandler;

public class BungeePluginMessageHandler extends PluginMessageHandler {

    private final BungeePAPIBungee plugin;

    public BungeePluginMessageHandler(BungeePAPIBungee plugin) {
        this.plugin = plugin;
        plugin.getProxy().registerChannel(PLUGIN_CHANNEL);
    }

    @Override
    public void unload() {
        plugin.getProxy().unregisterChannel(PLUGIN_CHANNEL);
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
