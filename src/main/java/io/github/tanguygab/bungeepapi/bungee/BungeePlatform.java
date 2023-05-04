package io.github.tanguygab.bungeepapi.bungee;

import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.PAPIServer;
import io.github.tanguygab.bungeepapi.common.Platform;
import io.github.tanguygab.bungeepapi.common.PluginMessageHandler;
import net.md_5.bungee.api.plugin.Listener;

import java.util.concurrent.TimeUnit;

public class BungeePlatform extends Platform {

    private final BungeePAPIBungee plugin;
    private Listener listener;

    public BungeePlatform(BungeePAPIBungee plugin) {
        this.plugin = plugin;
    }

    @Override
    public void load() {
        plugin.getProxy().getServers().forEach((server,info)-> instance.addServer(new PAPIServer(server,false)));
        plugin.getProxy().getPlayers().forEach(p-> BungeePAPI.getInstance().addPlayer(new BungeePAPIPlayer(p,null)));

        plugin.getProxy().getPluginManager().registerListener(plugin,listener = new BungeeListener());
        plugin.getProxy().getScheduler().schedule(plugin,()->
                        plugin.getProxy().getServers().forEach((server, info)->info.ping((result, error) -> instance.getServer(server).setStatus(error == null))),
                0,10, TimeUnit.SECONDS);
    }

    @Override
    public void unload() {
        plugin.getProxy().getPluginManager().unregisterListener(listener);
    }

    @Override
    public PluginMessageHandler getPluginMessageHandler() {
        return new BungeePluginMessageHandler(plugin);
    }
}
