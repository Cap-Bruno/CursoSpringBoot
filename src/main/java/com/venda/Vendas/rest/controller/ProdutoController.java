package com.venda.Vendas.rest.controller;

import com.venda.Vendas.domain.entity.Cliente;
import com.venda.Vendas.domain.entity.Produto;
import com.venda.Vendas.domain.repository.ProdutosRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos")
public class ProdutoController {

    @Autowired
    private ProdutosRepository produtosRepository;

    @Operation(summary = "Realiza a criação do produto", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao criar o produto")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto saveProduto(@RequestBody @Valid Produto produto){
        return produtosRepository.save(produto);
    }

    @Operation(summary = "Realiza a atualização do produto", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar o produto")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduto(@PathVariable("id") Long id, @RequestBody @Valid Produto produto){
        produtosRepository
                .findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    produtosRepository.save(produto);
                    return produtoExistente;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @Operation(summary = "Deleta o produto", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar o produto")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduto(@PathVariable("id") Long id){
        produtosRepository.findById(id)
                .map(produto -> {
                    produtosRepository.delete(produto);
                    return produto;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));;

    }

    @Operation(summary = "Busca o produto pelo Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
            @ApiResponse(responseCode = "404", description = "Prodyuto nçao encontrado")
    })
    @GetMapping("/{id}")
    public Produto getProdutoClienteById(@PathVariable("id") Long id){
        return produtosRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }
    @GetMapping
    public List<Produto> findProduto(Produto produtoFilter){
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(produtoFilter, exampleMatcher);
        return produtosRepository.findAll(example);
    }

}
