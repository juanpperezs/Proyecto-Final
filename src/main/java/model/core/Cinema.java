package model.core;

import model.espacios.Sala;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cinema {
    private String nombre;
    private List<Pelicula> listaPeliculas;
    private List<Sala> listaSalas;
    private List<Funcion> listaFunciones;
    private List<Venta> listaVentas;
    private List<Cliente> listaClientes;

    public Cinema(String nombre) {
        this.nombre = nombre;
        this.listaPeliculas = new ArrayList<Pelicula>();
        this.listaSalas = new ArrayList<>();
        this.listaFunciones = new ArrayList<>();
        this.listaVentas = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
    }

    public void registrarPelicula(Pelicula pelicula) {

    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }
    public void setListaPeliculas(List<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }

    public List<Sala> getListaSalas() {
        return listaSalas;
    }
    public void setListaSalas(List<Sala> listaSalas) {
        this.listaSalas = listaSalas;
    }

    public List<Funcion> getListaFunciones() {
        return listaFunciones;
    }
    public void setListaFunciones(List<Funcion> listaFunciones) {
        this.listaFunciones = listaFunciones;
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }
    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }
}