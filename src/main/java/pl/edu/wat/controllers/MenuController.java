package pl.edu.wat.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import pl.edu.wat.ApplicationSettingsReader;

public class MenuController {

    @FXML
    MenuBar topMenu;

    private ApplicationSettingsReader asr = new ApplicationSettingsReader();

    public void changeLanguage(Event e){
        Object src = e.getSource();
        MenuItem item = (MenuItem) src;
        switch (item.getText()){
            case "Polski":
                asr.setLanguage("pl");
                break;
            case "English":
                asr.setLanguage("en");
                break;
            default:
                break;
        }
        showInfo();
    }

    public void changeTheme(Event e){
        Object src = e.getSource();
        MenuItem item = (MenuItem) src;

        asr.setTheme(item.getText());
        showInfo();
    }

    private void showInfo(){
        //TODO zaimplementować
        //powinno pojawiajać się okno informujące o konieczności restartu aplikacji
    }

}
