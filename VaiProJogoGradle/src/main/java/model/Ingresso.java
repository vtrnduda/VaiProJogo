package model;

import jakarta.persistence.*;
import java.util.Random;

@Entity
@Table(name = "ingressos")
public class Ingresso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String codigo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;
    
    public Ingresso() {}
    
    public Ingresso(Jogo jogo, Categoria categoria) {
        this.jogo = jogo;
        this.categoria = categoria;
        this.codigo = gerarCodigo();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public Jogo getJogo() {
        return jogo;
    }
    
    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }
    
    private String gerarCodigo() {
        if (jogo != null) {
            String prefixo = jogo.gerarPrefixoCodigo();
            String sequencia = gerarSequenciaAleatoria();
            return prefixo + sequencia;
        }
        return gerarSequenciaAleatoria();
    }
    
    private String gerarSequenciaAleatoria() {
        Random random = new Random();
        int numero = random.nextInt(1000000); 
        return String.format("%06d", numero); 
    }
    
    @PrePersist
    private void prePersist() {
        if (this.codigo == null) {
            this.codigo = gerarCodigo();
        }
    }
    
    @Override
    public String toString() {
        return "Ingresso{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", categoria=" + (categoria != null ? categoria.getNumero() : null) +
                ", jogo=" + (jogo != null ? jogo.getId() : null) +
                '}';
    }
}