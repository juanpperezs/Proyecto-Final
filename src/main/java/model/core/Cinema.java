package model.core;

public class Cinema {

    private String identificacion;
    private String nombre;
    private TipoCliente tipoCliente;

    public Cinema(String identificacion, String nombre, TipoCliente tipoCliente) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

}
