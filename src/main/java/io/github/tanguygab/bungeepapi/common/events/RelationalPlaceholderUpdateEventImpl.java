package io.github.tanguygab.bungeepapi.common.events;

import io.github.tanguygab.bungeepapi.api.events.RelationalPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIServer;
import io.github.tanguygab.bungeepapi.api.placeholders.RelationalPlaceholder;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public class RelationalPlaceholderUpdateEventImpl implements RelationalPlaceholderUpdateEvent {

    @Getter private RelationalPlaceholder placeholder;
    @Getter private PAPIServer server;
    @Getter private PAPIPlayer viewer;
    @Getter private PAPIPlayer target;
    @Getter private String arguments;

}