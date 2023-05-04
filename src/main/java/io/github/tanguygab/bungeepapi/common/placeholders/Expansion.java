package io.github.tanguygab.bungeepapi.common.placeholders;

import lombok.Getter;

import java.util.List;

public abstract class Expansion {

    @Getter private final String identifier;
    private final List<Placeholder> placeholders;

    protected Expansion(String identifier) {
        this.identifier = identifier;
        placeholders = getPlaceholders();
    }

    public abstract List<Placeholder> getPlaceholders();

    public Placeholder getPlaceholder(String name) {
        return placeholders.stream().filter(p->p.getName().equals(name)).findFirst().orElse(null);
    }
}
