package br.joliny.pedidos.exception.causable;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrDataTransfer extends RuntimeException{
    private final HttpStatus status;

    public ErrDataTransfer(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
