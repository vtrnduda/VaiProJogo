package appconsole;

import jakarta.persistence.EntityManager;
import model.Categoria;
import model.Ingresso;
import model.Jogo;
import util.JPAUtil;

public class Cadastrar {
    private EntityManager manager;

    public Cadastrar() {
        try {
            manager = JPAUtil.conectarBanco();

            System.out.println("Cadastrando...");

            // Criar categorias de ingressos
            manager.getTransaction().begin();
            Categoria cat1 = new Categoria(1, 150.0);
            Categoria cat2 = new Categoria(2, 250.0);
            Categoria cat3 = new Categoria(3, 350.0);
            
            manager.persist(cat1);
            manager.persist(cat2);
            manager.persist(cat3);
            manager.getTransaction().commit();

            // Criar jogos
            manager.getTransaction().begin();
            Jogo jogo1 = new Jogo(
                    "15/10/2025 16:00",
                    "Estádio Maracanã",
                    "Flamengo",
                    "Fluminense"
            );
            manager.persist(jogo1);
            manager.getTransaction().commit();

            manager.getTransaction().begin();
            Jogo jogo2 = new Jogo(
                    "22/10/2025 16:00",
                    "Arena Itaquera",
                    "Corinthians",
                    "Palmeiras"
            );
            manager.persist(jogo2);
            manager.getTransaction().commit();

            manager.getTransaction().begin();
            Jogo jogo3 = new Jogo(
                    "05/11/2025 18:30",
                    "Mineirão",
                    "Cruzeiro",
                    "Atlético-MG"
            );
            manager.persist(jogo3);
            manager.getTransaction().commit();

            // Criar ingressos
            manager.getTransaction().begin();
            Ingresso ingresso1 = new Ingresso(jogo1, cat1);
            Ingresso ingresso2 = new Ingresso(jogo1, cat2);
            Ingresso ingresso3 = new Ingresso(jogo1, cat3);
            
            manager.persist(ingresso1);
            manager.persist(ingresso2);
            manager.persist(ingresso3);
            manager.getTransaction().commit();

            manager.getTransaction().begin();
            Ingresso ingresso4 = new Ingresso(jogo2, cat1);
            Ingresso ingresso5 = new Ingresso(jogo2, cat2);
            
            manager.persist(ingresso4);
            manager.persist(ingresso5);
            manager.getTransaction().commit();

            manager.getTransaction().begin();
            Ingresso ingresso6 = new Ingresso(jogo3, cat1);
            Ingresso ingresso7 = new Ingresso(jogo3, cat3);
            
            manager.persist(ingresso6);
            manager.persist(ingresso7);
            manager.getTransaction().commit();

            System.out.println("Cadastro concluído com sucesso!");

        } catch (Exception e) {
            if (manager != null && manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            System.out.println("Erro durante o cadastro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
        
        System.out.println("Fim do cadastro");
    }

    //=================================================
    public static void main(String[] args) {
        new Cadastrar();
    }
}