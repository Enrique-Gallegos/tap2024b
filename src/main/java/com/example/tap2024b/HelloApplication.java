package com.example.tap2024b;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Button btn1, btn2, btn3;

    private VBox vbox;

    private HBox hbox;

    private BorderPane pane = new BorderPane();

    public void CrearUI(){
        btn1 = new Button("Boton 1");
        btn2 = new Button("Boton 2");
        btn3 = new Button("Boton 3");
        vbox = new VBox(btn1, btn2, btn3);
        hbox = new HBox(btn1, btn2, btn3);
        pane.setBottom(hbox);
    }

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        CrearUI();
        Scene scene = new Scene(pane, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}