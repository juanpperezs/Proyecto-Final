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

    public Sala(String codigo, String nombreONumero, int capacidad) {
        this.codigo = codigo;
        this.nombreONumero = nombreONumero;
        this.capacidad = capacidad;
        this.estado = EstadoSala.DISPONIBLE;
        this.listaAsientos = new ArrayList<>();
        generarAsientos();
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

    public void generarAsientos(){
        listaAsientos.clear();
        int asientosPorFila = 5;
        for (int i = 0; i < capacidad; i++) {
            char letraFila = (char) ('A' + (i / asientosPorFila));
            String fila = String.valueOf(letraFila);
            int numero = (i % asientosPorFila) + 1;
            String codigoAsiento = fila + numero;
            listaAsientos.add(new Asiento (codigoAsiento, fila, numero));
        }
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