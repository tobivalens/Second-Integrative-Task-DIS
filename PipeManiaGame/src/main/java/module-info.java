module com.example.pipemaniagame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pipemaniagame to javafx.fxml;
    exports com.example.pipemaniagame;
    exports com.example.pipemaniagame.control;
    opens com.example.pipemaniagame.control to javafx.fxml;
    exports com.example.pipemaniagame.model;
}