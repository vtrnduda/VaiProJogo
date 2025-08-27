package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Categoria;
import model.Ingresso;
//import util.JPAUtil;

public class Alterar {

    private EntityManager manager;

    public Alterar() {
//        manager = JPAUtil.conectarBanco();
        String codigoIngresso = "FLAFLU328855";
        int numeroCategoria = 3;

        try {

            manager.getTransaction().begin();


            Ingresso ingresso = manager.createQuery(
                            "SELECT I FROM Ingresso I WHERE I.codigo = :codigo", Ingresso.class)
                    .setParameter("codigo", codigoIngresso)
                    .getSingleResult();


            Categoria categoria = manager.createQuery(
                            "SELECT C FROM Categoria C WHERE C.numero = :numero", Categoria.class)
                    .setParameter("numero", numeroCategoria)
                    .getSingleResult();

            System.out.println("Antes da alteracao" + ingresso);

            ingresso.setCategoria(categoria);
            manager.merge(ingresso);
            manager.getTransaction().commit();

            System.out.println("Depois da alteracao" + ingresso);


        } catch (NoResultException e) {
            System.out.println("Ingresso ou Categoria não encontrado(a).");
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            manager.close();

        }
    }

    public static void main(String[] args) {
        new Alterar();
    }
}
