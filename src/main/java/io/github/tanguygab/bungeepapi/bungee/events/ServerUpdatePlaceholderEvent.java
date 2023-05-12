package io.github.tanguygab.bungeepapi.bungee.events;

import io.github.tanguygab.bungeepapi.common.PAPIServer;
import io.github.tanguygab.bungeepapi.common.event.IServerPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.common.placeholders.types.ServerPlaceholder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

@AllArgsConstructor
public class ServerUpdatePlaceholderEvent extends Event implements IServerPlaceholderUpdateEvent {

    @Getter private ServerPlaceholder placeholder;
    @Getter private PAPIServer server;
    @Getter private String arguments;

}
