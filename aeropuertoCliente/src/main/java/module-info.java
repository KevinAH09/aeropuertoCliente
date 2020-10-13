module org.una.aeropuertocliente {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires com.google.gson;
    requires java.base;
    
    opens org.una.aeropuertocliente.controllers to javafx.fxml;
    opens org.una.aeropuertocliente to javafx.fxml;
    exports org.una.aeropuertocliente.controllers;
    exports org.una.aeropuertocliente;
    
    
}
