module com.example.cryptogui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.cryptogui;  // Export your main package
    exports com.example.cryptogui.controller; // Export the controller package to javafx.fxml

    opens com.example.cryptogui to javafx.fxml;
    opens com.example.cryptogui.controller to javafx.fxml;  // Open the controller package to javafx.fxml
}