package io.github.tanguygab.bungeepapi.api.events;

import io.github.tanguygab.bungeepapi.common.entities.PAPIServer;
import io.github.tanguygab.bungeepapi.api.placeholders.Placeholder;

public interface ServerPlaceholderUpdateEvent {

    Placeholder getPlaceholder();
    PAPIServer getServer();
    String getArguments();

}
