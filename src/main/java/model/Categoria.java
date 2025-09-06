package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private int numero;
    
    @Column(nullable = false)
    private double preco;
    
    // Cascade: não delete, mas faça update e merge
    @OneToMany(mappedBy = "categoria", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Ingresso> ingressos;
    
    public Categoria() {}
    
    public Categoria(int numero, double preco) {
        this.numero = numero;
        this.preco = preco;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public List<Ingresso> getIngressos() {
        return ingressos;
    }
    
    public void setIngressos(List<Ingresso> ingressos) {
        this.ingressos = ingressos;
    }
    
    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", numero=" + numero +
                ", preco=" + preco +
                '}';
    }
}