package io.github.AlexsandroCS.TesteTecKR.REST.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    // Para erros: 404.
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> erroNotFoundHandler(IllegalArgumentException errors){
        Map<String, String> returnErrors = new HashMap<>();
        returnErrors.put("Erro", errors.getMessage());
        return returnErrors;
    }

    // Para erros: 400.
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> erroBadRequestHandler(NullPointerException errors){
        Map<String, String> returnErrors = new HashMap<>();
        returnErrors.put("Erro", errors.getMessage());
        return returnErrors;
    }

    // Para erros: 403.
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleAccessDeniedException(AccessDeniedException errors) {
        Map<String, String> returnErrors = new HashMap<>();
        returnErrors.put("Erro", "Erro de acesso! favor inserir o Token para continuar a requisição.");
        return returnErrors;
    }

    // Para erros: 500.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> erroGenericHandler(MethodArgumentNotValidException errors) {
        Map<String, String> returnErrors = new HashMap<>();

        errors.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            returnErrors.put(fieldName, errorMessage);
        });
        return returnErrors;
    }

    // Qualquer outro tipo de erro.
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> erroGenericHandler(Exception errors){
        Map<String, String> returnErrors = new HashMap<>();
        returnErrors.put("Erro", errors.getMessage());
        return returnErrors;
    }
}