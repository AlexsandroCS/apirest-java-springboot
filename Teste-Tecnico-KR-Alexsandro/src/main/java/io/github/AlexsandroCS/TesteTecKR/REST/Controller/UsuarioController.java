package io.github.AlexsandroCS.TesteTecKR.REST.Controller;

import io.github.AlexsandroCS.TesteTecKR.REST.DTO.criarUsuarioDTO;
import io.github.AlexsandroCS.TesteTecKR.REST.DTO.loginDTO;
import io.github.AlexsandroCS.TesteTecKR.REST.DTO.retornoCriarUsuarioDTO;
import io.github.AlexsandroCS.TesteTecKR.REST.DTO.tokenRessDTO;
import io.github.AlexsandroCS.TesteTecKR.domain.service.DTO.entradaTokenDTO;
import io.github.AlexsandroCS.TesteTecKR.domain.service.DTO.mapper.LoginInputMapper;
import io.github.AlexsandroCS.TesteTecKR.domain.service.PedidoService;
import io.github.AlexsandroCS.TesteTecKR.domain.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private UsuarioService usuarioService;
    private PedidoService pedidoService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, PedidoService pedidoService){
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
    }

    @PostMapping("/usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public retornoCriarUsuarioDTO criarUsuario(@Valid @RequestBody criarUsuarioDTO criarLogin){
        return usuarioService.criarUsuario(criarLogin);
    }

    @GetMapping("/usuario/{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    public retornoCriarUsuarioDTO capturarUsuario(@RequestBody @PathVariable Long idUsuario){
        return usuarioService.capturarUsuario(idUsuario);
    }

    @PutMapping("/usuario/{idUsuario}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public retornoCriarUsuarioDTO atualizarUsuario(@PathVariable Long idUsuario, @Valid @RequestBody criarUsuarioDTO usuario){
        return usuarioService.alterarUsuario(idUsuario, usuario);
    }

    @DeleteMapping("/usuario/{idUsuario}")
    public ResponseEntity<Map<String, String>> deletarUsuario(@PathVariable Long idUsuario) {
        Boolean resultadoExclusao = pedidoService.deletarPedidosUsuario(idUsuario) && usuarioService.deletaUsuario(idUsuario);
        Map<String, String> mensagemEndPoint = new HashMap<>();

        if (resultadoExclusao) {
            mensagemEndPoint.put("Mensagem", "Usuário foi deletado com sucesso!");
            return ResponseEntity.ok(mensagemEndPoint);
        }
        mensagemEndPoint.put("Mensagem", "Não é possível deletar usuário!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemEndPoint);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<tokenRessDTO> login(@RequestBody @Valid loginDTO verificaLogin){
        entradaTokenDTO loginMapperUser = LoginInputMapper.build().apply(verificaLogin);
        String token = usuarioService.verificaLogin(loginMapperUser.email(), loginMapperUser.password()).token();
        tokenRessDTO respostaToken = new tokenRessDTO(token);
        return ResponseEntity.ok(respostaToken);
    }
}