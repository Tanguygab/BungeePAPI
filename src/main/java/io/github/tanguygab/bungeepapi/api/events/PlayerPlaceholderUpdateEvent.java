package io.github.tanguygab.bungeepapi.api.events;

import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;

public interface PlayerPlaceholderUpdateEvent extends ServerPlaceholderUpdateEvent {

    PAPIPlayer getPlayer();

}
