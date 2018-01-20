package pl.edu.wat.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class AlertEvent extends Event {
    public static final EventType<AlertEvent> LANGUAGE_CHANGE = new EventType<>(Event.ANY, "LANGUAGE_CHANGE");

    public AlertEvent() {
        super(LANGUAGE_CHANGE);
    }

    public AlertEvent(EventType<? extends Event> eventType) {
        super(LANGUAGE_CHANGE);
    }

    public AlertEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
