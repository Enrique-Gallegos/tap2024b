package com.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Calculadora extends Stage {

    private Button[][] arBtns;
    private TextField txtPantalla;
    private GridPane gdpTeclado;
    private VBox vBox;
    private Scene escena;
    String[] strTeclas = {"7","8","9","*","4","5","6","/","1","2","3","-",".","0","=","+"};
    private Button btnBorrar = new Button("<-");
    private double x = 0, y = 0, res = 0;
    private String operacion = "";
    private boolean flag = false;

    private void CrearUI(){
        arBtns = new Button[4][4];
        txtPantalla = new TextField("0");
        txtPantalla.setAlignment(Pos.CENTER_RIGHT);
        txtPantalla.setEditable(false);
        gdpTeclado = new GridPane();
        CrearTeclado();

        btnBorrar.setOnAction(event -> borrarPantalla());
        btnBorrar.setPrefSize(200,50);

        vBox = new VBox(txtPantalla,gdpTeclado,btnBorrar);
        vBox.setSpacing(1);
        escena = new Scene(vBox,200,270);
    }

    private void CrearTeclado(){
        for (int i = 0; i < arBtns.length; i++) {
            for (int j = 0; j < arBtns[0].length; j++) {
                arBtns[j][i] = new Button(strTeclas[4 * i + j]);
                arBtns[j][i].setPrefSize(50, 50);
                int finali = i, finalj = j;
                arBtns[j][i].setOnAction(event -> detectarTecla(strTeclas[4 * finali + finalj]));
                gdpTeclado.add(arBtns[j][i],j,i);
            }
        }
    }

    public Calculadora(){
        CrearUI();
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }

    private void borrarPantalla(){
        txtPantalla.clear();
        txtPantalla.setText("0");

    }

    private void detectarTecla(String tecla) {
        if (detectarOperacion(tecla)){
            flag = true;
        } else {
            if (flag)
                txtPantalla.clear();
            flag = false;
            if (txtPantalla.getText().equals("0"))
                txtPantalla.setText("");
            txtPantalla.appendText(tecla);
        }
    }

    private boolean detectarOperacion(String tecla) {
        switch(tecla){
            case "+":
                if (!flag)
                    if (!operacion.equals(""))
                        resultado();
                operacion = "+";
                x = Double.parseDouble(txtPantalla.getText());
                return true;
            case "-":
                if (!flag)
                    if (!operacion.equals(""))
                        resultado();
                operacion = "-";
                x = Double.parseDouble(txtPantalla.getText());
                return true;
            case "*":
                if (!flag)
                    if (!operacion.equals(""))
                        resultado();
                operacion = "*";
                x = Double.parseDouble(txtPantalla.getText());
                return true;
            case "/":
                if (!flag)
                    if (!operacion.equals(""))
                        resultado();
                operacion = "/";
                x = Double.parseDouble(txtPantalla.getText());
                return true;
            case "=":
                if(!flag)
                    resultado();
                return true;
            default:
                return false;
        }
    }

    private void resultado(){
        y = Double.parseDouble(txtPantalla.getText());
        realizarOperacion();
    }

    private void realizarOperacion(){
        if (!operacion.equals("")) {
            switch (operacion) {
                case "+":
                    res = x + y;
                    break;
                case "-":
                    res = x - y;
                    break;
                case "*":
                    res = x * y;
                    break;
                case "/":
                    res = x / y;
                    break;
            }
            txtPantalla.setText("" + res);
            operacion = "";
        }
    }
}