package com.venda.Vendas.rest.controller;

import com.venda.Vendas.domain.entity.Cliente;
import com.venda.Vendas.domain.repository.ClientesRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes")
public class ClienteController {

    @Autowired
    private ClientesRepository clientesRepository;

    @Operation(summary = "Busca o cliente pelo Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable("id") Long id){
        return clientesRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }
    @GetMapping
    public List<Cliente> findCliente(Cliente clienteFilter){
        ExampleMatcher exampleMatcher = ExampleMatcher
                                            .matching()
                                            .withIgnoreCase()
                                            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(clienteFilter, exampleMatcher);
        return clientesRepository.findAll(example);
    }

    @Operation(summary = "Realiza a criação do cliente", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao criar o cliente")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveCliente(@RequestBody @Valid Cliente cliente){
        return clientesRepository.save(cliente);
    }

    @Operation(summary = "Deleta o cliente", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar o cliente")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable("id") Long id){
        clientesRepository.findById(id)
                .map(cliente -> {
                    clientesRepository.delete(cliente);
                    return cliente;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));;
    }

    @Operation(summary = "Realiza a atualização do cliente", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar o cliente")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCliente(@PathVariable("id") Long id, @RequestBody @Valid Cliente cliente){
        clientesRepository
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientesRepository.save(cliente);
                    return clienteExistente;
        }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }
}
