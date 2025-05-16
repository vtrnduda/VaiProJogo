package model;

import java.util.Random;

public class Ingresso {
	
	private String codigo;
    private Categoria categoria;
    private Jogo jogo;
    
    public Ingresso(Jogo jogo, Categoria categoria) {
        this.jogo = jogo;
        this.categoria = categoria;
        this.codigo = gerarCodigo();
        this.jogo.adicionarIngresso(this);
    }
    
    public String getCodigo() {
        return codigo;
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
    
    private String gerarCodigo() {
        String prefixo = jogo.gerarPrefixoCodigo();
        String sequencia = gerarSequenciaAleatoria();
        return prefixo + sequencia;
    }
    
    private String gerarSequenciaAleatoria() {
        Random random = new Random();
        int numero = random.nextInt(1000000); 
        return String.format("%06d", numero); 
    }
    
    @Override
    public String toString() {
        return "Ingresso{" +
                "codigo='" + codigo + '\'' +
                ", categoria=" + categoria +
                ", jogo=" + jogo.getId() +
                '}';
    }
    
}
