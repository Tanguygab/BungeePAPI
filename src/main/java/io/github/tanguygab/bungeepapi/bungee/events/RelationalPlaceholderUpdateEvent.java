package io.github.tanguygab.bungeepapi.bungee.events;

import io.github.tanguygab.bungeepapi.common.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.PAPIServer;
import io.github.tanguygab.bungeepapi.common.event.IRelationalPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.common.placeholders.types.PlayerPlaceholder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

@AllArgsConstructor
public class RelationalPlaceholderUpdateEvent extends Event implements IRelationalPlaceholderUpdateEvent {

    @Getter private PlayerPlaceholder placeholder;
    @Getter private PAPIServer server;
    @Getter private PAPIPlayer viewer;
    @Getter private PAPIPlayer target;
    @Getter private String arguments;

}
