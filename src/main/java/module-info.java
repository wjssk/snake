module com.example.check {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;

    opens com.example.check to javafx.fxml;
    exports com.example.check;
}