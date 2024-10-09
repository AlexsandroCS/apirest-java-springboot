package io.github.AlexsandroCS.TesteTecKR.domain.service.DTO.mapper;

import io.github.AlexsandroCS.TesteTecKR.REST.DTO.loginDTO;
import io.github.AlexsandroCS.TesteTecKR.domain.service.DTO.entradaTokenDTO;

import java.util.function.Function;

public class LoginInputMapper implements Function<loginDTO, entradaTokenDTO> {

    public static LoginInputMapper build(){
        return new LoginInputMapper();
    }

    @Override
    public entradaTokenDTO apply(loginDTO login){
        return new entradaTokenDTO(login.email(), login.senha());
    }
}
