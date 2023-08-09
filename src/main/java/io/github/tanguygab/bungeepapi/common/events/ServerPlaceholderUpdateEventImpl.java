package io.github.tanguygab.bungeepapi.common.events;

import io.github.tanguygab.bungeepapi.common.entities.PAPIServer;
import io.github.tanguygab.bungeepapi.api.events.ServerPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.api.placeholders.ServerPlaceholder;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public class ServerPlaceholderUpdateEventImpl implements ServerPlaceholderUpdateEvent {

    @Getter private ServerPlaceholder placeholder;
    @Getter private PAPIServer server;
    @Getter private String arguments;

}