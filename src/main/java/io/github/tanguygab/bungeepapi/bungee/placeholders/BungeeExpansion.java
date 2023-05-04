package io.github.tanguygab.bungeepapi.bungee.placeholders;

import io.github.tanguygab.bungeepapi.bungee.BungeePAPIBungee;
import io.github.tanguygab.bungeepapi.common.placeholders.Expansion;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class BungeeExpansion extends Expansion {

    protected BungeeExpansion(String identifier) {
        super(identifier);
    }


    public BungeePAPIBungee getPAPI() {
        return BungeePAPIBungee.getInstance();
    }

    public abstract String getValue(ProxiedPlayer p, String args);

}
