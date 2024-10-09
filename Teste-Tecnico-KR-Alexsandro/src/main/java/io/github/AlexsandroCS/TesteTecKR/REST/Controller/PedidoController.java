package io.github.AlexsandroCS.TesteTecKR.REST.Controller;

import io.github.AlexsandroCS.TesteTecKR.REST.DTO.criarPedidoDTO;
import io.github.AlexsandroCS.TesteTecKR.REST.DTO.retornoCriarPedidoDTO;
import io.github.AlexsandroCS.TesteTecKR.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PedidoController {

    private PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/pedido")
    public ResponseEntity<retornoCriarPedidoDTO> criarPedido(@RequestBody criarPedidoDTO pedidoDTO) {
        try {
            retornoCriarPedidoDTO novoPedido = pedidoService.criarPedido(pedidoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/pedido")
    public ResponseEntity<List<retornoCriarPedidoDTO>> listarPedidos() {
        List<retornoCriarPedidoDTO> pedidos = pedidoService.buscarPedidos();
        return ResponseEntity.ok(pedidos);
    }
}
