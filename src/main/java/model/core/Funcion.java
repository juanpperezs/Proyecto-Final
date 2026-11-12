package model.core;

import model.enums.EstadoAsiento;
import model.enums.EstadoFuncion;
import model.espacios.Asiento;
import model.espacios.Sala;

import java.time.LocalDate;
import java.time.LocalTime;

public class Funcion {

    private String codigoFuncion;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDate fecha;
    private LocalTime hora;
    private double valorBase;
    private EstadoFuncion estado;

    public Funcion(String codigoFuncion, Pelicula pelicula, Sala sala, LocalDate fecha, LocalTime hora, double valorBase, EstadoFuncion estado) {
        this.codigoFuncion = codigoFuncion;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
        this.valorBase = valorBase;
        this.estado = estado;
    }

    public String getCodigoFuncion() {
        return codigoFuncion;
    }

    public void setCodigo(String codigo) {
        this.codigoFuncion = codigo;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public EstadoFuncion getEstado() {
        return estado;
    }

    public void setEstado(EstadoFuncion estado) {
        this.estado = estado;
    }


    public int getCantidadAsientosDisponibles() {
        int cantidadAsientosDisponibles = 0;
        for (Asiento asientoActual : sala.getListaAsientos()) {
            if (asientoActual.getEstado() == EstadoAsiento.DISPONIBLE) {
                cantidadAsientosDisponibles++;
            }
        }
        return cantidadAsientosDisponibles;
    }

    public int getCantidadAsientosOcupados() {
        int cantidadAsientosOcupados = 0;
        for (Asiento asientoActual : sala.getListaAsientos()) {
            if (asientoActual.getEstado() == EstadoAsiento.OCUPADO) {
                cantidadAsientosOcupados++;
            }
        }
        return cantidadAsientosOcupados;
    }
}