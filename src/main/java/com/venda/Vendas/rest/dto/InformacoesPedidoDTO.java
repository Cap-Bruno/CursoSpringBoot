package com.venda.Vendas.rest.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record InformacoesPedidoDTO(Long codigo, String cpf, String nome, BigDecimal total, String dataPedido, String statusPedido, List<InformacoesItemsPedidoDTO>items) {
}
