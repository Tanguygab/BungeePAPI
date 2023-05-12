package io.github.tanguygab.bungeepapi.common.placeholders.types;

import io.github.tanguygab.bungeepapi.common.PAPIServer;

import java.util.function.BiFunction;

public abstract class ServerPlaceholder extends Placeholder {

    private final BiFunction<PAPIServer,String,String> function;

    public ServerPlaceholder(String name, int refresh, BiFunction<PAPIServer,String,String> function) {
        super(name, refresh);
        this.function = function;
    }

    public String getValue(PAPIServer server, String args) {
        return function.apply(server,args);
    }

}
