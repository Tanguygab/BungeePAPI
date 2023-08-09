package io.github.tanguygab.bungeepapi.common.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class PAPIServer {

    @Getter private final String name;
    @Getter @Setter private boolean online;
    @Getter private final Map<String,PAPIWorld> worlds = new HashMap<>();

    public void addWorld(PAPIWorld world) {
        worlds.put(world.getName(),world);
    }
    public PAPIWorld getWorld(String name) {
        return worlds.get(name);
    }
    public List<PAPIPlayer> getPlayers() {
        return worlds.values().stream().map(PAPIWorld::getPlayers).flatMap(Collection::stream).toList();
    }

}