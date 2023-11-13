package com.venda.Vendas.domain.repository;

import com.venda.Vendas.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
