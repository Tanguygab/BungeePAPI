package io.github.tanguygab.bungeepapi.bungee;

import com.google.common.io.ByteArrayDataInput;
import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIServer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIWorld;
import io.github.tanguygab.bungeepapi.common.PluginMessageHandler;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public void onMessageReceive(ServerInfo server, byte[] data) {
        ByteArrayDataInput in = readMessage(data);
        String subChannel = in.readUTF();
        PAPIServer s = super.plugin.getServer(server.getName());
        switch (subChannel) {
            case "Load" -> {
                String world;
                while (!(world = in.readUTF()).equals("End")) {
                    int splitIndex = world.indexOf("|");
                    String wName = world.substring(0,splitIndex);
                    String[] playerNames = world.substring(splitIndex+1).split(",");
                    List<PAPIPlayer> players = Arrays.stream(playerNames).map(this::getPlayer).collect(Collectors.toCollection(ArrayList::new));
                    s.addWorld(new PAPIWorld(wName,players));
                }
                sendMessage(server,server.getName());
            }
            case "World Switch" -> {
                PAPIPlayer player = getPlayer(in.readUTF());
                PAPIWorld from = s.getWorld(in.readUTF());
                PAPIWorld to = s.getWorld(in.readUTF());
                if (from != null) from.getPlayers().remove(player); //null on login
                to.getPlayers().add(player);
            }
            case "World Created" -> s.addWorld(new PAPIWorld(in.readUTF(),new ArrayList<>()));
        }
    }

    public void sendMessage(ServerInfo server, String... args) {
        server.sendData(PLUGIN_CHANNEL,createMessage(args));
    }
}
