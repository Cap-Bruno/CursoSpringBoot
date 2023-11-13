package com.venda.Vendas.service.impl;

import com.venda.Vendas.domain.entity.Cliente;
import com.venda.Vendas.domain.entity.ItemPedido;
import com.venda.Vendas.domain.entity.Pedido;
import com.venda.Vendas.domain.entity.Produto;
import com.venda.Vendas.domain.enums.StatusPedido;
import com.venda.Vendas.domain.repository.ClientesRepository;
import com.venda.Vendas.domain.repository.ItemsPedidoRepository;
import com.venda.Vendas.domain.repository.PedidosRepository;
import com.venda.Vendas.domain.repository.ProdutosRepository;
import com.venda.Vendas.exception.ExceptionController;
import com.venda.Vendas.exception.PedidoNaoEncontradoException;
import com.venda.Vendas.rest.dto.ItemPedidoDTO;
import com.venda.Vendas.rest.dto.PedidoDTO;
import com.venda.Vendas.service.PedidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidosRepository pedidosRepository;
    @Autowired
    private ClientesRepository clientesRepository;
    @Autowired
    private ProdutosRepository produtosRepository;
    @Autowired
    private ItemsPedidoRepository itemsPedidoRepository;

    @Override
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Long idCliente = pedidoDTO.cliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new ExceptionController("Codigo de cliente invalido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.total());
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidoList = converterItems(pedido, pedidoDTO.items());
        pedidosRepository.save(pedido);
        itemsPedidoRepository.saveAll(itemPedidoList);
        pedido.setItemPedidoList(itemPedidoList);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Long id) {
        return pedidosRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Long id, StatusPedido statusPedido) {
        pedidosRepository
                .findById(id)
                .map(pedido -> {
                    pedido.setStatusPedido(statusPedido);
                    return pedidosRepository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new ExceptionController("Não é possivel realizar um pedido sem item");
        }

        return items
                .stream()
                .map(itemPedidoDTO -> {
                    Long idProduto = itemPedidoDTO.produto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(() -> new ExceptionController("Codigo de produto invalido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(itemPedidoDTO.quantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
