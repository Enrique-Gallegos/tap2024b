package com.example.tap2024b.vistas;

import com.example.tap2024b.vistas.IngresoDatosCliente;
import com.example.tap2024b.vistas.Spotify;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Stage {
    private Button btnAdmin, btnCliente;
    private Label lblMensaje;
    private VBox vbox;
    private Scene escena;

    public Login() {
        crearUI();
        this.setTitle("Bienvenido - Spotify");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        lblMensaje = new Label("Selecciona tu rol para continuar:");

        btnAdmin = new Button("Administrador");
        btnAdmin.setOnAction(event -> abrirInterfazAdmin());

        btnCliente = new Button("Cliente");
        btnCliente.setOnAction(event -> abrirInterfazCliente());

        vbox = new VBox(15, lblMensaje, btnAdmin, btnCliente);
        escena = new Scene(vbox, 300, 200);
    }

    private void abrirInterfazAdmin() {
        new Spotify();
        this.close();
    }

    private void abrirInterfazCliente() {
        new IngresoDatosCliente();
        this.close();
    }
}