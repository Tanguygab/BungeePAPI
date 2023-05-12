package io.github.tanguygab.bungeepapi.common;

import io.github.tanguygab.bungeepapi.common.config.ConfigurationFile;
import io.github.tanguygab.bungeepapi.common.config.YamlConfigurationFile;
import io.github.tanguygab.bungeepapi.common.placeholders.Manager;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BungeePAPI {

    @Getter @Setter private static BungeePAPI instance;
    @Getter private final Platform platform;
    private final File dataFolder;
    @Getter private CPUManager cpu;
    private Manager manager;
    @Getter private PluginMessageHandler pluginMessageHandler;
    @Getter private ConfigurationFile config;

    @Getter private final Map<UUID,PAPIPlayer> players = new HashMap<>();
    @Getter private final Map<String,PAPIServer> servers = new HashMap<>();


    public BungeePAPI(Platform platform, File dataFolder) {
        this.platform = platform;
        this.dataFolder = dataFolder;
        load();
    }

    public void load() {
        cpu = new CPUManager();
        try {
            config = new YamlConfigurationFile(BungeePAPI.class.getClassLoader().getResourceAsStream("config.yml"), new File(dataFolder, "config.yml"));
        } catch (Exception e) {e.printStackTrace();}
        manager = new Manager();
        platform.load();
        pluginMessageHandler = platform.getPluginMessageHandler();
        cpu.enable();
    }

    public void unload() {
        platform.unload();
        pluginMessageHandler.unload();
        servers.clear();
        players.clear();
    }

    public void addPlayer(PAPIPlayer player) {
        players.put(player.getUUID(),player);
    }
    public PAPIPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }
    public PAPIWorld getWorld(String name) {
        for (PAPIServer server : servers.values())
            if (server.getWorlds().containsKey(name))
                return server.getWorlds().get(name);
        return null;
    }
    public PAPIServer getServer(String name) {
        return servers.get(name);
    }
    public void addServer(PAPIServer server) {
        servers.put(server.getName(),server);
    }

    public static String parse(String text, PAPIServer server, boolean color) {
        return parse(text,server,null,null,color);
    }
    public static String parse(String text, PAPIServer server, PAPIPlayer player, boolean color) {
        return parse(text,server,player,player,color);
    }
    public static String parse(String text, PAPIServer server, PAPIPlayer viewer, PAPIPlayer target, boolean color) {
        return getInstance().manager.parse(text,server,viewer,target,color);
    }
}