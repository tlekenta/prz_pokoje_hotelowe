package pl.edu.wat.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import pl.edu.wat.MenuController;

import java.io.IOException;

public class TopMenu extends AnchorPane {

    private MenuController controller;

    public TopMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("top_menu.fxml"));
        loader.setControllerFactory(param -> controller = new MenuController());
        getChildren().add(loader.load());
    }
}
