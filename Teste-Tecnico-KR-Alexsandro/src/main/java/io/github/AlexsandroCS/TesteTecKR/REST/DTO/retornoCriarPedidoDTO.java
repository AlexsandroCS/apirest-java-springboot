package io.github.AlexsandroCS.TesteTecKR.REST.DTO;

import java.util.List;

public record retornoCriarPedidoDTO(
        Long id,
        String dataPedido,
        retornoCriarUsuarioDTO usuario,
        List<retornoCriarProdutoDTO> produtos
) { }
