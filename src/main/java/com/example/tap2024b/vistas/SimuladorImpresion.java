package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ImpresionThread;
import com.example.tap2024b.models.TareaImpresion;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class SimuladorImpresion extends Stage {
    private TableView<TareaImpresion> tblTareas;
    private ProgressBar pgbProgreso;
    private ImpresionThread hiloImpresion;
    private Button btnAgregarTarea;
    private Button btnEncApa;
    private boolean simuladorActivo;
    private static int siguienteId = 1;

    public SimuladorImpresion() {
        crearUI();
        this.setTitle("Simulador de Impresión");
        this.show();
    }

    private void crearUI() {
        tblTareas = new TableView<>();
        tblTareas.setItems(FXCollections.observableArrayList());
        inicializarTabla();

        btnAgregarTarea = new Button("Agregar Tarea");
        btnAgregarTarea.getStyleClass().add("btn");
        btnAgregarTarea.getStyleClass().add("btn-primary");
        btnAgregarTarea.setOnAction(e -> agregarTarea());

        btnEncApa = new Button("Encender Simulador");
        btnEncApa.getStyleClass().add("btn");
        btnEncApa.getStyleClass().add("btn-success");
        btnEncApa.setOnAction(e -> encenderApagar(btnEncApa));

        pgbProgreso = new ProgressBar(0);
        pgbProgreso.getStyleClass().add("progress-bar");

        VBox vbox = new VBox(10, tblTareas, btnAgregarTarea, btnEncApa, pgbProgreso);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-background-color: #595555;");

        Scene escena = new Scene(vbox, 600, 400);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        this.setScene(escena);
    }

    private void inicializarTabla() {
        TableColumn<TareaImpresion, Integer> colNumeroArchivo = new TableColumn<>("No. Archivo");
        colNumeroArchivo.setCellValueFactory(data -> data.getValue().numeroArchivoProperty().asObject());

        TableColumn<TareaImpresion, String> colNombreArchivo = new TableColumn<>("Nombre");
        colNombreArchivo.setCellValueFactory(data -> data.getValue().nombreArchivoProperty());
        colNombreArchivo.prefWidthProperty().bind(tblTareas.widthProperty().multiply(0.4));

        TableColumn<TareaImpresion, Integer> colNumeroHojas = new TableColumn<>("N° Hojas");
        colNumeroHojas.setCellValueFactory(data -> data.getValue().numeroHojasProperty().asObject());

        TableColumn<TareaImpresion, String> colHoraAcceso = new TableColumn<>("Hora Acceso");
        colHoraAcceso.setCellValueFactory(data -> data.getValue().horaAccesoProperty());

        TableColumn<TareaImpresion, Double> colProgreso = new TableColumn<>("Progreso");
        colProgreso.setCellFactory(tc -> new TableCell<>() {
            private final ProgressBar progressBar = new ProgressBar(0);

            @Override
            protected void updateItem(Double progreso, boolean empty) {
                super.updateItem(progreso, empty);
                if (empty || progreso == null) {
                    setGraphic(null);
                } else {
                    progressBar.setProgress(progreso);
                    setGraphic(progressBar);
                }
            }
        });
        colProgreso.setCellValueFactory(data -> data.getValue().progresoProperty().asObject());

        tblTareas.getColumns().addAll(colNumeroArchivo, colNombreArchivo, colNumeroHojas, colHoraAcceso, colProgreso);
    }

    private void agregarTarea() {
        int numeroArchivo = siguienteId++;
        String nombreArchivo = generarNombreArchivo();
        int numeroHojas = new Random().nextInt(10) + 3;
        String horaAcceso = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        TareaImpresion nuevaTarea = new TareaImpresion(numeroArchivo, nombreArchivo, numeroHojas, horaAcceso);
        tblTareas.getItems().add(nuevaTarea);
    }

    private String generarNombreArchivo() {
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String[] extensiones = {".txt", ".pdf", ".docx", ".xlsx"};
        String extension = extensiones[new Random().nextInt(extensiones.length)];
        return "Archivo_" + fechaHora + extension;
    }

    private void encenderApagar(Button btnEncApa) {
        if (simuladorActivo) {
            hiloImpresion.detenerSimulador();
            btnEncApa.setText("Encender Simulador");
        } else {
            if (hiloImpresion == null) {
                hiloImpresion = new ImpresionThread(tblTareas, pgbProgreso);
                hiloImpresion.setDaemon(true);
                hiloImpresion.start();
            }
            hiloImpresion.iniciarSimulador();
            btnEncApa.setText("Apagar Simulador");
        }
        simuladorActivo = !simuladorActivo;
    }
}
