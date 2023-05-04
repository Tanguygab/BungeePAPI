package io.github.tanguygab.bungeepapi.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
public abstract class PAPIPlayer<T> {

    @Getter private final String name;
    @Getter private final UUID UUID;
    @Getter private final T player;
    @Getter @Setter private PAPIServer server;
    @Getter @Setter private PAPIWorld world;


}
