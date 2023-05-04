package io.github.tanguygab.bungeepapi.bungee;

import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.PAPIWorld;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeePAPIPlayer extends PAPIPlayer<ProxiedPlayer> {

    public BungeePAPIPlayer(ProxiedPlayer player, PAPIWorld world) {
        super(player.getName(),player.getUniqueId(),player,BungeePAPI.getInstance().getServer(player.getServer().getInfo().getName()),world);
    }
}
