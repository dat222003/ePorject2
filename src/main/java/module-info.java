module eProject2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires mysql.connector.j;
    requires com.jfoenix;

    opens home to javafx.fxml;
    exports home;
    opens login to javafx.fxml;
    exports login;
    opens tab to javafx.fxml;
    exports tab;
    opens table to javafx.fxml;
    exports table;
    opens model to javafx.fxml;
    exports model;
}