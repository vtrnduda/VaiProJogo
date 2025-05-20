package appconsole;

import com.db4o.ObjectContainer;

import model.Categoria;
import model.Ingresso;
import model.Jogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cadastrar {

    private ObjectContainer manager;

    public Cadastrar(){
        manager = Util.conectar();

        System.out.println("cadastrando...");

        // Criar categorias de ingressos
        Categoria cat1 = new Categoria(1, 150.0);
        Categoria cat2 = new Categoria(2, 250.0);
        Categoria cat3 = new Categoria(3, 350.0);

        manager.store(cat1);
        manager.store(cat2);
        manager.store(cat3);
        manager.commit();

        // Criar jogos
        Jogo jogo1 = new Jogo(
                "15/10/2025 16:00",
                "Estádio Maracanã",
                "Flamengo",
                "Fluminense"
        );

        Jogo jogo2 = new Jogo(
                "22/10/2025 16:00",
                "Arena Itaquera",
                "Corinthians",
                "Palmeiras"
        );

        Jogo jogo3 = new Jogo(
                "05/11/2025 18:30",
                "Mineirão",
                "Cruzeiro",
                "Atlético-MG"
        );

        manager.store(jogo1);
        manager.store(jogo2);
        manager.store(jogo3);
        manager.commit();

        // Criar ingressos
        Ingresso ingresso1 = new Ingresso(jogo1, cat1);
        Ingresso ingresso2 = new Ingresso(jogo1, cat2);
        Ingresso ingresso3 = new Ingresso(jogo1, cat3);

        Ingresso ingresso4 = new Ingresso(jogo2, cat1);
        Ingresso ingresso5 = new Ingresso(jogo2, cat2);

        Ingresso ingresso6 = new Ingresso(jogo3, cat1);
        Ingresso ingresso7 = new Ingresso(jogo3, cat3);

        manager.store(ingresso1);
        manager.store(ingresso2);
        manager.store(ingresso3);
        manager.store(ingresso4);
        manager.store(ingresso5);
        manager.store(ingresso6);
        manager.store(ingresso7);
        manager.commit();

        System.out.println("Cadastro concluído com sucesso!");
        Util.desconectar();
        System.out.println("Fim do cadastro");
    }

    //=================================================
    public static void main(String[] args) {
        new Cadastrar();
    }
}
