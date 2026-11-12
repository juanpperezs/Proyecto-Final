package model.exceptions;

public class VentaInexistenteException extends Exception {

    public VentaInexistenteException(String mensajeError) {
        super(mensajeError);
    }
}