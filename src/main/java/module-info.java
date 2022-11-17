module com.example.velvethills {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires itextpdf;
    //requires javafx.swing;


    opens com.example.velvethills to javafx.fxml;
    exports com.example.velvethills;
    exports com.example.velvethills.Models;
    exports com.example.velvethills.Utils;
    exports com.example.velvethills.Utils.captcha;
}