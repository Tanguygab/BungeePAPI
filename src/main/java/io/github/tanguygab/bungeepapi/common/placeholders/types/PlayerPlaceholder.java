package io.github.tanguygab.bungeepapi.common.placeholders.types;

import io.github.tanguygab.bungeepapi.common.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.PAPIServer;
import io.github.tanguygab.bungeepapi.common.utils.TriFunction;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class PlayerPlaceholder extends Placeholder {

    private final Map<PAPIServer,Map<PAPIPlayer, List<String>>> lastValues = new WeakHashMap<>();
    private final TriFunction<PAPIServer,PAPIPlayer,String,String> function;

    public PlayerPlaceholder(String name, int refresh, TriFunction<PAPIServer,PAPIPlayer,String,String> function) {
        super(name, refresh);
        this.function = function;
    }

    public String getValue(PAPIServer server, PAPIPlayer target, String args) {
        return function.apply(server,target,args);
    }

}
