package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

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

            System.out.println("");
            System.out.println("Consultando categorias com preco > 100");
            System.out.println("");
            System.out.println("----------------------------------------------------------");



            List<Categoria> categorias = manager.createQuery(
                            "SELECT c FROM Categoria c WHERE c.preco > :preco", Categoria.class)
                    .setParameter("preco", 100.0)
                    .getResultList();
            categorias.forEach(System.out::println);

            System.out.println("");
            System.out.println("----------------------------------------------------------");


            System.out.println("");
            System.out.println("Consultando ingressos da categoria 3 do jogo 1");
            System.out.println("");
            System.out.println("----------------------------------------------------------");
            List<Ingresso> ingressos = manager.createQuery(
                            "SELECT I FROM Ingresso I WHERE I.categoria.numero = :numeroCat and I.jogo.id = :jogoId", Ingresso.class)
                    .setParameter("numeroCat", 3)
                    .setParameter("jogoId", 1L)
                    .getResultList();
            ingressos.forEach(System.out::println);

            System.out.println("");
            System.out.println("Consultando jogo com mais de 2 ingressos vendidos");
            System.out.println("");
            System.out.println("----------------------------------------------------------");

            List<Jogo> jogos = manager.createQuery(
                            "SELECT J FROM Jogo J WHERE SIZE(J.listaIngressos) > :qtd ", Jogo.class)
                    .setParameter("qtd", 2)
                    .getResultList();
            jogos.forEach(System.out::println);



        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Erro ao conectar ao banco de dados");
        } finally {
            manager.close();
        }
    }


    public static void main(String[] args) {
        new Consultar();
    }
}
