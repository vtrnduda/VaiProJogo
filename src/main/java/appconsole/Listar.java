package appconsole;

import daojpa.DAO;
import requisito.Fachada;
import model.Categoria;
import model.Ingresso;
import model.Jogo;

import java.util.List;

public class Listar {

    public Listar() {
        try {
            // Abrir conexão uma vez
            DAO.open();
            System.out.println("Iniciando listagens...");

            System.out.println("");
            System.out.println("---------------------------------------");
            System.out.println("LISTAGEM DAS CATEGORIAS");
            System.out.println("---------------------------------------");

            List<Categoria> categorias = Fachada.listarCategorias();
            if (categorias.isEmpty()) {
                System.out.println("Nenhuma categoria cadastrada.");
            } else {
                System.out.println("Total de categorias: " + categorias.size());
                for (Categoria c : categorias) {
                    System.out.println(c);
                }
            }

            System.out.println("");
            System.out.println("---------------------------------------");
            System.out.println("LISTAGEM DOS INGRESSOS");
            System.out.println("---------------------------------------");

            List<Ingresso> ingressos = Fachada.listarIngressos();
            if (ingressos.isEmpty()) {
                System.out.println("Nenhum ingresso cadastrado.");
            } else {
                System.out.println("Total de ingressos: " + ingressos.size());
                for (Ingresso i : ingressos) {
                    System.out.println(i);
                }
            }

            System.out.println("");
            System.out.println("---------------------------------------");
            System.out.println("LISTAGEM DOS JOGOS");
            System.out.println("---------------------------------------");

            List<Jogo> jogos = Fachada.listarJogos();
            if (jogos.isEmpty()) {
                System.out.println("Nenhum jogo cadastrado.");
            } else {
                System.out.println("Total de jogos: " + jogos.size());
                for (Jogo j : jogos) {
                    System.out.println(j);
                }
            }

            System.out.println("");
            System.out.println("---------------------------------------");
            System.out.println("LISTAGENS CONCLUÍDAS!");
            System.out.println("---------------------------------------");

        } catch (Exception e) {
            System.err.println("Erro durante as listagens: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fechar conexão no final
            DAO.close();
        }
    }

    public static void main(String[] args) {
        new Listar();
    }
}