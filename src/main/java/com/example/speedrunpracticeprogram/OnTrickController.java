package com.example.speedrunpracticeprogram;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OnTrickController {
    String trickName;
    @FXML
    private Label currentTrickLabel;

    public OnTrickController(String trick) {
        trickName = trick;
    }

    @FXML
    public void initialize(){
        currentTrickLabel.setText("Current Trick: " + trickName);
    }
}
