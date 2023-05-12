package io.github.tanguygab.bungeepapi.common.event;

import io.github.tanguygab.bungeepapi.common.PAPIPlayer;

public interface IPlayerPlaceholderUpdateEvent extends IServerPlaceholderUpdateEvent {

    PAPIPlayer getTarget();

}
