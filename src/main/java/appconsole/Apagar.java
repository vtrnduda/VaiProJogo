package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import model.Ingresso;
import model.Jogo;
import util.JPAUtil;


public class Apagar {

    private EntityManager manager;

    public Apagar() {
        try {

            String codigoIngresso = "FLAFLU781118";

            manager = JPAUtil.conectarBanco();
            manager.getTransaction().begin();

            Ingresso ingresso = manager.createQuery("SELECT i FROM Ingresso i WHERE i.codigo = :codigo", Ingresso.class)
                    .setParameter("codigo", codigoIngresso)
                    .getSingleResult();

            Jogo jogo = ingresso.getJogo();

            if (jogo == null) {
                System.out.println("Jogo nao encontrado");
                return;
            }

            if (jogo.getListaIngressos().removeIf(i -> i.getCodigo().equals(codigoIngresso))) {
                manager.remove(manager.contains(ingresso) ? ingresso : manager.merge(ingresso));
                manager.merge(jogo);
                manager.getTransaction().commit();
                System.out.println("Ingresso removido com sucesso");
            } else {
                System.out.println("Ingresso nao encontrado na lista do jogo");
            }
        } catch (NoResultException e)
        {
            System.out.println("Ingresso não encontrado!");
        } catch (Exception e) {

            manager.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {

            manager.close();
            System.out.println("Ingresso removido com sucesso");
        }
    }

    public static void main(String[] args) {
        new Apagar();
    }
}
