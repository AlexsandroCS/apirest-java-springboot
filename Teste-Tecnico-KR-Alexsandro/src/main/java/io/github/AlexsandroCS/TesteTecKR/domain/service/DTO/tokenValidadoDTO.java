package io.github.AlexsandroCS.TesteTecKR.domain.service.DTO;

public record tokenValidadoDTO(
        boolean isValid,
        int usuarioId,
        String userValid
) { }
