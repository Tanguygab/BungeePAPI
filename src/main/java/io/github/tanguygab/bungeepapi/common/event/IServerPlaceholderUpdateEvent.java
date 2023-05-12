package io.github.tanguygab.bungeepapi.common.event;

import io.github.tanguygab.bungeepapi.common.PAPIServer;
import io.github.tanguygab.bungeepapi.common.placeholders.types.Placeholder;

public interface IServerPlaceholderUpdateEvent {

    Placeholder getPlaceholder();
    PAPIServer getServer();
    String getArguments();

}
