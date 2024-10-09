package io.github.AlexsandroCS.TesteTecKR.REST.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record criarUsuarioDTO(
        @NotBlank(message = "É necessário ter um nome!")
        @Size(min = 3, message = "Nome precisa ter 3 ou mais letras!")
        String nome,

        @NotBlank(message = "Campo vazio, coloque um e-mail valido!")
        @Email(message = "E-mail não é inválido!")
        String email,

        @NotBlank(message = "Senha não pode ser vazia!")
        @Size(min = 6, message = "Senha precisa ter 6 ou mais dígitos!")
        String senha
) { }
