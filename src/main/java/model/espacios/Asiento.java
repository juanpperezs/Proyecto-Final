package model.espacios;

import model.enums.EstadoAsiento;

public class Asiento {
    private String codigoAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(String codigo, String fila, int numero) {
        this.codigoAsiento = codigo;
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public String getCodigoAsiento() {
        return codigoAsiento;
    }

    public void setCodigoAsiento(String codigoAsiento) {
        this.codigoAsiento = codigoAsiento;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Asiento{" +
                "codigoAsiento='" + codigoAsiento + '\'' +
                ", fila='" + fila + '\'' +
                ", numero=" + numero +
                ", estado=" + estado +
                '}';
    }

    public void liberarAsiento() {
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public void ocuparAsiento() {
        this.estado = EstadoAsiento.OCUPADO;
    }

}
