package com.example.tap2024b.components;

import com.example.tap2024b.models.TareaImpresion;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;

public class ImpresionThread extends Thread {

    private final TableView<TareaImpresion> tblTareas;
    private final ProgressBar pgbProgreso;
    private boolean activo;

    public ImpresionThread(TableView<TareaImpresion> tblTareas, ProgressBar pgbProgreso) {
        this.tblTareas = tblTareas;
        this.pgbProgreso = pgbProgreso;
        this.activo = true;
    }

    public void detenerSimulador() {
        this.activo = false;
    }

    public void iniciarSimulador() {
        this.activo = true;
    }

    @Override
    public void run() {
        while (true) {
            if (activo && !tblTareas.getItems().isEmpty()) {
                TareaImpresion tarea = tblTareas.getItems().get(0);
                procesarTarea(tarea);
                Platform.runLater(() -> tblTareas.getItems().remove(tarea));
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void procesarTarea(TareaImpresion tarea) {
        int hojas = tarea.getNumeroHojas();

        for (int i = 1; i <= hojas; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double progreso = (double) i / hojas;
            Platform.runLater(() -> tarea.setProgreso(progreso));
        }

        Platform.runLater(() -> tarea.setProgreso(0));
    }
}
