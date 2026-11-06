package br.com.sistemas.chamados.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;

    public Comentario() {}

    public Comentario(String texto, String autor, Chamado chamado) {
        this.texto = texto;
        this.autor = autor;
        this.chamado = chamado;
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public Chamado getChamado() { return chamado; }
    public void setChamado(Chamado chamado) { this.chamado = chamado; }
}