package io.github.tanguygab.bungeepapi.api.placeholders;

import io.github.tanguygab.bungeepapi.api.events.ServerPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIServer;
import io.github.tanguygab.bungeepapi.common.events.PlayerPlaceholderUpdateEventImpl;
import io.github.tanguygab.bungeepapi.common.events.ServerPlaceholderUpdateEventImpl;
import io.github.tanguygab.bungeepapi.common.utils.TriFunction;

import java.util.*;

public class PlayerPlaceholder extends Placeholder {

    private final Map<PAPIServer,Map<PAPIPlayer, Map<String,String>>> lastValues = new WeakHashMap<>();
    private final TriFunction<PAPIServer,PAPIPlayer,String,String> function;

    public PlayerPlaceholder(String name, int refresh, TriFunction<PAPIServer,PAPIPlayer,String,String> function) {
        super(name, refresh);
        this.function = function;
    }

    public String get(PAPIServer server, PAPIPlayer target, String args) {
        String output = function.apply(server,target,args);
        lastValues.computeIfAbsent(server,m->new WeakHashMap<>()).computeIfAbsent(target,m->new HashMap<>()).put(args,output);
        return output;
    }

    public String getLastValue(PAPIServer server, PAPIPlayer target, String args) {
        if (!lastValues.containsKey(server) || !lastValues.get(server).containsKey(target) || !lastValues.get(server).get(target).containsKey(args)) return get(server,target,args);
        return lastValues.get(server).get(target).getOrDefault(args,null);
    }

    @Override
    public List<ServerPlaceholderUpdateEvent> update() {
        List<ServerPlaceholderUpdateEvent> updatedPlaceholders = new ArrayList<>();
        BungeePAPI papi = BungeePAPI.getInstance();
        for (PAPIServer server : papi.getServers().values())
            for (PAPIPlayer target : papi.getPlayers().values())
                for (String args : getArguments())
                    if (get(server,target,args).equals(getLastValue(server,target,args)))
                        updatedPlaceholders.add(new PlayerPlaceholderUpdateEventImpl(this,server,target,args));
        return updatedPlaceholders;
    }

}
