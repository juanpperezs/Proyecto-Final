package model.core;

import model.enums.EstadoVenta;
import model.espacios.Asiento;
import model.enums.TipoCliente;

import java.time.LocalDateTime;

public class Venta {
    private String codigoVenta;
    private Cliente cliente;
    private Funcion funcion;
    private Asiento asiento;
    private LocalDateTime fechaYHora;
    private double valorPagado;
    private EstadoVenta estadoVenta;

    public Venta(String codigo, Cliente cliente, Funcion funcion, Asiento asiento, LocalDateTime fechaYHora, double valorPagado, EstadoVenta estadoVenta) {
        this.codigoVenta = codigo;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asiento = asiento;
        this.fechaYHora = fechaYHora;
        this.valorPagado = valorPagado;
        this.estadoVenta = estadoVenta;
    }

    public String getCodigoVenta() {
        return codigoVenta;
    }
    public void setCodigoVenta(String codigoVenta) {
        this.codigoVenta = codigoVenta;
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

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }
    public void setFechaYHora(LocalDateTime fechaYHora) {
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

    public double calcularValorPagado() {
        double base = funcion.getValorBase();
        TipoCliente tipo = cliente.getTipoCliente();
        double descuento = 0.0;
        if (tipo == TipoCliente.ESTUDIANTE) {
            descuento = 0.20;
        } else if (tipo == TipoCliente.NINO) {
            descuento = 0.30;
        } else if (tipo == TipoCliente.ADULTO_MAYOR) {
            descuento = 0.25;
        } else {
            descuento = 0.0;
        }
        this.valorPagado = base * (1.0 - descuento);
        return this.valorPagado;
    }

    public void cancelarVenta() {
        this.estadoVenta = EstadoVenta.CANCELADA;
        if (this.asiento != null) {
            this.asiento.liberarAsiento();
        }
    }

    @Override
    public String toString() {
        return "Venta{" +
                "codigoVenta='" + codigoVenta + '\'' +
                ", cliente=" + cliente.getNombre() +
                ", pelicula=" + funcion.getPelicula().getNombrePelicula() +
                ", funcion=" + funcion.getCodigoFuncion() +
                ", asiento=" + asiento.getCodigoAsiento() +
                ", fechaYHora='" + fechaYHora + '\'' +
                ", valorPagado=" + valorPagado +
                ", estadoVenta=" + estadoVenta +
                '}';
    }
}