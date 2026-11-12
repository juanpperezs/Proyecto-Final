package model.core;

import model.enums.Clasificacion;
import model.enums.EstadoPelicula;

public class Pelicula {
    private String codgio;
    private String nombre;
    private String genero;
    private int duracionMinutos;
    private  Clasificacion clasificacion;
    private EstadoPelicula estado;

    public Pelicula(String codgio, String nombre, String genero, int duracionMinutos, Clasificacion clasificacion, EstadoPelicula estado) {
        this.codgio = codgio;
        this.nombre = nombre;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.estado = estado;
    }

    public String getCodgio() {
        return codgio;
    }

    public void setCodgio(String codgio) {
        this.codgio = codgio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public EstadoPelicula getEstado() {
        return estado;
    }

    public void setEstado(EstadoPelicula estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "codgio='" + codgio + '\'' +
                ", nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                ", duracionMinutos=" + duracionMinutos +
                ", clasificacion=" + clasificacion +
                ", estado=" + estado +
                '}';
    }
}
