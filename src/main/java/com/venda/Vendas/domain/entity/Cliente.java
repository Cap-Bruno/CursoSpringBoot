package com.venda.Vendas.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", length = 100)
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @Column(name = "cpf", length = 11)
    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF
    private String cpf;

    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> pedidos;

    public Set<Pedido> getPedidos() {
        return pedidos;
    }


}
