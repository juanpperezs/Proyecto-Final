package model.exceptions;

public class SalaNoDisponibleException extends Exception {
    public SalaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}