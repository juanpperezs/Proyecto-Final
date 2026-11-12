package model.core;

import model.enums.TipoCliente;

public class Cliente {
    private String identificacionCliente;
    private String nombre;
    private TipoCliente tipoCliente;

    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente) {
        this.identificacionCliente = identificacion;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
    }

    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "identificacionCliente='" + identificacionCliente + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tipoCliente=" + tipoCliente +
                '}';
    }
}