package com.example.tap2024b.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Loteria extends Stage {

    private Timeline timeline;
    private HBox hboxMain, hboxButtons;
    private VBox vboxTablilla, vboxMazo;
    private GridPane gdpTabla;
    private Button Anterior, Siguiente, Inic_Det;
    private Scene escena,res;
    private List<Integer> numerosDisponibles, Mazo;
    private Button[][] arBtnTab;
    private Boolean[][] Matriz_B;
    private ImageView Baraja;
    private Label Cont,G_P;

    public Loteria() {
        inicializarNumeros();
        CrearUI();
        this.setTitle("Loteria Mexicana");
        this.setScene(escena);
        this.show();
        /**System.out.println(numerosDisponibles);
        System.out.println(Mazo);**/
    }

    private void CrearUI() {
        ImageView imv_Ant, imv_Sig, tapa;
        imv_Ant = new ImageView(new Image(getClass().getResource("/image/Izquierda.png").toString()));
        imv_Sig = new ImageView(new Image(getClass().getResource("/image/Derecha.png").toString()));
        gdpTabla = new GridPane();
        CrearTablilla();
        Cont = new Label("Contador");
        Baraja = new ImageView(new Image(getClass().getResource("/image/dorso.jpeg").toString()));
        Baraja.setFitHeight(500);
        Baraja.setFitWidth(350);
        Inic_Det = new Button("Iniciar");

        Cont.setMaxSize(400, 1000);
        Inic_Det.setPrefSize(250, 100);
        Inic_Det.setId("font-inic");

        Cont.setId("font-lbl");

        vboxMazo = new VBox(Cont, Baraja, Inic_Det);

        vboxMazo.setAlignment(Pos.CENTER);

        Anterior = new Button();
        Anterior.setGraphic(imv_Ant);
        Siguiente = new Button();
        Siguiente.setGraphic(imv_Sig);
        Anterior.setId("font-button");
        Siguiente.setId("font-button");

        Inic_Det.setOnAction(actionEvent -> InitDet_cont());
        hboxButtons = new HBox(Anterior, Siguiente);
        hboxButtons.setSpacing(176);
        vboxTablilla = new VBox(gdpTabla, hboxButtons);

        hboxMain = new HBox(vboxTablilla, vboxMazo);
        hboxMain.setSpacing(300);
        hboxMain.setPadding(new Insets(20));
        escena = new Scene(hboxMain, 1200, 700);
        escena.getStylesheets().add(getClass().getResource("/styles/Loteria.css").toString());
    }

    private void CrearTablilla() {
        arBtnTab = new Button[4][4];
        Matriz_B = new Boolean[4][4];
        Image img;
        ImageView imv;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Matriz_B[i][j] = false;

                int numeroAleatorio = obtenerNumeroAleatorio();
                String imagePath = "/image/imagen" + numeroAleatorio + ".jpg";
                img = new Image(getClass().getResource(imagePath).toString());


                imv = new ImageView(img);
                imv.setFitWidth(100);
                imv.setFitHeight(130);

                arBtnTab[i][j] = new Button();
                arBtnTab[i][j].setGraphic(imv);
                final int x = i;
                final int y = j;
                arBtnTab[i][j].setOnAction(actionEvent -> Marcar(x, y));
                gdpTabla.add(arBtnTab[i][j], j, i);
            }
        }
    }


    private void inicializarNumeros() {
        numerosDisponibles = new ArrayList<>();
        Mazo = new ArrayList<>();
        for (int i = 1; i <= 54; i++) {
            numerosDisponibles.add(i);
            Mazo.add(i);
        }
        Collections.shuffle(numerosDisponibles);
        Collections.shuffle(Mazo);
    }

    private int obtenerNumeroAleatorio() {
        return numerosDisponibles.remove(0);
    }

    private void InitDet_cont() {
        switch (Inic_Det.getText()) {
            case "Iniciar":
                Inic_Det.setText("Detener");

                timeline = new Timeline(new KeyFrame(Duration.seconds(.01), event -> actualizarContador()));
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
                break;

            case "Detener":
                Inic_Det.setText("Iniciar");
                if (timeline != null) {
                    timeline.stop();
                }
                break;

            case "Resultado":
            G_P = new Label("");
            G_P.setId("font-G_P");

            boolean gano = true;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (!Matriz_B[i][j]) {
                        gano = false;
                        break;
                    }
                }
                if (!gano) break;
            }

            if (gano) {
                G_P.setText("¡Ganaste!");
            } else {
                G_P.setText("¡Perdiste! Debes prestar más atención.");
            }
            Stage re = new Stage();
            res=new Scene(G_P);
            re.setTitle("Resultado");
            re.setScene(res);
            re.show();

            break;

            default:
                break;
        }
    }


    private void Marcar(int x, int y) {
        Image hechoImg = new Image(getClass().getResource("/image/Hecho.png").toString());
        ImageView imvHecho = new ImageView(hechoImg);
        imvHecho.setFitWidth(100);
        imvHecho.setFitHeight(130);
        arBtnTab[x][y].setGraphic(imvHecho);
        Matriz_B[x][y] = true;
    }

    private void actualizarContador() {
        if(Mazo.isEmpty()) {
            switch (Cont.getText()) {
                case "00:05":
                case "00:04":
                case "00:03":
                case "00:02":
                case "00:01":
                    Cont.setText("00:00");
                    break;
                case "00:00":
                    Image nuevaImagen = new Image(getClass().getResource("/image/Gracias.png").toString());
                    Baraja.setImage(nuevaImagen);
                    timeline.stop();
                    Inic_Det.setText("Resultado");
                    break;
                default:
                    break;
        }
        }
        else{
            switch (Cont.getText()) {
                case "Contador", "00:01":
                    Cont.setText("00:05");
                    Cambiar_carta();
                    break;
                case "00:05":
                    Cont.setText("00:04");
                    break;
                case "00:04":
                    Cont.setText("00:03");
                    break;
                case "00:03":
                    Cont.setText("00:02");
                    break;
                case "00:02":
                    Cont.setText("00:01");
                    break;
                default:
                    break;
            }
        }
    }

    private void Cambiar_carta(){
        if (!Mazo.isEmpty()) {
            int numeroCarta = Mazo.remove(0);
            String imagePath = "/image/imagen" + numeroCarta + ".jpg";
            Image nuevaImagen = new Image(getClass().getResource(imagePath).toString());
            Baraja.setImage(nuevaImagen);
        }
        else{
            Image nuevaImagen = new Image(getClass().getResource("/image/Gracias.png").toString());
            Baraja.setImage(nuevaImagen);
            Cont.setText("00:00");
            Inic_Det.setText("Resultado");
        }

    }
}