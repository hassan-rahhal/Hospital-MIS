module com.example.finalproject {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires javafx.base;
    requires jbcrypt;


    opens com.example.finalproject to javafx.graphics, javafx.fxml;
    exports com.example.finalproject;
}