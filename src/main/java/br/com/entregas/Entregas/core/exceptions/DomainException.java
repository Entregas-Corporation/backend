package br.com.entregas.Entregas.core.exceptions;

public class DomainException extends RuntimeException{
    public DomainException(String message){
        super(message);
    }
}
