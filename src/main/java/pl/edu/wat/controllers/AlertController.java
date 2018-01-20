package pl.edu.wat.controllers;

import javafx.event.EventHandler;
import lombok.Getter;
import pl.edu.wat.events.AlertEvent;

public class AlertController implements EventHandler<AlertEvent> {
    @Getter private static final AlertController instance = new AlertController();

    private AlertController() {
    }

    @Override
    public void handle(AlertEvent event) {
        //getSource
        //getEventType
        System.out.println();
    }

}
