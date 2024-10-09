package io.github.AlexsandroCS.TesteTecKR.domain.repository;

import io.github.AlexsandroCS.TesteTecKR.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
