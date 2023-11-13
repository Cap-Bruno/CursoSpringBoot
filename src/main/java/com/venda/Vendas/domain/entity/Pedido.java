package com.venda.Vendas.domain.entity;

import com.venda.Vendas.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;
    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido statusPedido;
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itemPedidoList;
}
