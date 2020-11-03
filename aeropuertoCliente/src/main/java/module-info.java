module org.una.aeropuertocliente {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires com.google.gson;
    requires java.base;
    requires jasperreports;
    requires java.xml;
    requires javax.interceptor.api;
    requires javax.inject;
    requires org.apache.logging.log4j;
    
    opens org.una.aeropuertocliente.controllers to javafx.fxml;
    opens org.una.aeropuertocliente to javafx.fxml;
    exports org.una.aeropuertocliente.controllers;
    exports org.una.aeropuertocliente;
    
    
}
