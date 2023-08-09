package io.github.tanguygab.bungeepapi.api.placeholders;

import io.github.tanguygab.bungeepapi.api.events.ServerPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIServer;
import io.github.tanguygab.bungeepapi.common.events.ServerPlaceholderUpdateEventImpl;

import java.util.*;
import java.util.function.BiFunction;

public class ServerPlaceholder extends Placeholder {

    private final Map<PAPIServer,Map<String,String>> lastValues = new WeakHashMap<>();
    private final BiFunction<PAPIServer,String,String> function;

    public ServerPlaceholder(String name, int refresh, BiFunction<PAPIServer,String,String> function) {
        super(name, refresh);
        this.function = function;
    }

    public String get(PAPIServer server, String args) {
        String output = function.apply(server,args);
        lastValues.computeIfAbsent(server,m->new WeakHashMap<>()).put(args,output);
        return output;
    }

    public String getLastValue(PAPIServer server, String args) {
        if (!lastValues.containsKey(server) || !lastValues.get(server).containsKey(args)) return get(server,args);
        return lastValues.get(server).getOrDefault(args,null);
    }

    @Override
    public List<ServerPlaceholderUpdateEvent> update() {
        List<ServerPlaceholderUpdateEvent> updatedPlaceholders = new ArrayList<>();
        for (PAPIServer server : BungeePAPI.getInstance().getServers().values())
            for (String args : getArguments())
                if (get(server,args).equals(getLastValue(server,args)))
                    updatedPlaceholders.add(new ServerPlaceholderUpdateEventImpl(this,server,args));
        return updatedPlaceholders;
    }

}
