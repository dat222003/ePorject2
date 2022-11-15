module test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires mysql.connector.java;

    opens login to javafx.fxml;
    exports login;
    opens home to javafx.fxml;
    exports home;
}