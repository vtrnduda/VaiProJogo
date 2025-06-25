package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Ingresso;
import model.Jogo;
import util.JPAUtil;

import java.util.List;
import java.util.Scanner;

public class Apagar {
    private EntityManager manager;
    
    public Apagar() {
        manager = JPAUtil.conectarBanco();
        
        try {
            // Exemplo específico como no projeto original
            apagarIngressoEspecifico();
            
            // Funcionalidade interativa adicional
            // apagarInterativo();
            
        } catch (Exception e) {
            if (manager != null && manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            System.out.println("Erro durante a exclusão: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        
        System.out.println("fim da aplicação");
    }
    
    private void apagarIngressoEspecifico() {
        System.out.println("Apagar Ingresso Específico");
        
        // Primeiro, vamos listar alguns ingressos para escolher um código
        System.out.println("\n=== Ingressos disponíveis ===");
        TypedQuery<Ingresso> queryListar = manager.createQuery(
            "SELECT i FROM Ingresso i ORDER BY i.codigo", Ingresso.class);
        List<Ingresso> todosIngressos = queryListar.getResultList();
        
        if (todosIngressos.isEmpty()) {
            System.out.println("Nenhum ingresso encontrado no banco!");
            return;
        }
        
        // Mostrar apenas os primeiros 5 ingressos
        System.out.println("Ingressos cadastrados:");
        for (int i = 0; i < Math.min(5, todosIngressos.size()); i++) {
            System.out.println(todosIngressos.get(i));
        }
        
        // Pegar o último ingresso como exemplo para deletar
        String codigoParaDeletar = todosIngressos.get(todosIngressos.size() - 1).getCodigo();
        System.out.println("\n=== Deletando ingresso: " + codigoParaDeletar + " ===");
        
        // Localizar ingresso a partir do código
        TypedQuery<Ingresso> queryIngresso = manager.createQuery(
            "SELECT i FROM Ingresso i WHERE i.codigo = :codigo", Ingresso.class);
        queryIngresso.setParameter("codigo", codigoParaDeletar);
        List<Ingresso> resultado = queryIngresso.getResultList();
        
        if (resultado.isEmpty()) {
            System.out.println("Ingresso não encontrado!");
            return;
        }
        
        Ingresso ingresso = resultado.get(0);
        System.out.println("Ingresso encontrado: " + ingresso);
        
        // Buscar o jogo associado
        TypedQuery<Jogo> queryJogo = manager.createQuery(
            "SELECT j FROM Jogo j WHERE j.id = :jogoId", Jogo.class);
        queryJogo.setParameter("jogoId", ingresso.getJogo().getId());
        List<Jogo> jogos = queryJogo.getResultList();
        
        if (jogos.isEmpty()) {
            System.out.println("Jogo não encontrado!");
            return;
        }
        
        manager.getTransaction().begin();
        
        Jogo jogo = jogos.get(0);
        
        // Remove o ingresso da lista do jogo (importante para manter consistência)
        jogo.getListaIngressos().remove(ingresso);
        
        // Remove o ingresso do banco
        manager.remove(manager.merge(ingresso));
        
        // Atualiza o jogo no banco
        manager.merge(jogo);
        
        manager.getTransaction().commit();
        
        System.out.println("Ingresso removido com sucesso!");
    }
    
    // Método interativo para deleção por input do usuário
    private void apagarInterativo() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n=== Exclusão Interativa ===");
        System.out.print("Digite o código do ingresso que deseja apagar: ");
        String codigo = scanner.nextLine();
        
        // Buscar o ingresso
        TypedQuery<Ingresso> queryIngresso = manager.createQuery(
            "SELECT i FROM Ingresso i WHERE i.codigo = :codigo", Ingresso.class);
        queryIngresso.setParameter("codigo", codigo);
        List<Ingresso> ingressos = queryIngresso.getResultList();
        
        if (ingressos.isEmpty()) {
            System.out.println("Ingresso com código '" + codigo + "' não encontrado!");
            return;
        }
        
        Ingresso ingresso = ingressos.get(0);
        System.out.println("Ingresso encontrado: " + ingresso);
        System.out.println("Jogo associado: " + ingresso.getJogo());
        
        System.out.print("Confirma a exclusão? (s/n): ");
        String confirmacao = scanner.nextLine();
        
        if (!confirmacao.equalsIgnoreCase("s")) {
            System.out.println("Exclusão cancelada!");
            return;
        }
        
        try {
            manager.getTransaction().begin();
            
            // Buscar o jogo para remover o ingresso da lista
            Jogo jogo = manager.find(Jogo.class, ingresso.getJogo().getId());
            if (jogo != null) {
                jogo.getListaIngressos().remove(ingresso);
                manager.merge(jogo);
            }
            
            // Remover o ingresso
            manager.remove(manager.merge(ingresso));
            manager.getTransaction().commit();
            
            System.out.println("Ingresso excluído com sucesso!");
            
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw e;
        }
    }
    
    // Método para apagar um jogo (e todos os seus ingressos)
    public void apagarJogo(Long jogoId) {
        try {
            manager.getTransaction().begin();
            
            // Buscar o jogo com seus ingressos
            TypedQuery<Jogo> queryJogo = manager.createQuery(
                "SELECT j FROM Jogo j LEFT JOIN FETCH j.listaIngressos WHERE j.id = :jogoId", 
                Jogo.class);
            queryJogo.setParameter("jogoId", jogoId);
            List<Jogo> jogos = queryJogo.getResultList();
            
            if (jogos.isEmpty()) {
                System.out.println("Jogo com ID " + jogoId + " não encontrado!");
                manager.getTransaction().rollback();
                return;
            }
            
            Jogo jogo = jogos.get(0);
            System.out.println("Jogo encontrado: " + jogo);
            System.out.println("Quantidade de ingressos associados: " + jogo.getListaIngressos().size());
            
            // Remover todos os ingressos do jogo primeiro
            for (Ingresso ingresso : jogo.getListaIngressos()) {
                manager.remove(ingresso);
            }
            
            // Remover o jogo
            manager.remove(jogo);
            manager.getTransaction().commit();
            
            System.out.println("Jogo e todos os seus ingressos foram removidos com sucesso!");
            
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw e;
        }
    }
    
    // Método para apagar uma categoria (se não houver ingressos associados)
    public void apagarCategoria(int numeroCategoria) {
        try {
            manager.getTransaction().begin();
            
            // Verificar se existem ingressos associados a esta categoria
            TypedQuery<Long> queryCount = manager.createQuery(
                "SELECT COUNT(i) FROM Ingresso i WHERE i.categoria.numero = :numero", 
                Long.class);
            queryCount.setParameter("numero", numeroCategoria);
            Long count = queryCount.getSingleResult();
            
            if (count > 0) {
                System.out.println("Não é possível apagar a categoria " + numeroCategoria + 
                                 " pois existem " + count + " ingressos associados a ela!");
                manager.getTransaction().rollback();
                return;
            }
            
            // Buscar e remover a categoria
            TypedQuery<model.Categoria> queryCategoria = manager.createQuery(
                "SELECT c FROM Categoria c WHERE c.numero = :numero", 
                model.Categoria.class);
            queryCategoria.setParameter("numero", numeroCategoria);
            List<model.Categoria> categorias = queryCategoria.getResultList();
            
            if (categorias.isEmpty()) {
                System.out.println("Categoria " + numeroCategoria + " não encontrada!");
                manager.getTransaction().rollback();
                return;
            }
            
            model.Categoria categoria = categorias.get(0);
            manager.remove(categoria);
            manager.getTransaction().commit();
            
            System.out.println("Categoria " + numeroCategoria + " removida com sucesso!");
            
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw e;
        }
    }
    
    public static void main(String[] args) {
        new Apagar();
    }
} 