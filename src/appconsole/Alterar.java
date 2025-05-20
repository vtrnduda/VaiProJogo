package appconsole;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import model.Categoria;

import java.util.List;

public class Alterar {
    private static ObjectContainer manager;

    public Alterar () {
        manager = Util.conectar();

        Query qc = manager.query();
        qc.constrain(Categoria.class);
        qc.descend("numero").constrain(1);
        List<Categoria> resultados = qc.execute();

        if(!resultados.isEmpty()) {
          System.out.println("Alterando a Categoria 1");
          Categoria c = resultados.getFirst();
          c.setNumero(4);
          c.setPreco(700.0);

          manager.store(c);
          manager.commit();

          System.out.println("Alteração realizada com sucesso!");
        } else
            System.out.println("Categoria 1 inexistente");

        Util.desconectar();

    }

    public static void main(String[] args) {
        new Alterar();
    }
}
