package io.github.tanguygab.bungeepapi.spigot;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.NonNull;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BPAPIExpansion extends PlaceholderExpansion {

    public final BungeePAPISpigot plugin;

    public BPAPIExpansion(BungeePAPISpigot plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NonNull String getIdentifier() {
        return "bungeepapi";
    }

    @Override
    public @NonNull String getAuthor() {
        return "Tanguygab";
    }

    @Override
    public @NonNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NonNull List<String> getPlaceholders() {
        List<String> list = new ArrayList<>(Arrays.asList(
                "proxy_<placeholder>",
                "server:<server>_<placeholder>"
        ));

        for (String pl : list)
            list.set(list.indexOf(pl),"%bungeepapi_"+pl+"%");

        return list;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        String arg = params.split("_")[0];
        String placeholder = params.substring(arg.length());

        if (arg.equalsIgnoreCase("proxy")) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Placeholder");
            out.writeUTF("Proxy");
            out.writeUTF(placeholder);
            plugin.getServer().sendPluginMessage(plugin,plugin.CHANNEL_NAME,out.toByteArray());
            return "Loading...";
        }

        if (arg.startsWith("server:")) {
            if (!arg.contains(":")) return "Unknown Server";
            String server = arg.split(":")[1];

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Placeholder");
            out.writeUTF("Server");
            out.writeUTF(server);
            out.writeUTF(placeholder);
            plugin.getServer().sendPluginMessage(plugin,plugin.CHANNEL_NAME,out.toByteArray());
            return "Loading...";
        }

        return null;
    }
}
