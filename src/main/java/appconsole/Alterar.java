package appconsole;

import daojpa.DAO;
import model.Ingresso;
import requisito.Fachada;


public class Alterar {


    public Alterar() {

        String codigoIngresso = "FLAFLU173977";
        Long numeroCategoria = 3L;

        try {

            DAO.open();
            System.out.println("Alterando Ingresso" + codigoIngresso);
            Fachada.alterarCategoriaDoIngresso(numeroCategoria, codigoIngresso);

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
