package com.example.speedrunpracticeprogram;

import  javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("opening scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Speedrun Practice Program");
        stage.setScene(scene);
        stage.show();
        primaryStage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource(fxml)));
        primaryStage.getScene().setRoot(pane);
//        if(fxml.equals("on trick scene.fxml")){
//            OpeningSceneController openingSceneController = new OpeningSceneController();
//        }
    }
}
