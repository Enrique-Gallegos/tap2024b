package com.example.tap2024b.vistas;
import com.example.tap2024b.models.ClienteDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class FormCliente extends Stage {

    private Scene scene;

    private TextField txtnomClt;
    private TextField txttelClt;
    private TextField txtemailClt;
    private Button btnGuardar;
    private VBox vbox;
    private ClienteDAO objCte;
    private TableView<ClienteDAO> tbvCliente;

    public FormCliente(TableView<ClienteDAO> tbv, ClienteDAO objC) {
        this.tbvCliente = tbv;
        CrearUI();
        if(objC != null) {
            this.objCte = objC;
            txtnomClt.setText(objCte.getNomClt());
            txtemailClt.setText(objCte.getEmailClt());
            txttelClt.setText(objCte.getTelClt());
            this.setTitle("Editar cliente");

        }
        else{
            this.objCte = new ClienteDAO();
            this.setTitle("Agregar cliente");
        }
        this.setScene(scene);
        this.show();
    }

    private void CrearUI(){
        txtnomClt = new TextField();
        txtnomClt.setPromptText("Nombre del cliente");

        txtemailClt = new TextField();
        txtemailClt.setPromptText("Email del cliente");

        txttelClt = new TextField();
        txttelClt.setPromptText("Telefono del cliente");

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarCliente());

        vbox = new VBox(txtnomClt,txtemailClt,txttelClt,btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        scene = new Scene(vbox,300,150);
    }

    private void GuardarCliente() {
        objCte.setEmailClt(txtemailClt.getText());
        objCte.setNomClt(txtnomClt.getText());
        objCte.setTelClt(txttelClt.getText());
        String msj;
        Alert.AlertType type;

        if(objCte.getIdClt()>0){

            objCte.UPDATE();
        }
        else {
            if (objCte.INSERT() > 0) {
                msj = "Registro insertado";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Ocurrio un error al insertar, intente de nuevo";
                type = Alert.AlertType.ERROR;
            }

            Alert alerta = new Alert(type);
            alerta.setTitle("Mensaje del Sistema");
            alerta.setContentText(msj);
            alerta.showAndWait();
        }
        tbvCliente.setItems(objCte.SELECTALL());
        tbvCliente.refresh();
    }

}
