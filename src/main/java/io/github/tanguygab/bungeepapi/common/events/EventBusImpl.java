package io.github.tanguygab.bungeepapi.common.events;

import io.github.tanguygab.bungeepapi.api.events.EventBus;
import io.github.tanguygab.bungeepapi.api.events.ServerPlaceholderUpdateEvent;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import net.kyori.event.EventSubscriber;
import net.kyori.event.PostResult;
import net.kyori.event.SimpleEventBus;

import java.util.function.Consumer;

public final class EventBusImpl implements EventBus {

    private final SimpleEventBus<ServerPlaceholderUpdateEvent> bus;

    public EventBusImpl() {
        this.bus = new SimpleEventBus<>(ServerPlaceholderUpdateEvent.class) {
            @Override
            protected boolean shouldPost(@NonNull ServerPlaceholderUpdateEvent event, @NonNull EventSubscriber<?> subscriber) {
                return true;
            }
        };
    }

    public <E extends ServerPlaceholderUpdateEvent> void fire(final E event) {
        if (!bus.hasSubscribers(event.getClass())) return;
        final PostResult result = bus.post(event);
        if (result.exceptions().isEmpty()) return;

        for (final Throwable exception : result.exceptions().values()) exception.printStackTrace();
    }

    @Override
    public <E extends ServerPlaceholderUpdateEvent> void register(Class<E> type, Consumer<E> handler) {
        bus.register(type, new HandlerWrapper<>(handler));
    }

    @Override
    public <E extends ServerPlaceholderUpdateEvent> void unregister(Consumer<E> handler) {
        bus.unregister(subscriber -> subscriber instanceof HandlerWrapper && ((HandlerWrapper<?>) subscriber).handler == handler);
    }

    @AllArgsConstructor
    private static final class HandlerWrapper<E> implements EventSubscriber<E> {

        private final Consumer<E> handler;

        @Override
        public void invoke(@NonNull E event) {
            handler.accept(event);
        }
    }
}
