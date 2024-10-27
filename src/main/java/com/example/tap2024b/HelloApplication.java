package com.example.tap2024b;

import com.example.tap2024b.components.CorredorThread;
import com.example.tap2024b.models.Conexion;
import com.example.tap2024b.vistas.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.Random;


import java.io.IOException;

import static com.example.tap2024b.models.Conexion.conexion;

public class HelloApplication extends Application {

    private BorderPane bdpPrincipal;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1,menCompetencia2,menSalir;
    private MenuItem mitCalculadora,mitLoteria,mitSpotify,mitBuscaminas,mitPista;

    public void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(event -> new Calculadora());
        mitLoteria = new MenuItem("Loteria");
        mitLoteria.setOnAction(event -> new Loteria());
        mitSpotify = new MenuItem("Spotify");
        mitSpotify.setOnAction(actionEvent -> new Spotify());
        mitBuscaminas = new MenuItem("Buscaminas");
        mitBuscaminas.setOnAction(actionEvent -> new Buscaminas());
        mitPista = new MenuItem("Pista Hilos");
        mitPista.setOnAction(ActionEvent-> new Pista());


        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora,mitLoteria,mitSpotify,mitBuscaminas);

        menCompetencia2 = new Menu("Competencia 2");
        menCompetencia2.getItems().addAll(mitPista);

        mnbPrincipal = new MenuBar(menCompetencia1, menCompetencia2);
        bdpPrincipal = new BorderPane();
        bdpPrincipal.setTop(mnbPrincipal);

    }

    @Override
    public void start(Stage stage) throws IOException {
        CrearUI();
        Scene scene = new Scene(bdpPrincipal, 320, 240);
        //scene.getStylesheets().add(getClass().getResource("/styles/main.css").toString());
        //scene.getStylesheets().add(getClass().getResource("/Styles/main.css").toExternalForm());

        scene.getStylesheets().add(new File("C:\\Users\\Admin\\IdeaProjects\\TAP2024b\\src\\main\\resources\\Styles\\main.css").toURI().toString());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        Conexion.crearConexion();
    }

    public static void main(String[] args) {
        launch();
    }
}