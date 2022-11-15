module com.example.ekbcpkiomayakovskogo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.velvethills to javafx.fxml;
    exports com.example.velvethills;
    exports com.example.velvethills.Models;
}