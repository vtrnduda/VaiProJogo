package model;

public class Categoria {
	private int numero;
    private double preco;
    
    public Categoria(int numero, double preco) {
        this.numero = numero;
        this.preco = preco;
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
