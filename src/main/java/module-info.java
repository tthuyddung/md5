module com.example.doan {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;
    requires java.sql;
    requires jdk.jdi;

    opens com.example.doan to javafx.fxml;
    exports com.example.doan;
}