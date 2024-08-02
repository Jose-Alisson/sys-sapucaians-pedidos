package br.joliny.pedidos.exception.handler;

import br.joliny.pedidos.exception.causable.ErrDataTransfer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerErrDataTransfer {
    @ExceptionHandler(ErrDataTransfer.class)
    public ResponseEntity<?> handle(ErrDataTransfer err){
        return ResponseEntity.status(err.getStatus()).body(err.getMessage());
    }
}