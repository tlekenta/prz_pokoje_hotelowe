package pl.edu.wat.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ReservationAddController implements ChangeListener<Number> {

    @FXML
    AnchorPane mainPane;

    @FXML
    AnchorPane leftPane;

    @FXML
    AnchorPane rightPane;

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        updateWidth(newValue.doubleValue() / 2);
    }

    public void updateWidth(double newWidth) {
        AnchorPane.setRightAnchor(leftPane, newWidth);
        AnchorPane.setLeftAnchor(rightPane, newWidth);
    }
}
