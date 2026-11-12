package model.exceptions;

public class CodigoPeliculaDuplicadoException extends Exception {

    public CodigoPeliculaDuplicadoException(String mensajeError) {
        super(mensajeError);
    }
}