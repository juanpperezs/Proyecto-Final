package model.espacios;

import model.enums.EstadoAsiento;

public class Asiento {
    private String codigo;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(String codigo, String fila, int numero) {
        this.codigo = codigo;
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
                "codigo='" + codigo + '\'' +
                ", fila='" + fila + '\'' +
                ", numero=" + numero +
                ", estado=" + estado +
                '}';
    }

    public void liberarAsiento() {
        this.estado = EstadoAsiento.DISPONIBLE;
    }
}
