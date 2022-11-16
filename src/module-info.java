module eProject2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires mysql.connector.java;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;

    opens home to javafx.fxml;
    exports home;
    opens login to javafx.fxml;
    exports login;
}