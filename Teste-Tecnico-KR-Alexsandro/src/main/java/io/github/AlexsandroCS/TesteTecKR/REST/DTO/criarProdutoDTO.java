package io.github.AlexsandroCS.TesteTecKR.REST.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record criarProdutoDTO(
        @NotBlank(message = "O produto precisa ter um nome!")
        @Size(min = 3, message = "Nome do produto precisa ter 3 ou mais letras!")
        String produto,

        BigDecimal valor
) { }
