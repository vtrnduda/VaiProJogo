package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import model.Categoria;
import model.Ingresso;
import model.Jogo;
import util.JPAUtil;

import java.util.List;


public class Listar {
    private EntityManager manager;

    public Listar() {
        try{
            manager = JPAUtil.conectarBanco();

            System.out.println("");
            System.out.println("---------------------------------------");
            System.out.println("");

            System.out.println("Listagem das Categorias");
            List<Categoria> categorias = manager.createQuery("SELECT C FROM Categoria C", Categoria.class).getResultList();
            for (Categoria c : categorias) {
                System.out.println(c);
            }

            System.out.println("");
            System.out.println("---------------------------------------");
            System.out.println("");

            System.out.println("Listagem das Ingressos");
            List<Ingresso> ingressos = manager.createQuery("SELECT I FROM Ingresso I", Ingresso.class).getResultList();
            for (Ingresso i : ingressos) {
                System.out.println(i);
            }

            System.out.println("");
            System.out.println("---------------------------------------");
            System.out.println("");

            System.out.println("Listagem das Jogos");
            List<Jogo> jogos = manager.createQuery("SELECT J FROM Jogo J", Jogo.class).getResultList();
            for (Jogo j : jogos) {
                System.out.println(j);
            }

            System.out.println("");
            System.out.println("---------------------------------------");
            System.out.println("");

        }
        finally {  if (manager != null) manager.close(); }

    }

    public static void main(String[] args) {
        new Listar();
    }
}
