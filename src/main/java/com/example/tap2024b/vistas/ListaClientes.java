package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCell;
import com.example.tap2024b.models.ClienteDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaClientes extends Stage {

    private TableView<ClienteDAO> tbvClientes;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    public ListaClientes(){
        CrearUI();
        this.setTitle("Lista de clientes :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(new Image("C:\\Users\\Admin\\IdeaProjects\\TAP2024b\\src\\main\\resources\\image\\Derecha.png"));
        Button btnAddCte = new Button();
        btnAddCte.setOnAction(actionEvent -> new FormCliente(tbvClientes,null));
        btnAddCte.setGraphic(imv);
        tlbMenu.getItems().add(btnAddCte);

        CrearTable();
        vBox = new VBox(tlbMenu,tbvClientes);
        escena = new Scene(vBox,500,250);
    }

    private void CrearTable() {
        ClienteDAO objClt = new ClienteDAO();
        tbvClientes = new TableView<>();

        TableColumn<ClienteDAO, String> tbcNom = new TableColumn<>("Nombre");
        tbcNom.setCellValueFactory(new PropertyValueFactory<>("nomClt"));

        TableColumn<ClienteDAO, String> tbcTel = new TableColumn<>("Telefono");
        tbcTel.setCellValueFactory(new PropertyValueFactory<>("telClt"));

        TableColumn<ClienteDAO, String> tbcEmail = new TableColumn<>("Email");
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("emailClt"));

        TableColumn<ClienteDAO,String> tbcEditar = new TableColumn<ClienteDAO,String>();
        tbcEditar.setCellFactory(
                new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
                    @Override
                    public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> param) {
                        return new ButtonCell("Editar");
                    }
                }
        );

        TableColumn<ClienteDAO,String> tbcEliminar = new TableColumn<ClienteDAO,String>();
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
                    @Override
                    public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> param) {
                        return new ButtonCell("Eliminar");
                    }
                }
        );

        tbvClientes.getColumns().addAll(tbcNom,tbcTel,tbcEmail,tbcEditar,tbcEliminar);
        tbvClientes.setItems(objClt.SELECTALL());
    }
}