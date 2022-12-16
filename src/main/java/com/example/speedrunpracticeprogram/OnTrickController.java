package com.example.speedrunpracticeprogram;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OnTrickController extends Controller {
    String trickName;
    @FXML
    private Label currentTrickLabel;
    DatabaseManager databaseManager = new DatabaseManager();

    @FXML
    public void initialize(){
        CurrentTrickSingleton singleton = CurrentTrickSingleton.getInstance();
        trickName = singleton.getTrick().trickName;
        currentTrickLabel.setText("Current Trick: " + trickName);

    }
}
