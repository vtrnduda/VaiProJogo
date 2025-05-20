package appconsole;


import java.util.List;
import java.time.LocalDateTime;

import com.db4o.ObjectContainer;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import model.Categoria;
import model.Ingresso;
import model.Jogo;

public class Consultar {
    private ObjectContainer manager;

    public Consultar() {
        try {
            manager = Util.conectar();
            consultar();
            Util.desconectar();
        } catch (Exception e) {
            System.out.println("Erro ao conectar no banco de dados: " + e.getMessage());
            return;
        }
    }

    public void consultar() {
        List<Categoria> categorias;
        List<Ingresso> ingressos;
        List<Jogo> jogos;
        Query q;


        System.out.println("\n---listar categoria com preço > 100:");
        q = manager.query();
        q.constrain(Categoria.class);
        q.constrain(new FiltroCategoriaPrecoMaiorQueX(100.0));
        categorias = q.execute();
        for(Categoria c : categorias) {
            System.out.println(c);
        }

        System.out.println("\n---listar os ingressos da categoria 3 do jogo JOGO-0001");
        q = manager.query();
        q.constrain(Ingresso.class);
        q.constrain(new FiltroIngressosCategoriaXJogoY(3, "JOGO-0001"));
        ingressos = q.execute();
        for(Ingresso i : ingressos) {
            System.out.println(i);
        }

        System.out.println("\n---listar jogos com mais de 50 ingressos vendidos");
        q = manager.query();
        q.constrain(Jogo.class);
        q.constrain(new FiltroJogoMaisXIngressos(2));
        jogos = q.execute();
        for(Jogo j : jogos) {
            System.out.println(j);
        }

    }

    public static void main(String[] args) {
        new Consultar();
    }
}

//***********************************************************************
//classes internas para os filtros
//***********************************************************************

class FiltroCategoriaPrecoMaiorQueX implements Evaluation {
    private double preco;
    public FiltroCategoriaPrecoMaiorQueX(double preco) {
        this.preco = preco;
    }

    public void evaluate(Candidate candidate) {

        Categoria cat = (Categoria) candidate.getObject();
        candidate.include(cat.getPreco() > preco);


    }
}
//quais os ingressos da categoria de numero 3 do jogo de id JOGO-0001

class FiltroIngressosCategoriaXJogoY implements Evaluation {
    private int n;
    private String s;
    public FiltroIngressosCategoriaXJogoY(int n, String s) {
        this.n = n;
        this.s = s;
    }

    public void evaluate(Candidate candidate) {
        Ingresso ing = (Ingresso) candidate.getObject(); // JOGO-0001
        boolean match = false;
        if (ing.getCategoria() != null && ing.getJogo() != null){
            match = ing.getCategoria().getNumero() == n && ing.getJogo().getId().equals(s);
        }

        candidate.include(match);
    }
}

class FiltroJogoMaisXIngressos implements Evaluation {
    private int n;
    public FiltroJogoMaisXIngressos(int n) {
        this.n = n;
    }
    public void evaluate(Candidate candidate) {
        Jogo jogo = (Jogo) candidate.getObject();

        if(jogo.getListaIngressos().size() > n) {
            candidate.include(true);
        } else {
            candidate.include(false);
        }
    }
}

