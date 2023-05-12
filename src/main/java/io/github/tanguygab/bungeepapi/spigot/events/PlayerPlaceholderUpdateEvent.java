package io.github.tanguygab.bungeepapi.spigot.events;

import io.github.tanguygab.bungeepapi.common.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.PAPIServer;
import io.github.tanguygab.bungeepapi.common.event.IPlayerPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.common.placeholders.types.PlayerPlaceholder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
public class PlayerPlaceholderUpdateEvent extends Event implements IPlayerPlaceholderUpdateEvent {

    @Getter private static final HandlerList Handlers = new HandlerList();
    @Getter private PlayerPlaceholder placeholder;
    @Getter private PAPIServer server;
    @Getter private PAPIPlayer target;
    @Getter private String arguments;

}