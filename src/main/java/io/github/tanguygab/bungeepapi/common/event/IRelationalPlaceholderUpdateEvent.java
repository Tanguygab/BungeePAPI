package io.github.tanguygab.bungeepapi.common.event;

import io.github.tanguygab.bungeepapi.common.PAPIPlayer;

public interface IRelationalPlaceholderUpdateEvent extends IServerPlaceholderUpdateEvent {

    PAPIPlayer getViewer();
    PAPIPlayer getTarget();

}
