package io.github.tanguygab.bungeepapi.bungee;

import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.PluginMessageHandler;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeListener implements Listener {

    @EventHandler
    public void onPlMsg(PluginMessageEvent e) {
        if (e.getTag().equalsIgnoreCase(PluginMessageHandler.PLUGIN_CHANNEL))
            BungeePAPI.getInstance().getPluginMessageHandler().onMessageReceive(e.getData());
    }

}
