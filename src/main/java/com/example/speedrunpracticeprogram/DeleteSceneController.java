package com.example.speedrunpracticeprogram;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class DeleteSceneController {
    public final ObservableList<Trick> tricksList = FXCollections.observableArrayList();
    @FXML
    public TableView<Trick> trickTableView;
    @FXML
    private TableColumn<Trick, String> nameColumn;
    @FXML
    private TableColumn<Trick, Integer> timesAttemptedColumn;
    private DatabaseManager databaseManager = new DatabaseManager();
    public void initialize(){
        //nameColumn.setCellFactory();
        trickTableView = new TableView<Trick>();
        tricksList.addAll(databaseManager.getAllTricks());
        ObservableList<Trick> observableList = FXCollections.observableList(databaseManager.getAllTricks());
        trickTableView.getItems().clear();
        trickTableView.getItems().addAll(observableList);
    }
    public ObservableList<Trick> getTricksList(){
        return tricksList;
    }
}
