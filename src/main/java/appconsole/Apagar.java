package appconsole;

import daojpa.DAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import model.Ingresso;
import model.Jogo;
import requisito.Fachada;


public class Apagar {

    private EntityManager manager;

    public Apagar() {
        Long id = 2L;
        try {


            DAO.open();
            System.out.println("Apagando ingresso de id" + id);
            Fachada.removerIngresso(id);


        } catch (Exception e) {
            System.out.println("Ingresso não encontrado!");
            e.printStackTrace();
        }
        finally
        {

            DAO.close();
            System.out.println("Ingresso removido com sucesso");
        }
    }

    public static void main(String[] args) {
        new Apagar();
    }
}
