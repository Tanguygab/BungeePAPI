package io.github.tanguygab.bungeepapi.common.events;

import io.github.tanguygab.bungeepapi.api.events.PlayerPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIServer;
import io.github.tanguygab.bungeepapi.api.placeholders.PlayerPlaceholder;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public class PlayerPlaceholderUpdateEventImpl implements PlayerPlaceholderUpdateEvent {

    @Getter private PlayerPlaceholder placeholder;
    @Getter private PAPIServer server;
    @Getter private PAPIPlayer player;
    @Getter private String arguments;

}