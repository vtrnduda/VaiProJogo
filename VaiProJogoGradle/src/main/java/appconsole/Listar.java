package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Categoria;
import model.Ingresso;
import model.Jogo;
import util.JPAUtil;

import java.util.List;

public class Listar {
    private EntityManager manager;
    
    public Listar() {
        manager = JPAUtil.conectarBanco();
        
        try {
            listarTodos();
        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }
    
    private void listarTodos() {
        // Listar todas as categorias
        System.out.println("\n---listagem das Categorias:");
        TypedQuery<Categoria> queryCategoria = manager.createQuery(
            "SELECT c FROM Categoria c ORDER BY c.numero", Categoria.class);
        List<Categoria> categorias = queryCategoria.getResultList();
        for (Categoria c : categorias) {
            System.out.println(c);
        }
        
        // Listar todos os jogos
        System.out.println("\n---listagem dos Jogos:");
        TypedQuery<Jogo> queryJogo = manager.createQuery(
            "SELECT j FROM Jogo j ORDER BY j.id", Jogo.class);
        List<Jogo> jogos = queryJogo.getResultList();
        for (Jogo j : jogos) {
            System.out.println(j);
        }
        
        // Listar todos os ingressos
        System.out.println("\n---listagem dos Ingressos:");
        TypedQuery<Ingresso> queryIngresso = manager.createQuery(
            "SELECT i FROM Ingresso i ORDER BY i.codigo", Ingresso.class);
        List<Ingresso> ingressos = queryIngresso.getResultList();
        for (Ingresso i : ingressos) {
            System.out.println(i);
        }
    }
    
    public static void main(String[] args) {
        new Listar();
    }
} 