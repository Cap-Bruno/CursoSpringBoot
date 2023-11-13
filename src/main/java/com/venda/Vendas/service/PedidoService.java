package com.venda.Vendas.service;

import com.venda.Vendas.domain.entity.Pedido;
import com.venda.Vendas.domain.enums.StatusPedido;
import com.venda.Vendas.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);
    Optional<Pedido> obterPedidoCompleto(Long id);
    void atualizaStatus(Long id, StatusPedido statusPedido);

}
