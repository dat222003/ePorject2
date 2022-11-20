module eProject2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    // add icon pack modules
//    requires org.kordamp.ikonli.fontawesome;
    requires mysql.connector.j;

    opens home to javafx.fxml;
    exports home;
    opens login to javafx.fxml;
    exports login;
}