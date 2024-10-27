module com.example.tap2024b {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    opens com.example.tap2024b to javafx.fxml;
    exports com.example.tap2024b;

    opens com.example.tap2024b.models;
}