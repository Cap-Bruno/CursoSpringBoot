package com.venda.Vendas.rest.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record InformacoesItemsPedidoDTO(String descricaoProduto, BigDecimal precoUnitario, int quantidade) {
}
