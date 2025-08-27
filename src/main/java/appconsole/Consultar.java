package appconsole;

import daojpa.DAO;
import requisito.Fachada;
import model.Categoria;
import model.Ingresso;
import model.Jogo;

import java.util.List;

public class Consultar {

    public Consultar() {
        try {
            // Abrir conexão uma vez
            DAO.open();
            System.out.println("Iniciando consultas...");

            System.out.println("");
            System.out.println("----------------------------------------------------------");
            System.out.println("Consultando categorias com preço > 100");
            System.out.println("----------------------------------------------------------");

            List<Categoria> categorias = Fachada.categoriasComPrecoMaiorQue(100.0);
            if (categorias.isEmpty()) {
                System.out.println("Nenhuma categoria encontrada com preço > 100");
            } else {
                categorias.forEach(System.out::println);
            }

            System.out.println("");
            System.out.println("----------------------------------------------------------");
            System.out.println("Consultando ingressos da categoria 3 do jogo 1");
            System.out.println("----------------------------------------------------------");

            List<Ingresso> ingressos = Fachada.ingressosDaCategoriaXDoJogoY(3, 1L);
            if (ingressos.isEmpty()) {
                System.out.println("Nenhum ingresso encontrado para categoria 3 do jogo 1");
            } else {
                ingressos.forEach(System.out::println);
            }

            System.out.println("");
            System.out.println("----------------------------------------------------------");
            System.out.println("Consultando jogos com mais de 2 ingressos vendidos");
            System.out.println("----------------------------------------------------------");

            List<Jogo> jogos = Fachada.jogosComMaisDeXIngressosVendidos(2);
            if (jogos.isEmpty()) {
                System.out.println("Nenhum jogo encontrado com mais de 2 ingressos");
            } else {
                jogos.forEach(System.out::println);
            }

            System.out.println("");
            System.out.println("----------------------------------------------------------");
            System.out.println("Consultas concluídas!");

        } catch (Exception e) {
            System.err.println("Erro durante as consultas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fechar conexão no final
            DAO.close();
        }
    }

    public static void main(String[] args) {
        new Consultar();
    }
}