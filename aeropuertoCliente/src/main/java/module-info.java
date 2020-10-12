module org.una.aeropuertocliente {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.base;
    opens org.una.aeropuertocliente to javafx.fxml;
    exports org.una.aeropuertocliente;
}
