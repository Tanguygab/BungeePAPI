package io.github.tanguygab.bungeepapi.spigot.events;

import io.github.tanguygab.bungeepapi.common.PAPIServer;
import io.github.tanguygab.bungeepapi.common.event.IServerPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.common.placeholders.types.ServerPlaceholder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
public class ServerUpdatePlaceholderEvent extends Event implements IServerPlaceholderUpdateEvent {

    @Getter private static final HandlerList Handlers = new HandlerList();
    @Getter private ServerPlaceholder placeholder;
    @Getter private PAPIServer server;
    @Getter private String arguments;

}