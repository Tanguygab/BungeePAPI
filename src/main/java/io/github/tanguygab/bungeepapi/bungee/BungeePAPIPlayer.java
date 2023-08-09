package io.github.tanguygab.bungeepapi.bungee;

import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIWorld;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeePAPIPlayer extends PAPIPlayer {

    public BungeePAPIPlayer(ProxiedPlayer player, PAPIWorld world) {
        super(player.getName(),player.getUniqueId(),player,BungeePAPI.getInstance().getServer(player.getServer().getInfo().getName()),world);
    }
}
