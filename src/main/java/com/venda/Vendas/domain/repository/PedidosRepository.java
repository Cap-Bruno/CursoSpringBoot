package com.venda.Vendas.domain.repository;

import com.venda.Vendas.domain.entity.Cliente;
import com.venda.Vendas.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidosRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("select p from Pedido p left join fetch p.itemPedidoList where p.id = :id")
    Optional<Pedido> findByIdFetchItens(@Param("id") Long id);
}
