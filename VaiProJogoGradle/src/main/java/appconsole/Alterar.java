package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Categoria;
import model.Ingresso;
import model.Jogo;
import util.JPAUtil;

import java.util.List;
import java.util.Scanner;

public class Alterar {
    private EntityManager manager;
    
    public Alterar() {
        manager = JPAUtil.conectarBanco();
        
        try {
            // Exemplo específico como no projeto original
            alterarIngressoEspecifico();
            
            // Funcionalidade interativa adicional
            // alterarInterativo();
            
        } catch (Exception e) {
            if (manager != null && manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            System.out.println("Erro durante a alteração: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }
    
    private void alterarIngressoEspecifico() {
        // Primeiro, vamos listar alguns ingressos para escolher um código
        System.out.println("=== Ingressos disponíveis ===");
        TypedQuery<Ingresso> queryListar = manager.createQuery(
            "SELECT i FROM Ingresso i ORDER BY i.codigo", Ingresso.class);
        List<Ingresso> todosIngressos = queryListar.getResultList();
        
        if (todosIngressos.isEmpty()) {
            System.out.println("Nenhum ingresso encontrado no banco!");
            return;
        }
        
        // Mostrar apenas os primeiros 5 ingressos
        System.out.println("Primeiros ingressos cadastrados:");
        for (int i = 0; i < Math.min(5, todosIngressos.size()); i++) {
            System.out.println(todosIngressos.get(i));
        }
        
        // Pegar o primeiro ingresso como exemplo
        String codigoExemplo = todosIngressos.get(0).getCodigo();
        System.out.println("\n=== Alterando ingresso: " + codigoExemplo + " ===");
        
        // Buscar o ingresso específico
        TypedQuery<Ingresso> queryIngresso = manager.createQuery(
            "SELECT i FROM Ingresso i WHERE i.codigo = :codigo", Ingresso.class);
        queryIngresso.setParameter("codigo", codigoExemplo);
        List<Ingresso> resultados = queryIngresso.getResultList();
        
        // Buscar categoria 3
        TypedQuery<Categoria> queryCategoria = manager.createQuery(
            "SELECT c FROM Categoria c WHERE c.numero = :numero", Categoria.class);
        queryCategoria.setParameter("numero", 3);
        List<Categoria> resultadoCategorias = queryCategoria.getResultList();
        
        if (!resultados.isEmpty() && !resultadoCategorias.isEmpty()) {
            manager.getTransaction().begin();
            
            Ingresso ingresso = resultados.get(0);
            Categoria novaCategoria = resultadoCategorias.get(0);
            
            System.out.println("Antes da alteração: " + ingresso);
            System.out.println("Categoria atual: " + ingresso.getCategoria());
            
            // Alterar a categoria
            ingresso.setCategoria(novaCategoria);
            
            System.out.println("Depois da alteração: " + ingresso);
            System.out.println("Nova categoria: " + ingresso.getCategoria());
            
            // Persistir a alteração
            manager.merge(ingresso);
            manager.getTransaction().commit();
            
            System.out.println("Alteração realizada com sucesso!");
            
        } else {
            if (resultados.isEmpty()) {
                System.out.println("Ingresso não encontrado!");
            }
            if (resultadoCategorias.isEmpty()) {
                System.out.println("Categoria 3 não encontrada!");
            }
        }
    }
    
    // Método interativo para alteração por input do usuário
    private void alterarInterativo() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n=== Alteração Interativa ===");
        System.out.print("Digite o código do ingresso que deseja alterar: ");
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
        
        // Listar categorias disponíveis
        System.out.println("\nCategorias disponíveis:");
        TypedQuery<Categoria> queryCategorias = manager.createQuery(
            "SELECT c FROM Categoria c ORDER BY c.numero", Categoria.class);
        List<Categoria> categorias = queryCategorias.getResultList();
        
        for (Categoria c : categorias) {
            System.out.println("- " + c);
        }
        
        System.out.print("Digite o número da nova categoria: ");
        try {
            int numeroCategoria = Integer.parseInt(scanner.nextLine());
            
            // Buscar a categoria escolhida
            TypedQuery<Categoria> queryCategoria = manager.createQuery(
                "SELECT c FROM Categoria c WHERE c.numero = :numero", Categoria.class);
            queryCategoria.setParameter("numero", numeroCategoria);
            List<Categoria> categoriasEncontradas = queryCategoria.getResultList();
            
            if (categoriasEncontradas.isEmpty()) {
                System.out.println("Categoria " + numeroCategoria + " não encontrada!");
                return;
            }
            
            manager.getTransaction().begin();
            
            Categoria novaCategoria = categoriasEncontradas.get(0);
            System.out.println("Antes da alteração: " + ingresso);
            
            ingresso.setCategoria(novaCategoria);
            manager.merge(ingresso);
            manager.getTransaction().commit();
            
            System.out.println("Depois da alteração: " + ingresso);
            System.out.println("Alteração realizada com sucesso!");
            
        } catch (NumberFormatException e) {
            System.out.println("Número de categoria inválido!");
        }
    }
    
    // Método para alterar dados de um jogo
    public void alterarJogo(Long jogoId, String novaDataHora, String novoLocal) {
        try {
            manager.getTransaction().begin();
            
            Jogo jogo = manager.find(Jogo.class, jogoId);
            if (jogo != null) {
                System.out.println("Antes da alteração: " + jogo);
                
                if (novaDataHora != null) {
                    jogo.setDataHora(novaDataHora);
                }
                if (novoLocal != null) {
                    jogo.setLocal(novoLocal);
                }
                
                manager.merge(jogo);
                manager.getTransaction().commit();
                
                System.out.println("Depois da alteração: " + jogo);
                System.out.println("Jogo alterado com sucesso!");
            } else {
                System.out.println("Jogo com ID " + jogoId + " não encontrado!");
                manager.getTransaction().rollback();
            }
            
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw e;
        }
    }
    
    public static void main(String[] args) {
        new Alterar();
    }
} 