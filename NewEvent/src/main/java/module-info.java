module com.newevent {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.jfoenix;
    requires mysql.connector.java;


    opens com.newevent to javafx.fxml;
    exports com.newevent;
}