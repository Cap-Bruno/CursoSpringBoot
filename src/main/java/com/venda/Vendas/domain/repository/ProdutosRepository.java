package com.venda.Vendas.domain.repository;

import com.venda.Vendas.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto, Long> {

}
