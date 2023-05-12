package io.github.tanguygab.bungeepapi.common.placeholders.types;

import io.github.tanguygab.bungeepapi.common.placeholders.Manager;
import lombok.Getter;

public abstract class Placeholder {

    @Getter private final String name;
    @Getter private final int refresh;

    public Placeholder(String name, int refresh) {
        this.name = name;
        this.refresh = Manager.getInstance().hasRefreshOverride(name) ? Manager.getInstance().getRefresh(name) : refresh;
    }

}
