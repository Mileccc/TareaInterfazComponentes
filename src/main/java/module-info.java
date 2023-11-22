module com.tarea2.interfaz {
    // Requiere javafx.controls y javafx.fxml para la interfaz gráfica y FXML.
    requires transitive javafx.controls;
    requires javafx.fxml;

    // Hace accesibles los paquetes controlador, modelo y aplicación a javafx.fxml.
    opens com.tarea2.interfaz.controlador;
    exports com.tarea2.interfaz.controlador to javafx.fxml;
    opens com.tarea2.interfaz.modelo;
    exports com.tarea2.interfaz.modelo to javafx.fxml;
    opens com.tarea2.interfaz.application to javafx.fxml;
    exports com.tarea2.interfaz.application;
}

