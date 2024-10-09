package io.github.AlexsandroCS.TesteTecKR.REST.Controller;

import io.github.AlexsandroCS.TesteTecKR.REST.DTO.criarProdutoDTO;
import io.github.AlexsandroCS.TesteTecKR.domain.entity.Produto;
import io.github.AlexsandroCS.TesteTecKR.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProdutoController {

    private ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    @PostMapping("/produto")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criarProduto(@Valid @RequestBody criarProdutoDTO criarProduto){
        return produtoService.criarProduto(criarProduto);
    }

    @GetMapping("/produto/{idProduto}")
    @ResponseStatus(HttpStatus.OK)
    public Produto capturarProduto(@PathVariable Long idProduto) {
        return produtoService.capturarProduto(idProduto);
    }
}
