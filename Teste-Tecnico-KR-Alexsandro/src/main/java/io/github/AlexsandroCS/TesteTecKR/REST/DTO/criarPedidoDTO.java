package io.github.AlexsandroCS.TesteTecKR.REST.DTO;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record criarPedidoDTO(
        @NotBlank(message = "O pedido precisa ter um Usuário vinculado!")
        Long usuarioId,

        @NotBlank(message = "Precisa ter pelo menos 1 Produto vinculado!")
        List<Long> produtoIds
) { }
