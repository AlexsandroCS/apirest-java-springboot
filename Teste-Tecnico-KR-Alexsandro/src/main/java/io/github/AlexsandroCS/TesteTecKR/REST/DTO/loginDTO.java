package io.github.AlexsandroCS.TesteTecKR.REST.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record loginDTO(
        @NotBlank(message = "E-mail não pode ser vazio!")
        @Email(message = "Insira um e-mail válido!")
        String email,

        @NotBlank(message = "Senha incorreta!")
        String senha
) { }