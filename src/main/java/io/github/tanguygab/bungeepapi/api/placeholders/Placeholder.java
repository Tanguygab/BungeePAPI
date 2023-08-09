package io.github.tanguygab.bungeepapi.api.placeholders;

import io.github.tanguygab.bungeepapi.api.events.ServerPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.common.Manager;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class Placeholder {

    @Getter private final String name;
    @Getter private final int refresh;
    @Getter private final List<String> arguments = new ArrayList<>();

    public Placeholder(String name, int refresh) {
        if (refresh % 50 != 0 && refresh != -1) throw new IllegalArgumentException("Refresh interval must be divisible by " + 50);
        this.name = name;
        this.refresh = Manager.getInstance().hasRefreshOverride(name) ? Manager.getInstance().getRefresh(name) : refresh;
    }

    public void addArguments(String args) {
        arguments.add(args);
    }

    public abstract List<ServerPlaceholderUpdateEvent> update();

}
