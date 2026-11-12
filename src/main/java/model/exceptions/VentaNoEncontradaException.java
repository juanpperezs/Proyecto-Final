package model.exceptions;

public class VentaNoEncontradaException extends Exception {
    public VentaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}