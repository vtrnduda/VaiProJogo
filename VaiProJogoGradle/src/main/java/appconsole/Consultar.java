package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Categoria;
import model.Ingresso;
import model.Jogo;
import util.JPAUtil;

import java.util.List;

public class Consultar {
    private EntityManager manager;
    
    public Consultar() {
        try {
            manager = JPAUtil.conectarBanco();
            consultar();
        } catch (Exception e) {
            System.out.println("Erro ao conectar no banco de dados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }
    
    public void consultar() {
        // 1. Listar categoria com preço > 100
        System.out.println("\n---listar categoria com preço > 100:");
        consultarCategoriasPrecoMaiorQue(100.0);
        
        // 2. Listar os ingressos da categoria 3 do jogo 1
        System.out.println("\n---listar os ingressos da categoria 3 do jogo 1");
        consultarIngressosCategoriaEJogo(3, 1L);
        
        // 3. Listar jogos com mais de 2 ingressos vendidos
        System.out.println("\n---listar jogos com mais de 2 ingressos vendidos");
        consultarJogosComMaisXIngressos(2);
    }
    
    private void consultarCategoriasPrecoMaiorQue(double preco) {
        TypedQuery<Categoria> query = manager.createQuery(
            "SELECT c FROM Categoria c WHERE c.preco > :preco ORDER BY c.numero", 
            Categoria.class);
        query.setParameter("preco", preco);
        
        List<Categoria> categorias = query.getResultList();
        for (Categoria c : categorias) {
            System.out.println(c);
        }
    }
    
    private void consultarIngressosCategoriaEJogo(int numeroCategoria, Long jogoId) {
        TypedQuery<Ingresso> query = manager.createQuery(
            "SELECT i FROM Ingresso i WHERE i.categoria.numero = :numeroCategoria AND i.jogo.id = :jogoId ORDER BY i.codigo", 
            Ingresso.class);
        query.setParameter("numeroCategoria", numeroCategoria);
        query.setParameter("jogoId", jogoId);
        
        List<Ingresso> ingressos = query.getResultList();
        for (Ingresso i : ingressos) {
            System.out.println(i);
        }
    }
    
    private void consultarJogosComMaisXIngressos(int quantidadeMinima) {
        TypedQuery<Jogo> query = manager.createQuery(
            "SELECT j FROM Jogo j LEFT JOIN FETCH j.listaIngressos WHERE SIZE(j.listaIngressos) > :quantidade ORDER BY j.id", 
            Jogo.class);
        query.setParameter("quantidade", quantidadeMinima);
        
        List<Jogo> jogos = query.getResultList();
        for (Jogo j : jogos) {
            System.out.println(j);
        }
    }
    
    // Métodos adicionais de consulta úteis
    public void consultarIngressosPorCodigo(String codigo) {
        System.out.println("\n---buscar ingresso por código: " + codigo);
        TypedQuery<Ingresso> query = manager.createQuery(
            "SELECT i FROM Ingresso i WHERE i.codigo = :codigo", 
            Ingresso.class);
        query.setParameter("codigo", codigo);
        
        List<Ingresso> ingressos = query.getResultList();
        for (Ingresso i : ingressos) {
            System.out.println(i);
        }
    }
    
    public void consultarJogosPorTime(String nomeTime) {
        System.out.println("\n---buscar jogos por time: " + nomeTime);
        TypedQuery<Jogo> query = manager.createQuery(
            "SELECT j FROM Jogo j WHERE j.timeA LIKE :nomeTime OR j.timeB LIKE :nomeTime ORDER BY j.dataHora", 
            Jogo.class);
        query.setParameter("nomeTime", "%" + nomeTime + "%");
        
        List<Jogo> jogos = query.getResultList();
        for (Jogo j : jogos) {
            System.out.println(j);
        }
    }
    
    public static void main(String[] args) {
        new Consultar();
    }
} 