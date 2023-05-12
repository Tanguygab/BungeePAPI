package io.github.tanguygab.bungeepapi.spigot;

import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.PAPIServer;
import org.bukkit.entity.Player;

public class SpigotPAPIPlayer extends PAPIPlayer {

    public SpigotPAPIPlayer(Player player, PAPIServer server) {
        super(player.getName(), player.getUniqueId(), player, server,BungeePAPI.getInstance().getWorld(player.getWorld().getName()));
    }
}