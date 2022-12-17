package com.example.speedrunpracticeprogram;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

import java.text.DecimalFormat;

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
    @FXML
    private Label allTimeConsistency;
    DatabaseManager databaseManager = new DatabaseManager();

    @FXML
    public void initialize(){
        CurrentTrickSingleton singleton = CurrentTrickSingleton.getInstance();
        trickName = singleton.getTrick().trickName;
        currentTrickLabel.setText("Current Trick: " + trickName);
        trickDropdownSetup(selectFromExistingTricksDropdown);
        updateAllTimeSuccessRate();
    }
    @FXML
    public void onSuccessClick(){
        databaseManager.inputButtonPressResultIntoTable(trickName, true);
        updateAllTimeSuccessRate();
    }
    @FXML
    public void onFailClick(){
        databaseManager.inputButtonPressResultIntoTable(trickName, false);
        updateAllTimeSuccessRate();
    }
    public void updateAllTimeSuccessRate(){
        DecimalFormat decimalFormat = new DecimalFormat("##.##%");
        String formattedPercent = decimalFormat.format(databaseManager.getAllTimeSuccessRate(trickName));
        if(!formattedPercent.equals("NaN"))
            allTimeConsistency.setText("All-Time Consistency: " + formattedPercent);
    }
}
