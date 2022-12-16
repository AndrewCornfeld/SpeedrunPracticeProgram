package com.example.speedrunpracticeprogram;

import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;

import java.io.IOException;
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
            //databaseManager.deleteALLTables();
            databaseManager.createStartupTables();
            onTrickDropdownClick();
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
    @FXML
    protected void onTrickDropdownClick(){
        ArrayList<String> trickList = (ArrayList<String>) databaseManager.getNamesOfTricks();
        MenuItem item = new MenuItem(trickList.get(0));
        //ArrayList<MenuItem> itemList = new ArrayList<>();
        for(String trick : trickList) {
            MenuItem nextItem = new MenuItem(trick);
            nextItem.setId(trick);
            selectFromExistingTricksDropdown.getItems().add(nextItem);
            nextItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        HelloApplication.changeScene("on trick scene.fxml");
                    }
                    catch (IOException ioException){
                        System.out.println("Something went wrong.");
                    }

                }
            });
        }
    }

    //https://stackoverflow.com/questions/29605277/javafx-how-to-change-scene-fxml-from-menuitem
}