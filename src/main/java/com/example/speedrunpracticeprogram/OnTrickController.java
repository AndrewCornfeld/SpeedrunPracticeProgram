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
    private Label currentSessionConsistency;
    @FXML
    private Label streakText;
    DatabaseManager databaseManager = new DatabaseManager();

    @FXML
    public void initialize(){
        CurrentTrickSingleton singleton = CurrentTrickSingleton.getInstance();
        trickName = singleton.getTrick().getTrickName();
        currentTrickLabel.setText("Current Trick: " + trickName);
        trickDropdownSetup(selectFromExistingTricksDropdown);
        updateAllTimeSuccessRate();
        updateSessionSuccessRate();
        currentSessionConsistency.setText("Current Session Consistency: ");
        streakText.setVisible(false);
        streakText.setTextFill(Color.color(0, 1, 0));
        updateCurrentStreak(0);
    }
    @FXML
    public void onSuccessClick(){
        AttemptEntry thisEntry = databaseManager.inputButtonPressResultIntoTable(trickName, true);
        updateAllTimeSuccessRate();
        updateSessionSuccessRate();
        updateCurrentStreak(thisEntry.getStreak());
    }
    @FXML
    public void onFailClick(){
        AttemptEntry thisEntry = databaseManager.inputButtonPressResultIntoTable(trickName, false);
        updateAllTimeSuccessRate();
        updateSessionSuccessRate();
        updateCurrentStreak(0);
    }
    public void updateAllTimeSuccessRate(){
        DecimalFormat decimalFormat = new DecimalFormat("##.##%");
        String formattedPercent = decimalFormat.format(databaseManager.getAllTimeSuccessRate(trickName));
        if(!formattedPercent.equals("NaN"))
            allTimeConsistency.setText("All-Time Consistency: " + formattedPercent);
    }
    public void updateSessionSuccessRate(){
        DecimalFormat decimalFormat = new DecimalFormat("##.##%");
        String formattedPercent = decimalFormat.format(databaseManager.getCurrentSessionSuccessRate(trickName));
        if(!formattedPercent.equals("NaN"))
            currentSessionConsistency.setText("Current Session Consistency: " + formattedPercent);
    }
    public void updateCurrentStreak(int streak){
        streakText.setVisible(false);
        streakText.setText("Current Streak: " + streak + " in a row!");
        if(streak >= 3){
            streakText.setVisible(true);
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
