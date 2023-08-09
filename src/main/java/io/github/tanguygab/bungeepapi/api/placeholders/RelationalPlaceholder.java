package io.github.tanguygab.bungeepapi.api.placeholders;

import io.github.tanguygab.bungeepapi.api.events.ServerPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.common.BungeePAPI;
import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIServer;
import io.github.tanguygab.bungeepapi.common.events.PlayerPlaceholderUpdateEventImpl;
import io.github.tanguygab.bungeepapi.common.events.RelationalPlaceholderUpdateEventImpl;
import io.github.tanguygab.bungeepapi.common.utils.QuadriFunction;

import java.util.*;

public class RelationalPlaceholder extends Placeholder {

    private final Map<PAPIServer,Map<PAPIPlayer,Map<PAPIPlayer, Map<String,String>>>> lastValues = new WeakHashMap<>();
    private final QuadriFunction<PAPIServer,PAPIPlayer,PAPIPlayer,String,String> function;

    public RelationalPlaceholder(String name, int refresh, QuadriFunction<PAPIServer,PAPIPlayer,PAPIPlayer,String,String> function) {
        super(name, refresh);
        this.function = function;
    }

    public String get(PAPIServer server, PAPIPlayer viewer, PAPIPlayer target, String args) {
        String output = function.apply(server,viewer,target,args);
        lastValues.computeIfAbsent(server,m->new WeakHashMap<>()).computeIfAbsent(viewer, m->new HashMap<>()).computeIfAbsent(target, m->new HashMap<>()).put(args,output);
        return output;
    }

    public String getLastValue(PAPIServer server, PAPIPlayer viewer, PAPIPlayer target, String args) {
        if (!lastValues.containsKey(server) || !lastValues.get(server).containsKey(viewer) || !lastValues.get(server).get(viewer).containsKey(target)
                || !lastValues.get(server).get(viewer).get(target).containsKey(args)) return get(server,viewer,target,args);
        return lastValues.get(server).get(viewer).get(target).getOrDefault(args,null);
    }


    @Override
    public List<ServerPlaceholderUpdateEvent> update() {
        List<ServerPlaceholderUpdateEvent> updatedPlaceholders = new ArrayList<>();
        BungeePAPI papi = BungeePAPI.getInstance();
        for (PAPIServer server : papi.getServers().values())
            for (PAPIPlayer viewer : papi.getPlayers().values())
                for (PAPIPlayer target : papi.getPlayers().values())
                    for (String args : getArguments())
                        if (get(server,viewer,target,args).equals(getLastValue(server,viewer,target,args)))
                            updatedPlaceholders.add(new RelationalPlaceholderUpdateEventImpl(this,server,viewer,target,args));
        return updatedPlaceholders;
    }

}
