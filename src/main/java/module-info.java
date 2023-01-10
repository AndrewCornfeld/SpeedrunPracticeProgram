module com.example.speedrunpracticeprogram {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;


    opens com.example.speedrunpracticeprogram.main to javafx.fxml;
    exports com.example.speedrunpracticeprogram;
}