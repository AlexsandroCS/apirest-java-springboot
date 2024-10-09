package io.github.AlexsandroCS.TesteTecKR.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "produto")
    private String produto;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    public Produto(){ }

    public Produto(Long id, String produto, BigDecimal valor) {
        this.id = id;
        this.produto = produto;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}

