package com.venda.Vendas.rest.controller;

import com.venda.Vendas.exception.ExceptionController;
import com.venda.Vendas.exception.PedidoNaoEncontradoException;
import com.venda.Vendas.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerException(ExceptionController ex){
        String messageError = ex.getMessage();
        return new ApiErrors(messageError);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex){
        List<String> erros = ex.getBindingResult().getAllErrors()
                .stream().map(erro -> erro.getDefaultMessage()).collect(Collectors.toList());
        return new ApiErrors(erros);
    }

}
