package model.exceptions;

public class FuncionInexistenteException extends Exception {

    public FuncionInexistenteException(String mensajeError) {
        super(mensajeError);
    }
}