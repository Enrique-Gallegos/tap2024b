package com.example.tap2024b.vistas;

import com.example.tap2024b.components.CorredorThread;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Pista extends Stage {

    private String[] strCorredores = {"Kike","Hugo","Ruben","Roberto","Ximena"};
    private GridPane gdpPista;
    private ProgressBar[] pgbCarriles;
    private Button btnIniciar;
    private Scene escena;
    private Label[] lblCorredores;
    private CorredorThread[] thrCorredores;
    private VBox vbox;


    public Pista(){
        CrearUI();
        this.setTitle("Pista de Atletismo");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        gdpPista = new GridPane();
        lblCorredores = new Label[5];
        pgbCarriles = new ProgressBar[5];

        for (int i = 0;  5> i;i++){
            lblCorredores[i] = new Label(strCorredores[i]);
            gdpPista.add(lblCorredores[i], 0, i);
            pgbCarriles[i] = new ProgressBar(0);
            gdpPista.add(pgbCarriles[i], 1, i);


        }

        btnIniciar = new Button("Iniciar carrera");
        btnIniciar.setOnAction(ActionEvent -> Iniciar());
        vbox = new VBox(gdpPista,btnIniciar);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        escena=new Scene(vbox,200,200);
    }

    private void Iniciar() {
        thrCorredores = new CorredorThread[5];
        for (int i = 0;  5> i;i++){
            thrCorredores[i] = new CorredorThread(strCorredores[i],pgbCarriles[i]);
            thrCorredores[i].start();
        }
    }


}

