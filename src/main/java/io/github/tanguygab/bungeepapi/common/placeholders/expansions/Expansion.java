package io.github.tanguygab.bungeepapi.common.placeholders.expansions;

import io.github.tanguygab.bungeepapi.common.placeholders.types.Placeholder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public abstract class Expansion {

    @Getter private final String identifier;
    @Getter private final Map<String,Placeholder> placeholders = new HashMap<>();

    public void addPlaceholder(Placeholder placeholder) {
        placeholders.put(placeholder.getName(),placeholder);
    }

    public Placeholder getPlaceholder(String name) {
        return placeholders.get(name);
    }
}