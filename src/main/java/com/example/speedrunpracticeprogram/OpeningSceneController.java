package com.example.speedrunpracticeprogram;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class OpeningSceneController{
    @FXML
    private Label newTrickNameLabel;
    @FXML
    private TextField newTrickNameTextField;
    @FXML
    private Button newTrickNameAddButton;
    @FXML
    public void initialize(){
        newTrickNameLabel.setVisible(false);
        newTrickNameTextField.setVisible(false);
        newTrickNameAddButton.setVisible(false);
    }

    @FXML
    protected void onNewTrickButtonClick() {
        newTrickNameLabel.setVisible(true);
        newTrickNameTextField.setVisible(true);
        newTrickNameAddButton.setVisible(true);
    }
    @FXML
    protected void onNewTrickNameAddButtonClick() {

    }

}