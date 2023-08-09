package io.github.tanguygab.bungeepapi.api;

import io.github.tanguygab.bungeepapi.api.placeholders.Placeholder;
import io.github.tanguygab.bungeepapi.common.config.ConfigurationFile;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public abstract class Expansion {

    @Getter private final String identifier;
    @Getter private final Map<String,Placeholder> placeholders = new HashMap<>();

    protected void addPlaceholder(Placeholder placeholder) {
        placeholders.put(placeholder.getName(),placeholder);
    }

    public Placeholder getPlaceholder(String name) {
        return placeholders.get(name);
    }

    public BungeePAPI getPAPI() {
        return BungeePAPI.getInstance();
    }
    public Map<String,Object> getConfig() {
        return getPAPI().getConfig(identifier);
    }
}