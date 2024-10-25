package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCell;
import com.example.tap2024b.models.AlbumDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaAlbum extends Stage {
    private TableView<AlbumDAO> tbvAlbum;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaAlbum() {
        CrearUI();
        this.setTitle("Lista de Álbumes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        Button btnAddAlbum = new Button("Agregar Álbum");
        btnAddAlbum.setOnAction(actionEvent -> new FormAlbum(tbvAlbum, null));
        tlbMenu.getItems().add(btnAddAlbum);

        CrearTable();
        vBox = new VBox(tlbMenu, tbvAlbum);
        escena = new Scene(vBox, 600, 400);
    }

    private void CrearTable() {
        AlbumDAO objAlbum = new AlbumDAO();
        tbvAlbum = new TableView<>();

        TableColumn<AlbumDAO, String> tbcBanda = new TableColumn<>("Banda");
        tbcBanda.setCellValueFactory(new PropertyValueFactory<>("banda"));

        TableColumn<AlbumDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<AlbumDAO, String> tbcAño = new TableColumn<>("Año de Salida");
        tbcAño.setCellValueFactory(new PropertyValueFactory<>("añoSalida"));

        // Column for the Edit button
        TableColumn<AlbumDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                album -> new FormAlbum(tbvAlbum, album), // Action for editing
                null  // No delete action in the edit column
        ));

        // Column for the Delete button
        TableColumn<AlbumDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new ButtonCell<>(
                "Eliminar",
                null, // No edit action in the delete column
                album -> {
                    album.DELETE();  // Delete action
                    tbvAlbum.setItems(album.SELECTALL());
                }
        ));

        tbvAlbum.getColumns().addAll(tbcBanda, tbcNombre, tbcAño, tbcEditar, tbcEliminar);
        tbvAlbum.setItems(objAlbum.SELECTALL());
    }

}
