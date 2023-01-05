package com.example.speedrunpracticeprogram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class OpeningSceneController extends Controller{
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
            //databaseManager.deleteALLTables();
            databaseManager.createStartupTables();
            trickDropdownSetup(selectFromExistingTricksDropdown);
        }
        catch (IllegalStateException ignore){}
        //databaseManager.connect();
    }

    @FXML
    protected void onNewTrickButtonClick() {
        databaseManager.createStartupTables();
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
            addTrickToDropdown(newTrickNameTextField.getText());
            trickDropdownSetup(selectFromExistingTricksDropdown);
        }
        catch (SQLException e){
            System.out.println(e);
            if(e.toString().contains("already")){
                emptyNewTrickLabel.setTextFill(Color.color(1,0,0));
                emptyNewTrickLabel.setText("You already have a trick with this name! Create a trick with a different name.");
                emptyNewTrickLabel.setVisible(true);
            }
        }


    }
    private void addTrickToDropdown(String trickName){
        selectFromExistingTricksDropdown.getItems().add(new MenuItem(trickName));
    }

    public void onDeleteTrickButtonClick(ActionEvent actionEvent) {
        try {
            HelloApplication.changeScene("delete scroll scene.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //https://stackoverflow.com/questions/29605277/javafx-how-to-change-scene-fxml-from-menuitem
}