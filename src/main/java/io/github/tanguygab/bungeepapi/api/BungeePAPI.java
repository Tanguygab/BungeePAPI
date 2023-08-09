package io.github.tanguygab.bungeepapi.api;

import io.github.tanguygab.bungeepapi.api.events.EventBus;
import io.github.tanguygab.bungeepapi.api.placeholders.Placeholder;
import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIServer;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public abstract class BungeePAPI {

    @Getter @Setter private static BungeePAPI instance;

    public abstract EventBus getEventBus();

    public abstract String parse(String text, PAPIServer server, boolean color);
    public abstract String parse(String text, PAPIServer server, PAPIPlayer player, boolean color);
    public abstract String parse(String text, PAPIServer server, PAPIPlayer viewer, PAPIPlayer target, boolean color);


    public abstract void register(Expansion expansion);
    public abstract Placeholder getPlaceholder(String placeholder);
    public abstract Map<Placeholder,String> getPlacehodlerWithArgs(String placeholderWithArgs);
    public abstract Map<String,Object> getConfig(String expansion);
}
