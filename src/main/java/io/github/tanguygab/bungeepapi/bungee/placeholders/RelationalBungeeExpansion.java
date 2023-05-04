package io.github.tanguygab.bungeepapi.bungee.placeholders;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class RelationalBungeeExpansion extends BungeeExpansion {

    protected RelationalBungeeExpansion(String identifier, int refresh) {
        super(identifier, refresh);
        if (!identifier.startsWith("%rel_")) throw new IllegalArgumentException("Relational placeholder identifiers must start with \"rel_\"");
    }

    @Override
    public String getValue(ProxiedPlayer p, String args) {
        throw new IllegalStateException("Not supported for relational placeholders");
    }

    public abstract String getValue(ProxiedPlayer viewer, ProxiedPlayer target, String args);

}
