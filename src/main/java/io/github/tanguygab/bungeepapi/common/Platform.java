package io.github.tanguygab.bungeepapi.common;

public abstract class Platform {

    protected final BungeePAPI instance = BungeePAPI.getInstance();

    public abstract void load();
    public abstract void unload();

    public abstract PluginMessageHandler getPluginMessageHandler();
}
