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
    public final ObservableList<Trick> tricksList = FXCollections.observableArrayList();
    @FXML
    public TableView trickTableView;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn timesAttemptedColumn;
    private DatabaseManager databaseManager = new DatabaseManager();
    public void initialize(){
        //nameColumn.setCellFactory();
        trickTableView = new TableView();
        nameColumn = new TableColumn("Trick Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("TrickName"));

        timesAttemptedColumn = new TableColumn("Times Attempted");
        timesAttemptedColumn.setCellValueFactory(new PropertyValueFactory<>("TimesAttempted"));
        ArrayList<Trick> trickArrayList = databaseManager.getAllTricks();
        trickTableView.getColumns().addAll(nameColumn, timesAttemptedColumn);
        //tricksList.addAll(databaseManager.getAllTricks());
        ObservableList<Trick> observableList = FXCollections.observableArrayList(trickArrayList);
        for (Trick trick: observableList
             ) {
            System.out.println(trick);
        }
        //trickTableView.getItems().clear();
        //Trick testTrick = new Trick("sugma skip", 69);
        //trickTableView.getItems().add(testTrick);
        trickTableView.setItems(observableList);
    }
    //public ObservableList<Trick> getTricksList(){
        //return tricksList;
    //}
}
