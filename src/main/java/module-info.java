module com.example.tap2024b {
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires java.desktop;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.controls;
    opens com.example.tap2024b to javafx.fxml;
    exports com.example.tap2024b;

    opens com.example.tap2024b.models;
}