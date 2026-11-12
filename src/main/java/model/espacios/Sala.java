package model.espacios;

import model.enums.EstadoSala;
import java.util.ArrayList;
import java.util.List;

public class Sala {
    private String codigo;
    private String nombreONumero;
    private int capacidad;
    private EstadoSala estado;
    private List<Asiento> listaAsientos;

    public Sala(String codigo, String nombreONumero, int capacidad, EstadoSala estado, List<Asiento> listaAsientos) {
        this.codigo = codigo;
        this.nombreONumero = nombreONumero;
        this.capacidad = capacidad;
        this.estado = estado;
        this.listaAsientos = listaAsientos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreONumero() {
        return nombreONumero;
    }

    public void setNombreONumero(String nombreONumero) {
        this.nombreONumero = nombreONumero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoSala getEstado() {
        return estado;
    }

    public void setEstado(EstadoSala estado) {
        this.estado = estado;
    }

    public List<Asiento> getListaAsientos() {
        return listaAsientos;
    }

    public void setListaAsientos(List<Asiento> listaAsientos) {
        this.listaAsientos = listaAsientos;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "codigo='" + codigo + '\'' +
                ", nombreONumero='" + nombreONumero + '\'' +
                ", capacidad=" + capacidad +
                ", estado=" + estado +
                ", listaAsientos=" + listaAsientos +
                '}';
    }
}
