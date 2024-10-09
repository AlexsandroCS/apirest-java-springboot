package io.github.AlexsandroCS.TesteTecKR.domain.service;

import io.github.AlexsandroCS.TesteTecKR.REST.DTO.criarProdutoDTO;
import io.github.AlexsandroCS.TesteTecKR.domain.entity.Produto;
import io.github.AlexsandroCS.TesteTecKR.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto criarProduto(criarProdutoDTO novoProduto) {
        Produto produto = new Produto(null, novoProduto.produto(), novoProduto.valor());
        return produtoRepository.save(produto);
    }

    public Produto capturarProduto(Long idProduto) {
        return produtoRepository.findById(idProduto).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado!"));
    }
}
