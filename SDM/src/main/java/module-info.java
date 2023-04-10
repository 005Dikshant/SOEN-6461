module com.example.sdm {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sdm to javafx.fxml;
    exports com.example.sdm;
}