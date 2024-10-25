package com.example.tap2024b.vistas;

import com.example.tap2024b.models.AlbumDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormAlbum extends Stage {

    private Scene scene;
    private TextField txtBanda;
    private TextField txtNombre;
    private TextField txtAño;
    private Button btnGuardar;
    private VBox vbox;
    private AlbumDAO objAlbum;
    private TableView<AlbumDAO> tbvAlbum;

    public FormAlbum(TableView<AlbumDAO> tbv, AlbumDAO objA) {
        this.tbvAlbum = tbv;
        CrearUI();
        if (objA != null) {
            this.objAlbum = objA;
            txtBanda.setText(objAlbum.getBanda());
            txtNombre.setText(objAlbum.getNombre());
            txtAño.setText(objAlbum.getAñoSalida());
            this.setTitle("Editar Álbum");

        } else {
            this.objAlbum = new AlbumDAO();
            this.setTitle("Agregar Álbum");
        }
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        txtBanda = new TextField();
        txtBanda.setPromptText("Banda");

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del Álbum");

        txtAño = new TextField();
        txtAño.setPromptText("Año de Salida");

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarAlbum());

        vbox = new VBox(txtBanda, txtNombre, txtAño, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        scene = new Scene(vbox, 300, 200);
    }

    private void GuardarAlbum() {
        objAlbum.setBanda(txtBanda.getText());
        objAlbum.setNombre(txtNombre.getText());
        objAlbum.setAñoSalida(txtAño.getText());
        String msj;
        Alert.AlertType type;

        if (!objAlbum.esNuevo()) {
            objAlbum.UPDATE();
            msj = "Álbum actualizado correctamente";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (objAlbum.INSERT() > 0) {
                msj = "Álbum insertado correctamente";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Error al insertar, intente de nuevo";
                type = Alert.AlertType.ERROR;
            }
        }

        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();

        tbvAlbum.setItems(objAlbum.SELECTALL());
        tbvAlbum.refresh();
    }
}
