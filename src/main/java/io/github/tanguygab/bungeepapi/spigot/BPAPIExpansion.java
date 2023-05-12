package io.github.tanguygab.bungeepapi.spigot;

import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.PAPIServer;
import io.github.tanguygab.bungeepapi.common.PAPIWorld;
import io.github.tanguygab.bungeepapi.common.placeholders.Manager;
import lombok.Getter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BPAPIExpansion extends PlaceholderExpansion {

    private final BungeePAPISpigot plugin;
    private final BungeePAPI papi = BungeePAPI.getInstance();
    @Getter private final List<String> placeholders = new ArrayList<>();
    @Getter private final String identifier = "bungeepapi";;
    @Getter private final String author = "Tanguygab";;
    @Getter private final String version;

    public BPAPIExpansion(BungeePAPISpigot plugin) {
        this.plugin = plugin;
        version = plugin.getDescription().getVersion();
        for (String pl : Arrays.asList(
                "proxy_<placeholder>",
                "server:<server>_<placeholder>"
        )) placeholders.add("%bungeepapi_"+pl+"%");
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        String[] args = params.split("_");
        String arg = args[0];
        String placeholder = params.substring(args.length > 1 ? arg.length()+1 : arg.length());

        if (arg.equalsIgnoreCase("proxy")) {
            return onProxyPlaceholder(placeholder);
        }
        boolean current = arg.equalsIgnoreCase("current");
        if (arg.startsWith("server:") || current) {
            if (!arg.contains(":") && !current) return "Unknown Server";
            return onServerPlaceholder(current ? ((SpigotPlatform)papi.getPlatform()).getServer() : papi.getServer(arg.split(":")[1]),placeholder);
        }
        if (arg.equalsIgnoreCase("parse")) {
            // %bungeepapi_parse_proxy_<placeholder>%
            // %bungeepapi_parse_server:<server>_<placeholder>%
            // %bungeepapi_parse_proxyother:<player>_<placeholder>%
            // %bungeepapi_parse_serverother:<server>,<player>_<placeholder>%


        }
        return null;
    }

    private String onProxyPlaceholder(String params) {
        return null;
    }

    private String onServerPlaceholder(PAPIServer server, String params) {
        if (server == null) return "Unknown Server";
        String[] args = params.split("_");
        return switch (args[0].toLowerCase()) {
            case "name" -> server.getName();
            case "status" -> server.isStatus()+"";
            case "worlds" ->server.getWorlds().values().stream().map(PAPIWorld::getName).collect(Collectors.joining(", "));
            case "worldcount" ->server.getWorlds().size()+"";
            case "players" ->server.getPlayers().stream().map(PAPIPlayer::getName).collect(Collectors.joining(", "));
            case "playercount" ->server.getPlayers().size()+"";
            default -> null;
        };
    }
}