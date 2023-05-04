package io.github.tanguygab.bungeepapi.spigot;

import io.github.tanguygab.bungeepapi.common.PAPIServer;
import io.github.tanguygab.bungeepapi.common.PAPIWorld;
import io.github.tanguygab.bungeepapi.common.Platform;
import io.github.tanguygab.bungeepapi.common.PluginMessageHandler;
import org.bukkit.event.HandlerList;

public class SpigotPlatform extends Platform {

    private final BungeePAPISpigot plugin;
    private final PAPIServer server;
    protected SpigotListener listener;

    public SpigotPlatform(BungeePAPISpigot plugin, PAPIServer server) {
        this.plugin = plugin;
        this.server = server;
    }

    @Override
    public void load() {
        plugin.getServer().getWorlds().forEach(world-> server.addWorld(new PAPIWorld(world.getName())));
        plugin.getServer().getOnlinePlayers().forEach(p->instance.addPlayer(new SpigotPAPIPlayer(p,server)));
        plugin.getServer().getPluginManager().registerEvents(listener = new SpigotListener(),plugin);
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(plugin);
    }

    @Override
    public PluginMessageHandler getPluginMessageHandler() {
        return new SpigotPluginMessageHandler(plugin);
    }
}
