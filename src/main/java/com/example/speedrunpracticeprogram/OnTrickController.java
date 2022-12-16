package com.example.speedrunpracticeprogram;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

public class OnTrickController extends Controller {
    String trickName;
    @FXML
    private Label currentTrickLabel;
    @FXML
    private MenuButton selectFromExistingTricksDropdown;
    @FXML
    private Button success;
    @FXML
    private Button fail;
    DatabaseManager databaseManager = new DatabaseManager();

    @FXML
    public void initialize(){
        CurrentTrickSingleton singleton = CurrentTrickSingleton.getInstance();
        trickName = singleton.getTrick().trickName;
        currentTrickLabel.setText("Current Trick: " + trickName);
        trickDropdownSetup(selectFromExistingTricksDropdown);
    }
    @FXML
    public void onSuccessClick(){
        databaseManager.inputButtonPressResultIntoTable(trickName, true);
    }
    @FXML
    public void onFailClick(){
        databaseManager.inputButtonPressResultIntoTable(trickName, false);
    }
}
