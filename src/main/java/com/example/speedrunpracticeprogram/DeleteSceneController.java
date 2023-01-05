package com.example.speedrunpracticeprogram;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class DeleteSceneController {
    @FXML
    public TableView<Trick> trickTableView;
    @FXML
    public TableColumn<Trick, String> nameColumn;
    @FXML
    public TableColumn<Trick, Integer> timesAttemptedColumn;
    @FXML
    public TableColumn<Trick, Integer> streakColumn;
    @FXML
    public VBox deleteOptionsVBox;
    @FXML
    public Label deleteText;
    private DatabaseManager databaseManager = new DatabaseManager();
    public void initialize(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("trickName"));
        timesAttemptedColumn.setCellValueFactory(new PropertyValueFactory<>("timesAttempted"));
        ArrayList<Trick> trickArrayList = databaseManager.getAllTricks();
        trickTableView.setItems(observableList1);
        deleteOptionsVBox.setVisible(false);
        trickTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    System.out.println(trickTableView.getSelectionModel().getSelectedItem());
                    deleteOptionsVBox.setVisible(true);
                    deleteText.setText("Delete " + trickTableView.getSelectionModel().getSelectedItem().getTrickName() + "? \nThis action cannot be undone.");
                }
            }
        });
    }
    ObservableList<Trick> observableList1 = FXCollections.observableArrayList(databaseManager.getAllTricks());
    public void onDeleteNoButtonClick(){
        deleteOptionsVBox.setVisible(false);
    }
    public void onDeleteYesButtonClick(){
        databaseManager.deleteTrick(trickTableView.getSelectionModel().getSelectedItem());
        trickTableView.getItems().remove(trickTableView.getSelectionModel().getSelectedItem());
        deleteOptionsVBox.setVisible(false);
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
