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
    requires com.jfoenix;

    opens home to javafx.fxml;
    exports home;
    opens login to javafx.fxml;
    exports login;
    opens tab to javafx.fxml;
    exports tab;
    opens employee to javafx.fxml;
    exports employee;
    opens table to javafx.fxml;
    exports table;
}