package io.github.tanguygab.bungeepapi.spigot;

import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class BungeePAPISpigot extends JavaPlugin {

    @Override
    public void onEnable() {
        BungeePAPI.setInstance(new BungeePAPI(new SpigotPlatform(this),getDataFolder()));
    }

    @Override
    public void onDisable() {
        BungeePAPI.getInstance().unload();
    }

}