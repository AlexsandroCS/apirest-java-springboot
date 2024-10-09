package io.github.AlexsandroCS.TesteTecKR.domain.repository;

import io.github.AlexsandroCS.TesteTecKR.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    Usuario findByNome(String nome);
}
