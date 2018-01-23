package pl.edu.wat.events;

import javafx.event.Event;
import javafx.event.EventType;
import pl.edu.wat.controllers.MainController;

public class ChangeViewEvent extends Event{
    public static final EventType<ChangeViewEvent> ROOMS_VIEW = new EventType<>(Event.ANY, "ROOMS_VIEW");
    public static final EventType<ChangeViewEvent> RESERVATIONS_VIEW = new EventType<>(Event.ANY, "RESERVATIONS_VIEW");
    public static final EventType<ChangeViewEvent> RESERVATIONS_ADD_VIEW = new EventType<>(Event.ANY, "RESERVATIONS_ADD_VIEW");

    public ChangeViewEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public static ChangeViewEvent createEvent(int which){
        switch (which) {
            case MainController.ROOMS_VIEW:
                return new ChangeViewEvent(ROOMS_VIEW);
            case MainController.RESERVATIONS_VIEW:
                return new ChangeViewEvent(RESERVATIONS_VIEW);
            case MainController.RESERVATIONS_ADD_VIEW:
                return new ChangeViewEvent(RESERVATIONS_ADD_VIEW);
            default:
                return new ChangeViewEvent(ROOMS_VIEW);
        }
    }

}
