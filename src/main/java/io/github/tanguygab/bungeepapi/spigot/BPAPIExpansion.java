package io.github.tanguygab.bungeepapi.spigot;

import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIServer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIWorld;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BPAPIExpansion extends PlaceholderExpansion {

    private final BungeePAPI papi = BungeePAPI.getInstance();
    @Getter private final List<String> placeholders = new ArrayList<>();
    @Getter private final String identifier = "bungeepapi";
    @Getter private final String author = "Tanguygab";
    @Getter private final String version;
    @Getter @Accessors(fluent = true) boolean persist = true;

    public BPAPIExpansion(BungeePAPISpigot plugin) {
        version = plugin.getDescription().getVersion();
        for (String pl : Arrays.asList(
                "servers",
                "servercount",
                "serverstatuses",
                "players",
                "playercount",
                "parse_<placeholder>"
        )) placeholders.add("%bungeepapi_proxy_"+pl+"%");
        for (String pl : Arrays.asList(
                "name",
                "status",
                "worlds",
                "worldcount",
                "players",
                "playercount",
                "parse_<placeholder>"
        )) placeholders.add("%bungeepapi_server:<server>_"+pl+"%");
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        String[] args = params.split("_");
        String arg = args[0];
        String placeholder = params.substring(args.length > 1 ? arg.length()+1 : arg.length());

        if (arg.equalsIgnoreCase("proxy")) return onProxyPlaceholder(placeholder,papi.getPlayer(player.getUniqueId()));

        boolean current = arg.equalsIgnoreCase("current");
        if (arg.startsWith("server:") || current) {
            if (!arg.contains(":") && !current) return "Unknown Server";
            PAPIServer server = current ? ((SpigotPlatform)papi.getPlatform()).getServer() : papi.getServer(arg.split(":")[1]);
            return onServerPlaceholder(server,placeholder,papi.getPlayer(player.getUniqueId()));
        }
        return null;
    }


    private String onProxyPlaceholder(String params, PAPIPlayer player) {
        return switch (params.split("_")[0].toLowerCase()) {
            case "servers" -> String.join(",",papi.getServers().keySet());
            case "servercount" -> papi.getServers().size()+"";
            case "serverstatuses" -> papi.getServers().values().stream().map(s->(s.isOnline()?"&a":"&c")+s.getName()).collect(Collectors.joining(", "));
            case "players" -> papi.getPlayers().values().stream().map(PAPIPlayer::getName).collect(Collectors.joining(", "));
            case "playercount" -> papi.getPlayers().size()+"";
            case "parse" -> papi.parse(params,null,player,true);
            default -> null;
        };
    }

    private String onServerPlaceholder(PAPIServer server, String params, PAPIPlayer player) {
        if (server == null) return "Unknown Server";
        String[] args = params.split("_");
        return switch (args[0].toLowerCase()) {
            case "name" -> server.getName();
            case "status" -> server.isOnline()+"";
            case "worlds" -> server.getWorlds().values().stream().map(PAPIWorld::getName).collect(Collectors.joining(", "));
            case "worldcount" -> server.getWorlds().size()+"";
            case "players" -> server.getPlayers().stream().map(PAPIPlayer::getName).collect(Collectors.joining(", "));
            case "playercount" -> server.getPlayers().size()+"";
            case "parse" -> papi.parse(params,server,player,true);
            default -> null;
        };
    }
}