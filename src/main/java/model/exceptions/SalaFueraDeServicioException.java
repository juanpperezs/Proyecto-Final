package model.exceptions;

public class SalaFueraDeServicioException extends Exception {

    public SalaFueraDeServicioException(String mensajeError) {
        super(mensajeError);
    }
}