package io.github.tanguygab.bungeepapi.common.placeholders.types;

import io.github.tanguygab.bungeepapi.common.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.PAPIServer;
import io.github.tanguygab.bungeepapi.common.utils.QuadriFunction;

public class RelationalPlaceholder extends Placeholder {
    protected final QuadriFunction<PAPIServer,PAPIPlayer,PAPIPlayer,String,String> function;
    public RelationalPlaceholder(String name, int refresh, QuadriFunction<PAPIServer,PAPIPlayer,PAPIPlayer,String,String> function) {
        super(name, refresh);
        this.function = function;
    }

    public String getValue(PAPIServer server, PAPIPlayer viewer, PAPIPlayer target, String args) {
        return function.apply(server,viewer,target,args);
    }

}
