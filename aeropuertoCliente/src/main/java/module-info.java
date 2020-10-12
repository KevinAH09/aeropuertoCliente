module org.una.aeropuertocliente {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.una.aeropuertocliente to javafx.fxml;
    exports org.una.aeropuertocliente;
}
