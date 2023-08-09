package io.github.tanguygab.bungeepapi.api.events;

import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;

public interface RelationalPlaceholderUpdateEvent extends ServerPlaceholderUpdateEvent {

    PAPIPlayer getViewer();
    PAPIPlayer getTarget();

}
