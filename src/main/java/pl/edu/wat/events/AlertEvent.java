package pl.edu.wat.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class AlertEvent extends Event {
    public static final EventType<AlertEvent> LANGUAGE_CHANGE = new EventType<>(Event.ANY, "LANGUAGE_CHANGE");
    public static final EventType<AlertEvent> THEME_CHANGE = new EventType<>(Event.ANY, "THEME_CHANGE");
    public static final EventType<AlertEvent> CURRENCY_CHANGE = new EventType<>(Event.ANY, "CURRENCY_CHANGE");
    public static final EventType<AlertEvent> RESERVATION_ADD_SUCCESS = new EventType<>(Event.ANY, "RESERVATION_ADD_SUCCESS");
    public static final EventType<AlertEvent> ROOM_ADD_SUCCESS = new EventType<>(Event.ANY, "ROOM_ADD_SUCCESS");

    public AlertEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public AlertEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
