module com.example.speedrunpracticeprogram {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.speedrunpracticeprogram to javafx.fxml;
    exports com.example.speedrunpracticeprogram;
}