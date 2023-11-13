package com.venda.Vendas.rest.controller;

import com.venda.Vendas.domain.entity.ItemPedido;
import com.venda.Vendas.domain.entity.Pedido;
import com.venda.Vendas.domain.enums.StatusPedido;
import com.venda.Vendas.rest.dto.*;
import com.venda.Vendas.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Realiza a criação do pedido", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao criar o pedido")
    })
    @PostMapping
    @ResponseStatus(CREATED)
    public Long savePedido(@RequestBody @Valid PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }

    @Operation(summary = "Busca o pedido pelo Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Long id) {
        return pedidoService
                .obterPedidoCompleto(id)
                .map(p -> converterPedido(p))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    @Operation(summary = "Realiza a atualização do status do pedido", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar o pedido")
    })
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Long id, @RequestBody AtualizacaoStatusPedidoDTO atualizacaoStatusPedidoDTO){
        String novoStatus = atualizacaoStatusPedidoDTO.statusPedido();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converterPedido(Pedido pedido) {
        return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nome(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .statusPedido(pedido.getStatusPedido().name())
                .items(converterItems(pedido.getItemPedidoList()))
                .build();
    }

    private List<InformacoesItemsPedidoDTO> converterItems(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacoesItemsPedidoDTO
                        .builder().descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }
}
