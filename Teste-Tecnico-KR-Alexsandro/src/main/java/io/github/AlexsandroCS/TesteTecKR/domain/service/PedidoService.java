package io.github.AlexsandroCS.TesteTecKR.domain.service;

import io.github.AlexsandroCS.TesteTecKR.REST.DTO.criarPedidoDTO;
import io.github.AlexsandroCS.TesteTecKR.REST.DTO.retornoCriarPedidoDTO;
import io.github.AlexsandroCS.TesteTecKR.REST.DTO.retornoCriarProdutoDTO;
import io.github.AlexsandroCS.TesteTecKR.REST.DTO.retornoCriarUsuarioDTO;
import io.github.AlexsandroCS.TesteTecKR.domain.entity.Pedido;
import io.github.AlexsandroCS.TesteTecKR.domain.entity.Produto;
import io.github.AlexsandroCS.TesteTecKR.domain.entity.Usuario;
import io.github.AlexsandroCS.TesteTecKR.domain.repository.PedidoRepository;
import io.github.AlexsandroCS.TesteTecKR.domain.repository.UsuarioRepository;
import io.github.AlexsandroCS.TesteTecKR.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public retornoCriarPedidoDTO criarPedido(criarPedidoDTO criaPedido) {
        Usuario usuario = usuarioRepository.findById(criaPedido.usuarioId()).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));

        List<Produto> produtos = produtoRepository.findAllById(criaPedido.produtoIds());
        Pedido pedido = new Pedido(null, usuario, produtos, new Date());
        Pedido salvarPedido = pedidoRepository.save(pedido);
        retornoCriarUsuarioDTO retornaUsuarioDTO = new retornoCriarUsuarioDTO(salvarPedido.getUsuario().getId(), salvarPedido.getUsuario().getNome(), salvarPedido.getUsuario().getEmail(), salvarPedido.getDataPedido());

        List<retornoCriarProdutoDTO> retornaProdutosDTO = salvarPedido.getProdutos().stream()
            .map(produto -> new retornoCriarProdutoDTO(produto.getId(), produto.getProduto(), produto.getValor().toString())).collect(Collectors.toList());
        return new retornoCriarPedidoDTO(salvarPedido.getId(), salvarPedido.getDataPedido().toString(),retornaUsuarioDTO, retornaProdutosDTO);
    }

    public List<retornoCriarPedidoDTO> buscarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        return pedidos.stream()
            .map(pedido -> new retornoCriarPedidoDTO(
                pedido.getId(),
                pedido.getDataPedido().toString(),
                new retornoCriarUsuarioDTO(
                    pedido.getUsuario().getId(),
                    pedido.getUsuario().getNome(),
                    pedido.getUsuario().getEmail(),
                    pedido.getUsuario().getDataCriacao()
                ),
                pedido.getProdutos().stream()
                    .map(produto -> new retornoCriarProdutoDTO(
                        produto.getId(),
                        produto.getProduto(),
                        produto.getValor().toString()
                    )).collect(Collectors.toList())
            )).collect(Collectors.toList());
    }

    @Transactional
    public Boolean deletarPedidosUsuario(Long idUsuario) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(idUsuario);

        if (!pedidos.isEmpty()) {
            pedidoRepository.deleteAll(pedidos);
        }
        return true;
    }
}
