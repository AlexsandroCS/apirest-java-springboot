package io.github.AlexsandroCS.TesteTecKR.REST.DTO;

import java.util.Date;

public record retornoCriarUsuarioDTO(
    Long id,
    String nome,
    String email,
    Date dataCriacao
) { }
