package appconsole;

import daojpa.DAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Categoria;
import model.Ingresso;
import requisito.Fachada;


public class Alterar {

    private EntityManager manager;

    public Alterar() {

        String codigoIngresso = "FLAFLU173977";
        Long numeroCategoria = 3L;

        try {

            DAO.open();
            System.out.println("Alterando Ingresso" + codigoIngresso);
            Ingresso ingresso = Fachada.alterarCategoriaDoIngresso(numeroCategoria, codigoIngresso);

        } catch (Exception e) {
            System.err.println("Erro durante a alteração: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DAO.close();

        }
    }

    public static void main(String[] args) {
        new Alterar();
    }
}
