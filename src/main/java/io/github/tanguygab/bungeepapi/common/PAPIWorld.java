package io.github.tanguygab.bungeepapi.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PAPIWorld {

    @Getter private final String name;
    @Getter private final List<PAPIPlayer<?>> players = new ArrayList<>();

    public PAPIWorld(String name, List<PAPIPlayer<?>> players) {
        this.name = name;
        this.players.addAll(players);
    }

}
