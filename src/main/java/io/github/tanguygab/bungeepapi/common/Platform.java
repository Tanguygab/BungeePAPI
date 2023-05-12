package io.github.tanguygab.bungeepapi.common;

import lombok.Getter;
import lombok.Setter;

public abstract class Platform {

    protected final BungeePAPI instance = BungeePAPI.getInstance();
    @Getter @Setter private boolean loaded;

    public abstract void load();
    public abstract void unload();
    public abstract PluginMessageHandler getPluginMessageHandler();

}