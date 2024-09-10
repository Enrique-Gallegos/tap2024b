package com.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
            try {
                if (txtPantalla.getText().isEmpty()) {
                    mostrarAlertError("No se ha ingresado un número.");
                    return;
                }

                x2 = Float.parseFloat(txtPantalla.getText());

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
                        if (x2 == 0) {
                            throw new ArithmeticException("División por cero");
                        }
                        r = x1 / x2;
                        break;
                    default:
                        break;
                }

                txtPantalla.setText(String.valueOf(r));
                operacionTerminada = true;
            } catch (NumberFormatException e) {
                mostrarAlertError("Formato de número no válido.");
            } catch (ArithmeticException e) {
                mostrarAlertError(e.getMessage());
            } catch (Exception e) {
                mostrarAlertError("Ha ocurrido un error.");
            }
        });

        btnClear.setOnAction(actionEvent -> borrarPantalla());

        vBox = new VBox(txtPantalla, gdpTeclado, btnClear);
        vBox.setSpacing(5);
        escena = new Scene(vBox, 200, 270);
        escena.getStylesheets().add(getClass().getResource("/styles/Calc.css").toString());
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
        txtPantalla.setText("");
        x1 = 0;
        x2 = 0;
        r = 0;
        operacionTerminada = false;
    }

    private void detectarTecla(String tecla) {
        if (Operaciones.contains(tecla)) {
            op = tecla;
            x1 = Float.parseFloat(txtPantalla.getText());
            txtPantalla.setText("");
            System.out.println("x1=" + x1 + "\nx2=" + x2 + "\nop=" + op);
        } else {
            if (operacionTerminada && !Operaciones.contains(tecla)) {
                txtPantalla.clear();
                operacionTerminada = false;
            }
            txtPantalla.appendText(tecla);
        }
    }

    private void mostrarAlertError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error en la operación");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
