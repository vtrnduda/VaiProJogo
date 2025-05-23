package appconsole;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import model.Categoria;
import model.Ingresso;

import java.util.List;

public class Alterar {
    private static ObjectContainer manager;

    public Alterar () {
        manager = Util.conectar();

        Query qi = manager.query();
        qi.constrain(Ingresso.class);
        qi.descend("codigo").constrain("CRUATL168328");
        List<Ingresso> resultados = qi.execute();

        Query qc = manager.query();
        qc.constrain(Categoria.class);
        qc.descend("numero").constrain(3);
        List<Categoria> resultadoCategorias = qc.execute();

        if(!resultados.isEmpty()) {
            System.out.println("Alterando o ingresso para categoria 3");
            Ingresso i = resultados.getFirst();

            System.out.println("Antes da alteracao:" + i );
            i.setCategoria(resultadoCategorias.getFirst());
            System.out.println("Depois da alteracao:" + i);


          manager.store(i);
          manager.commit();

        } else
            System.out.println("Categoria inexistente");

        Util.desconectar();

    }

    public static void main(String[] args) {
        new Alterar();
    }
}
