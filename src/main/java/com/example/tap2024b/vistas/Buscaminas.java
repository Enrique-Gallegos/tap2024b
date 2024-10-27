package com.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import javafx.scene.control.ButtonType;
import javafx.application.Platform;

public class Buscaminas extends Stage {

    private Scene escena;
    private GridPane mapa;
    private VBox vbox;
    private HBox hbox_info, hbox_txt;
    private Label lbl_bombas, lbl_banderas, lbl_info, lbl_error;
    private TextField Txt_Bombas;
    private Button iniciar;
    private Button[][] mapaB;
    private boolean[][] bombasMapa;
    private int bandera = 0;
    private int bombas = 0;
    Set<String> coordenadasUsadas;
    Random random;
    private final ButtonType Reintentar = new ButtonType("Reintentar");
    private final ButtonType Salir = new ButtonType("Salir");
    private String path = new String("C:\\Users\\Admin\\IdeaProjects\\TAP2024b\\src\\main\\resources\\image\\");


    public Buscaminas() {
        CrearUI();
        this.setTitle("Buscaminas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        lbl_info = new Label("La cuadricula tiene un tamaño de 8x8, siendo un total de 64 casillas.");
        lbl_info.setId("lbl_info");

        lbl_error = new Label();
        lbl_error.setId("lbl_error");

        Txt_Bombas = new TextField();
        Txt_Bombas.setPromptText("Bombas a colocar");
        Txt_Bombas.setId("Txt_Bombas");

        iniciar = new Button("Iniciar");
        iniciar.setOnAction(actionEvent -> init());
        iniciar.setId("btn_iniciar");

        hbox_txt = new HBox(Txt_Bombas, iniciar);
        hbox_txt.setSpacing(20);
        hbox_txt.setId("hbox_txt");
        hbox_txt.setAlignment(Pos.CENTER);

        vbox = new VBox(lbl_info, hbox_txt,lbl_error);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(20);
        escena = new Scene(vbox);
        escena.getStylesheets().add(new File("C:\\Users\\Admin\\IdeaProjects\\TAP2024b\\src\\main\\resources\\Styles\\Buscaminas.css").toURI().toString());
    }
    private void init()
    {
        String textoBombas = Txt_Bombas.getText();
        lbl_error.setText("");

        try {
            bombas = Integer.parseInt(textoBombas);

            if (bombas <= 0 || bombas > 64) {
                lbl_error.setText("Ingrese un número entre 1 y 64.");
                return;
            }
        } catch (NumberFormatException e) {
            lbl_error.setText("Ingrese un número válido");
            return;
        }

        mapa = new GridPane();
        mapa.setAlignment(Pos.CENTER);
        mapaB = new Button[8][8];
        bombasMapa = new boolean[8][8];
        CrearMapa();
        bombas = Integer.parseInt(Txt_Bombas.getText());

        lbl_banderas = new Label("Banderas: " + bandera);
        lbl_banderas.setId("lbl_banderas");

        lbl_bombas = new Label("Bombas: " + bombas);
        lbl_bombas.setId("lbl_bombas");

        hbox_info = new HBox(lbl_banderas, lbl_bombas);
        hbox_info.setSpacing(20);
        hbox_info.setId("hbox_info");

        vbox.getChildren().clear();
        vbox.getChildren().addAll(lbl_info, hbox_info, mapa);
        vbox.setAlignment(Pos.CENTER);

        ColocarBombas(bombas);
        this.sizeToScene();
    }

    private void CrearMapa() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mapaB[i][j] = new Button();
                mapaB[i][j].setPrefSize(60, 60);
                cambiar(mapaB[i][j], i, j);
                mapa.add(mapaB[i][j], j, i);
            }
        }
    }

    private void cambiar(Button B, int x, int y) {
        //Image bander = new Image(getClass().getResource("/image/bandera.jpg").toString());
        ImageView imvBan = new ImageView(new Image(path+"bandera.jpg"));
        imvBan.setFitWidth(30);
        imvBan.setFitHeight(30);

       // Image mina = new Image(getClass().getResource("/image/mina.jpg").toString());
        ImageView imvMina =  new ImageView(new Image(path+"mina.jpg"));
        imvMina.setFitWidth(30);
        imvMina.setFitHeight(30);

        B.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (bombasMapa[x][y]) {
                    B.setGraphic(imvMina);

                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("¡Has perdido!");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Has hecho clic en una bomba. ¿Qué deseas hacer?");

                    alerta.getButtonTypes().setAll(Reintentar, Salir);
                    alerta.showAndWait().ifPresent(response -> {
                        if (response == Reintentar) {
                            reiniciarJuego();
                        } else if (response == Salir) {
                            Platform.exit();
                        }
                    });

                    revelarBombas();
                } else {
                    int bombasAlrededor = contarBombas(x, y);
                    B.setText(String.valueOf(bombasAlrededor));
                    verificarVictoria();
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                if (B.getGraphic() == null && Objects.equals(B.getText(), "")) {
                    B.setGraphic(imvBan);
                    bandera++;
                    lbl_banderas.setText("Banderas: " + bandera);
                } else if (B.getGraphic() == imvBan && Objects.equals(B.getText(), "")) {
                    B.setGraphic(null);
                    bandera--;
                    lbl_banderas.setText("Banderas: " + bandera);
                }
            }
        });
    }

    private int contarBombas(int x, int y) {
        int bombasAlrededor = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                int filaVecina = x + i;
                int columnaVecina = y + j;

                if (filaVecina >= 0 && filaVecina < 8 && columnaVecina >= 0 && columnaVecina < 8) {
                    if (bombasMapa[filaVecina][columnaVecina]) {
                        bombasAlrededor++;
                    }
                }
            }
        }

        return bombasAlrededor;
    }

    private void ColocarBombas(int cantidadBombas) {
        coordenadasUsadas = new HashSet<>();
        random = new Random();

        int bombasColocadas = 0;
        while (bombasColocadas < cantidadBombas) {
            int fila = random.nextInt(8);
            int columna = random.nextInt(8);

            String coordenada = fila + "," + columna;

            if (!coordenadasUsadas.contains(coordenada)) {
                bombasMapa[fila][columna] = true;

                coordenadasUsadas.add(coordenada);
                bombasColocadas++;
            }
        }
    }

    private void revelarBombas() {
        Image mina = new Image(path+"mina.jpg");
        ImageView imvMina;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (bombasMapa[i][j]) {
                    imvMina = new ImageView(mina);
                    imvMina.setFitWidth(30);
                    imvMina.setFitHeight(30);
                    mapaB[i][j].setGraphic(imvMina);
                }
            }
        }
    }

    private void reiniciarJuego() {
        lbl_error.setText("");
        bandera = 0;
        bombas = 0;
        vbox.getChildren().clear();
        Txt_Bombas.clear();
        vbox.getChildren().addAll(lbl_info, hbox_txt);
        this.sizeToScene();
    }

    private void verificarVictoria() {

        boolean victoria = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!bombasMapa[i][j] && Objects.equals(mapaB[i][j].getText(), "")) {
                    victoria = false;
                    break;
                }
            }
            if (!victoria) break;
        }

        if (victoria) {
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("¡Felicidades!");
            alerta.setHeaderText(null);
            alerta.setContentText("¡Ganaste! ¿Quieres volver a jugar?");

            alerta.getButtonTypes().setAll(Reintentar, Salir);
            alerta.showAndWait().ifPresent(response -> {
                if (response == Reintentar) {
                    reiniciarJuego();
                } else if (response == Salir) {
                    Platform.exit();
                }
            });
        }
    }

}