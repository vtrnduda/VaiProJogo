package model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private int numero;
    
    @Column(nullable = false)
    private double preco;
    
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
    
    @Override
    public String toString() {
        return "Categoria{" +
                "numero=" + numero +
                ", preco=" + preco +
                '}';
    }
}