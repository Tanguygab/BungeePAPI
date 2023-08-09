package io.github.tanguygab.bungeepapi.bungee;

import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.defaultexpansions.ReplaceExpansion;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePAPIBungee extends Plugin implements Listener {

    @Getter private static BungeePAPIBungee instance;

    @Override
    public void onEnable() {
        instance = this;
        BungeePAPI.setInstance(new BungeePAPI(new BungeePlatform(this),getDataFolder()));
        BungeePAPI.getInstance().register(new ReplaceExpansion());
    }

    @Override
    public void onDisable() {
        BungeePAPI.getInstance().unload();
    }
}
