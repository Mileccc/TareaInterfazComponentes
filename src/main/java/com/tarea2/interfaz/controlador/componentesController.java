package com.tarea2.interfaz.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.tarea2.interfaz.modelo.User;
import javafx.scene.control.Alert;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Node;

public class componentesController {
    // Vincula componentes de la interfaz gráfica con el controlador
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private TextField usernameInput; // Campo de texto para el nombre del usuario
    @FXML
    private Button greetButton; // Botón para saludar
    @FXML
    private Label greetingLabel; // Etiqueta para mostrar saludos
    @FXML
    private Button addUserButton; // Botón para agregar usuarios
    @FXML
    private Button deleteUserButton; // Botón para eliminar usuarios
    @FXML
    private TableView<User> TableViewdeListas; // Tabla para mostrar usuarios
    @FXML
    private TableColumn<User, String> firstNameColumn; // Columna para el nombre
    @FXML
    private TableColumn<User, String> lastNameColumn; // Columna para el apellido
    // Lista para manejar usuarios
    ObservableList<User> listaUsuarios = FXCollections.observableArrayList();
    // Etiqueta para mostrar cuando la tabla está vacía
    private Label textPredTablaVacia;

    @FXML
    private HBox titleBar; // Barra de título para la ventana
    private double xOffset = 0; // Desplazamiento en X para arrastrar la ventana
    private double yOffset = 0; // Desplazamiento en Y para arrastrar la ventana

    // Cambia el saludo basado en el nombre de usuario ingresado
    @FXML
    public void cambiarLabel(ActionEvent event) {
        greetingLabel.setText("Hola " + usernameInput.getText());
    }

    @FXML
    private void initialize() {
        // Configura la barra de título para ser arrastrable
        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        titleBar.setOnMouseDragged(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        // Configura las columnas de la tabla para los usuarios
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableViewdeListas.setItems(listaUsuarios);

        // Configura el mensaje para una tabla vacía
        configurarPlaceholderTablaVacia();
    }

    @FXML
    public void agregarNombre(ActionEvent event) {
        // Divide el texto ingresado en el campo de texto usernameInput por espacios
        String[] nombreCompleto = usernameInput.getText().split(" ");

        if (nombreCompleto.length <= 1) {
            // Caso cuando se ingresa menos de dos palabras: muestra una alerta
            Alert alertaInformacion = new Alert(AlertType.INFORMATION);
            alertaInformacion.setTitle("Información");
            alertaInformacion.setHeaderText(null);
            alertaInformacion.setContentText("Por favor debes introducir el nombre y el primer apellido");
            alertaInformacion.showAndWait();
        } else if (nombreCompleto.length == 2) {
            // Caso cuando se ingresan exactamente dos palabras: crea y añade un nuevo
            // usuario
            User newUser = new User(nombreCompleto[0], nombreCompleto[1]);
            listaUsuarios.add(newUser); // Añade el nuevo usuario a la lista observable
            System.out.println("Usuario añadido."); // Mensaje en consola para depuración
        } else {
            // Caso cuando se ingresan más de dos palabras: muestra una alerta
            Alert alertaInformacion = new Alert(AlertType.INFORMATION);
            alertaInformacion.setTitle("Información");
            alertaInformacion.setHeaderText(null);
            alertaInformacion.setContentText("Por favor introduce solo el nombre y el primer apellido");
            alertaInformacion.showAndWait();
        }
    }

    @FXML
    public void eliminarNombre(ActionEvent event) {
        // Divide el texto del campo usernameInput en nombre y apellido
        String[] nombreCompleto = usernameInput.getText().split(" ");

        if (nombreCompleto.length >= 2) {
            // Obtiene nombre y apellido del texto dividido
            String firstName = nombreCompleto[0];
            String lastName = nombreCompleto[1];

            // Crea un diálogo de confirmación para la eliminación del usuario
            Alert alerta = new Alert(AlertType.CONFIRMATION);
            alerta.setTitle("Confirmación de Eliminación");
            alerta.setHeaderText("Eliminar Usuario");
            alerta.setContentText("¿Estás seguro de que quieres eliminar a " + firstName + " " + lastName + "?");

            // Muestra el diálogo y espera la respuesta del usuario
            Optional<ButtonType> resultado = alerta.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                // Usuario confirmó la eliminación
                User userAEliminar = null;
                // Busca al usuario en la lista por nombre y apellido
                for (User user : listaUsuarios) {
                    if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)) {
                        userAEliminar = user;
                        break;
                    }
                }
                if (userAEliminar != null) {
                    // Elimina al usuario encontrado de la lista
                    listaUsuarios.remove(userAEliminar);
                } else {
                    // Informa al usuario si no se encuentra el nombre en la lista
                    Alert alertaInformacion = new Alert(AlertType.INFORMATION);
                    alertaInformacion.setTitle("Información");
                    alertaInformacion.setHeaderText(null);
                    alertaInformacion
                            .setContentText("El usuario " + firstName + " " + lastName + " no existe en la lista.");
                    alertaInformacion.showAndWait();
                }
            }
        }
    }

    private void configurarPlaceholderTablaVacia() {
        // Crea una nueva etiqueta para mostrar cuando la tabla está vacía
        textPredTablaVacia = new Label("Sin usuarios actualmente");
        // Agrega una clase CSS para aplicar estilos específicos al placeholder
        textPredTablaVacia.getStyleClass().add("tabla-vacia-placeholder");
        // Establece el placeholder en la tabla de usuarios
        TableViewdeListas.setPlaceholder(textPredTablaVacia);
    }
    
    public void minimizeWindow(ActionEvent event) {
        // Minimiza la ventana cuando se hace clic en el botón correspondiente
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
    }
    
    public void maximizeWindow(ActionEvent event) {
        // Obtiene la ventana actual y alterna su estado entre maximizado y tamaño normal
        Stage stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }
    
    public void closeWindow(ActionEvent event) {
        // Cierra la ventana cuando se hace clic en el botón de cerrar
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }
    
}
