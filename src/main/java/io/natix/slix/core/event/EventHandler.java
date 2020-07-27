package io.natix.slix.core.event;

import io.natix.slix.core.payload.Event;

public interface EventHandler {

    void onReceived(Event event);

}
