package com.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Set;

public class Calculadora extends Stage {

    private static final Set<String> Operaciones = Set.of("+", "-", "*", "/");
    private Button[][] arBtns;
    private TextField txtPantalla;
    private GridPane gdpTeclado;
    private VBox vBox;
    private Scene escena;
    String[] strTeclas = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "-", ".", "0", "=", "+"};

    private float x1, x2, r;
    private String op;
    private Button btnClear;
    private boolean operacionTerminada;
    private static final String ERROR_PREFIX = "Error";

    private void CrearUI() {

        arBtns = new Button[4][4];
        txtPantalla = new TextField("");
        txtPantalla.setAlignment(Pos.CENTER_RIGHT);
        txtPantalla.setEditable(false);
        gdpTeclado = new GridPane();
        CrearTeclado();
        btnClear = new Button("Clear");
        btnClear.setId("font-button");

        arBtns[2][3].setOnAction(actionEvent -> {
            if (op == null || txtPantalla.getText().isEmpty()) {
                txtPantalla.setText("Error: Falta número u operación");
                return;
            }

            if (!esNumero(txtPantalla.getText())) {
                txtPantalla.setText("Error: Número inválido");
                return;
            }

            x2 = Float.parseFloat(txtPantalla.getText());

            if (op.equals("/") && x2 == 0) {
                txtPantalla.setText("Error: Division entre 0");
                return;
            }

            switch (op) {
                case "+":
                    r = x1 + x2;
                    break;
                case "-":
                    r = x1 - x2;
                    break;
                case "*":
                    r = x1 * x2;
                    break;
                case "/":
                    r = x1 / x2;
                    break;
            }

            txtPantalla.setText(String.valueOf(r));
            operacionTerminada = true;
        });

        // Acción para el botón "Clear"
        btnClear.setOnAction(actionEvent -> borrarPantalla());

        vBox = new VBox(txtPantalla, gdpTeclado, btnClear);
        vBox.setSpacing(5);
        escena = new Scene(vBox, 200, 270);
        escena.getStylesheets().add(new File("C:\\Users\\Admin\\IdeaProjects\\TAP2024b\\src\\main\\resources\\Styles\\Calc.css").toURI().toString());
    }

    private void CrearTeclado() {
        for (int i = 0; i < arBtns.length; i++) {
            for (int j = 0; j < arBtns[0].length; j++) {
                arBtns[j][i] = new Button(strTeclas[4 * i + j]);
                arBtns[j][i].setPrefSize(50, 50);
                int finali = i, finalj = j;
                arBtns[j][i].setOnAction(event -> detectarTecla(strTeclas[4 * finali + finalj]));
                gdpTeclado.add(arBtns[j][i], j, i);
            }
        }
    }

    public Calculadora() {
        CrearUI();
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
        operacionTerminada = false;
    }

    private void borrarPantalla() {
        txtPantalla.clear();
        x1 = 0;
        x2 = 0;
        r = 0;
        op = null;
        operacionTerminada = false;
    }

    private void detectarTecla(String tecla) {
        if (txtPantalla.getText().startsWith(ERROR_PREFIX)) {
            txtPantalla.clear();
        }

        if (Operaciones.contains(tecla)) {
            if (!esNumero(txtPantalla.getText())) {
                txtPantalla.setText("Error: Número inválido");
                return;
            }
            op = tecla;
            x1 = Float.parseFloat(txtPantalla.getText());
            txtPantalla.setText("");
        } else {
            if (operacionTerminada && !Operaciones.contains(tecla)) {
                txtPantalla.clear();
                operacionTerminada = false;
            }
            txtPantalla.appendText(tecla);
        }
    }

    private boolean esNumero(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
