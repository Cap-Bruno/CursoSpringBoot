package com.venda.Vendas.domain.repository;

import com.venda.Vendas.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientesRepository extends JpaRepository<Cliente, Long> {

    //O nome da função da interface é no padrão da query que você está solicitando
    //Logo, a ordem do nome na função tambem importa quando utilizada no parametro
    List<Cliente> findByNomeLike(String nome);

    //Para utilizar outro nome da função utilizasse a annotation @Query
    @Query("select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPeloNome(@Param("nome") String nome);

    boolean existsByNome(String nome);

    @Query("select c from Cliente c left join fetch c.pedidos p where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Long id);
}
