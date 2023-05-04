package io.github.tanguygab.bungeepapi.common.placeholders;

import io.github.tanguygab.bungeepapi.common.utils.TriFunction;
import lombok.Getter;


public abstract class Placeholder {

    @Getter private final String name;
    @Getter private final int refresh;


    public Placeholder(String name, int refresh) {
        this.name = name;
        this.refresh = refresh;
    }

}

