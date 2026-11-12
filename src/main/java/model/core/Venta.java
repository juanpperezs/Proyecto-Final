package model.core;

import model.enums.EstadoVenta;
import model.espacios.Asiento;

public class Venta {
    private String codigo;
    private Cliente cliente;
    private Funcion funcion;
    private Asiento asiento;
    private String fechaYHora;
    private double valorPagado;
    private EstadoVenta estadoVenta;

    public Venta(String codigo, Cliente cliente, Funcion funcion, Asiento asiento, String fechaYHora, double valorPagado, EstadoVenta estadoVenta) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asiento = asiento;
        this.fechaYHora = fechaYHora;
        this.valorPagado = valorPagado;
        this.estadoVenta = estadoVenta;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcion getFuncion() {
        return funcion;
    }
    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Asiento getAsiento() {
        return asiento;
    }
    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public String getFechaYHora() {
        return fechaYHora;
    }
    public void setFechaYHora(String fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public double getValorPagado() {
        return valorPagado;
    }
    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public EstadoVenta getEstadoVenta() {
        return estadoVenta;
    }
    public void setEstadoVenta(EstadoVenta estadoVenta) {
        this.estadoVenta = estadoVenta;
    }
}
