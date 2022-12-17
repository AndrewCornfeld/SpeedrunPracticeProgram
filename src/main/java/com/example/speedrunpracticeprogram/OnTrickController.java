package com.example.speedrunpracticeprogram;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.paint.Color;

import java.io.IOException;
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
    private Button returnToMainMenuButton;
    @FXML
    private Label allTimeConsistency;
    @FXML
    private Label streakText;
    DatabaseManager databaseManager = new DatabaseManager();
    int currentStreak;

    @FXML
    public void initialize(){
        CurrentTrickSingleton singleton = CurrentTrickSingleton.getInstance();
        trickName = singleton.getTrick().trickName;
        currentTrickLabel.setText("Current Trick: " + trickName);
        trickDropdownSetup(selectFromExistingTricksDropdown);
        updateAllTimeSuccessRate();
        streakText.setVisible(false);
        streakText.setTextFill(Color.color(0, 1, 0));
        currentStreak = 0;
    }
    @FXML
    public void onSuccessClick(){
        databaseManager.inputButtonPressResultIntoTable(trickName, true);
        updateAllTimeSuccessRate();
        updateCurrentStreak(true);
    }
    @FXML
    public void onFailClick(){
        databaseManager.inputButtonPressResultIntoTable(trickName, false);
        updateAllTimeSuccessRate();
        updateCurrentStreak(false);
    }
    public void updateAllTimeSuccessRate(){
        DecimalFormat decimalFormat = new DecimalFormat("##.##%");
        String formattedPercent = decimalFormat.format(databaseManager.getAllTimeSuccessRate(trickName));
        if(!formattedPercent.equals("NaN"))
            allTimeConsistency.setText("All-Time Consistency: " + formattedPercent);
    }
    public void updateCurrentStreak(boolean success){
        if(success){
            currentStreak++;
        }
        else {
            currentStreak = 0;
        }

    }
    @FXML
    public void onReturnToMainMenuClick(){
        try {
            CurrentTrickSingleton singleton = CurrentTrickSingleton.getInstance();
            singleton.setTrickName(null);
            HelloApplication.changeScene("opening scene.fxml");
        }
        catch (IOException ioException){
            System.out.println("Error Returning to title screen.");
            System.out.println(ioException);
        }
    }
}
