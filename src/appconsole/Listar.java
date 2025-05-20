package appconsole;


import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import model.Categoria;
import model.Ingresso;
import model.Jogo;

import java.util.List;

public class Listar {
    private static ObjectContainer manager;
    public static void main(String[] args) {
        manager = Util.conectar();

        Query qc = manager.query();
        System.out.println("\n---listagem das Categorias:");

        qc.constrain(Categoria.class);
        List<Categoria> categorias = qc.execute();
        for (Categoria c : categorias) {
            System.out.println(c);
        }

        Query qj = manager.query();
        System.out.println("\n---listagem dos Jogos:");

        qj.constrain(Jogo.class);
        List<Jogo> jogos = qj.execute();
        for (Jogo j : jogos) {
            System.out.println(j);
        }

        Query qi = manager.query();
        System.out.println("\n---listagem dos Ingressos:");

        qi.constrain(Ingresso.class);
        List<Ingresso> ingressos = qi.execute();
        for (Ingresso i : ingressos) {
            System.out.println(i);
        }

        Util.desconectar();
        }
}
