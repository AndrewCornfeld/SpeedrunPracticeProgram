package com.example.speedrunpracticeprogram;

import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OpeningSceneController{
    DatabaseManager databaseManager = new DatabaseManager();
    @FXML
    private Label newTrickNameLabel;
    @FXML
    private Label emptyNewTrickLabel;
    @FXML
    private TextField newTrickNameTextField;
    @FXML
    private Button newTrickNameAddButton;
    @FXML
    private Button addNewTrickButton;
    @FXML
    private MenuButton selectFromExistingTricksDropdown;
    @FXML
    public void initialize(){
        newTrickNameLabel.setVisible(false);
        newTrickNameTextField.setVisible(false);
        newTrickNameAddButton.setVisible(false);
        emptyNewTrickLabel.setVisible(false);
        try {
            databaseManager.deleteALLTables();
            databaseManager.createStartupTables();
        }
        catch (IllegalStateException ignore){}
        //databaseManager.connect();
    }

    @FXML
    protected void onNewTrickButtonClick() {
        newTrickNameLabel.setVisible(true);
        newTrickNameTextField.setVisible(true);
        newTrickNameAddButton.setVisible(true);
        addNewTrickButton.setDisable(true);
    }
    @FXML
    protected void onNewTrickNameTextFieldTextEntry(KeyEvent event){
        emptyNewTrickLabel.setVisible(false);
    }
    @FXML
    protected void onNewTrickNameAddButtonClick() {
        if(newTrickNameTextField.getText().length()==0){
            emptyNewTrickLabel.setVisible(true);
        }
        try {
            databaseManager.addTrickToDatabase(newTrickNameTextField.getText());
            addNewTrickButton.setDisable(false);
            emptyNewTrickLabel.setTextFill(Color.color(0, 1, 0));
            emptyNewTrickLabel.setText(newTrickNameTextField.getText() + " added!");
            emptyNewTrickLabel.setVisible(true);
        }
        catch (SQLException e){
            if(e.toString().contains("already")){
                emptyNewTrickLabel.setTextFill(Color.color(1,0,0));
                emptyNewTrickLabel.setText("You already have a trick with this name! Create a trick with a different name.");
                emptyNewTrickLabel.setVisible(true);
            }
        }


    }
    @FXML
    protected void onTrickDropdownClick(){
        ObservableList<MenuItem> menuItemObservableList = selectFromExistingTricksDropdown.getItems();
        menuItemObservableList.clear();
        ArrayList<String> trickList = (ArrayList<String>) databaseManager.getNamesOfTricks();
        for(String trick : trickList){
            menuItemObservableList.add(new MenuItem("" + trick));
        }
        selectFromExistingTricksDropdown.getItems().addAll(menuItemObservableList);
    }
}