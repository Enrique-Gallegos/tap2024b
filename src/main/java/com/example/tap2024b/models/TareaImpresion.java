package com.example.tap2024b.models;

import javafx.beans.property.*;

public class TareaImpresion {

    private final SimpleIntegerProperty numeroArchivo;
    private final SimpleStringProperty nombreArchivo;
    private final SimpleIntegerProperty numeroHojas;
    private final SimpleStringProperty horaAcceso;
    private final SimpleDoubleProperty progreso;

    public TareaImpresion(int numeroArchivo, String nombreArchivo, int numeroHojas, String horaAcceso) {
        this.numeroArchivo = new SimpleIntegerProperty(numeroArchivo);
        this.nombreArchivo = new SimpleStringProperty(nombreArchivo);
        this.numeroHojas = new SimpleIntegerProperty(numeroHojas);
        this.horaAcceso = new SimpleStringProperty(horaAcceso);
        this.progreso = new SimpleDoubleProperty(0);
    }

    public int getNumeroArchivo() {
        return numeroArchivo.get();
    }

    public String getNombreArchivo() {
        return nombreArchivo.get();
    }

    public int getNumeroHojas() {
        return numeroHojas.get();
    }

    public String getHoraAcceso() {
        return horaAcceso.get();
    }

    public SimpleIntegerProperty numeroArchivoProperty() {
        return numeroArchivo;
    }

    public SimpleStringProperty nombreArchivoProperty() {
        return nombreArchivo;
    }

    public SimpleIntegerProperty numeroHojasProperty() {
        return numeroHojas;
    }

    public SimpleStringProperty horaAccesoProperty() {
        return horaAcceso;
    }

    public double getProgreso() {
        return progreso.get();
    }

    public void setProgreso(double progreso) {
        this.progreso.set(progreso);
    }

    public DoubleProperty progresoProperty() {
        return progreso;
    }
}
