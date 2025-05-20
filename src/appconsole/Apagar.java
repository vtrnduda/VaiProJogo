package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import model.Ingresso;

public class Apagar {
    private static ObjectContainer manager;

    public static void main(String[] args) {
        manager = Util.conectar();
        System.out.println("Apagar o Ingresso CRUATL648529");

        // localizar inrgesso com numero CRUATL648529
        Query q = manager.query();
        q.constrain(Ingresso.class);
        q.descend("codigo").constrain("CRUATL648529");
        List<Ingresso> resultado = q.execute();

        if (!resultado.isEmpty()) {
            Ingresso ingresso = resultado.getFirst();

            manager.delete(ingresso);
            manager.commit();
        }

        Util.desconectar();
        System.out.println("fim da aplicação");
    }

}