package com.example.speedrunpracticeprogram;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

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
    private DatabaseManager databaseManager = new DatabaseManager();
    public void initialize(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("trickName"));
        timesAttemptedColumn.setCellValueFactory(new PropertyValueFactory<>("timesAttempted"));
        ArrayList<Trick> trickArrayList = databaseManager.getAllTricks();
        //trickTableView.getColumns().addAll(nameColumn, timesAttemptedColumn, streakColumn);
        ObservableList<Trick> observableList = FXCollections.observableArrayList(
                new Trick("asdfs", 1, 1)
        );

        for (Trick trick: observableList
             ) {
            System.out.println(trick);
        }
        trickTableView.setItems(observableList1);
    }
    //public ObservableList<Trick> getTricksList(){
        //return tricksList;
    //}
    ObservableList<Trick> observableList1 = FXCollections.observableArrayList(databaseManager.getAllTricks());
}
