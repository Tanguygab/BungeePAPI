package io.github.tanguygab.bungeepapi.api.events;

import java.util.function.Consumer;

public interface EventBus {

    /**
     * Registers the given handler to this event bus.
     *
     * @param type    the class type of the event
     * @param handler the handler to register
     * @param <E>     the type of the event
     */
    <E extends ServerPlaceholderUpdateEvent> void register(Class<E> type, Consumer<E> handler);

    /**
     * Unregisters the given handler from this event bus.
     *
     * @param handler the handler to unregister
     * @param <E>     the type of the event
     */
    <E extends ServerPlaceholderUpdateEvent> void unregister(Consumer<E> handler);
}