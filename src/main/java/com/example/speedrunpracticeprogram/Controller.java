package com.example.speedrunpracticeprogram;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Controller {
    DatabaseManager databaseManager = new DatabaseManager();

    protected void trickDropdownSetup(MenuButton selectFromExistingTricksDropdown){
        selectFromExistingTricksDropdown.getItems().clear();
        ArrayList<String> trickList = (ArrayList<String>) databaseManager.getNamesOfTricks();
        for(String trick : trickList) {
            MenuItem nextItem = new MenuItem(trick);
            nextItem.setId(trick);
            selectFromExistingTricksDropdown.getItems().add(nextItem);
            nextItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        CurrentTrickSingleton singleton = CurrentTrickSingleton.getInstance();
                        singleton.setTrickName(nextItem.getText());
                        HelloApplication.changeScene("on trick scene.fxml");
                    }
                    catch (IOException ioException){
                        System.out.println("Something went wrong.");
                        System.out.println(ioException);
                    }

                }
            });
        }
    }
}
