package com.venda.Vendas.rest.dto;

import com.venda.Vendas.validation.NotEmptyList;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record PedidoDTO(
        @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
        Long cliente,
        @NotNull(message = "{campo.total-pedido.obrigatorio}")
        BigDecimal total,
        @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
        List<ItemPedidoDTO> items) {
}
