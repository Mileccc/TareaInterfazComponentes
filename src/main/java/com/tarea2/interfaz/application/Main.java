package com.tarea2.interfaz.application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Carga la interfaz de usuario desde un archivo FXML
            Parent root = FXMLLoader.load(getClass().getResource("/com/tarea2/interfaz/vista/componentes.fxml"));

            // Crea una nueva escena utilizando la interfaz de usuario cargada
            Scene scene = new Scene(root);
            // Hace el fondo de la escena transparente.
            scene.setFill(Color.TRANSPARENT);
            // Aplica estilo transparente al marco de la ventana.
            primaryStage.initStyle(StageStyle.TRANSPARENT);

            // Agrega una hoja de estilos CSS para personalizar la interfaz
            scene.getStylesheets().add(getClass().getResource("/com/tarea2/interfaz/vista/style.css").toExternalForm());

            // Establece el título de la ventana principal
            primaryStage.setTitle("Mi primer proyecto JavaFX");

            // Configura y muestra la ventana principal con la escena cargada
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            // Manejo de excepciones en caso de errores al cargar el FXML
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Inicia la aplicación JavaFX
        launch(args);
    }
}

