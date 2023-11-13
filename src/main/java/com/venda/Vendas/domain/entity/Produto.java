package com.venda.Vendas.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String descricao;
    @NotNull(message = "{campo.preco.obrigatorio}")
    private BigDecimal preco;
}
